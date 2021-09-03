package com.demo.messagecenterdemo;

import android.app.Application;
import android.text.TextUtils;

import com.jingdong.amon.router.JDRouter;
import com.jingdong.app.mall.bundle.CommonMessageCenter.config.MsgCenterEnvironmentConfig;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.JumpRouteI;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.MatObserverI;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.UserInfoI;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.AppUtils;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.LogUtil;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.MsgCenterConfigUtils;

import org.json.JSONObject;

import java.util.Map;


public class DemoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
//        JDRouter.init(this);
        AppUtils.init(this);

        // 初始化时传入appName
        MsgCenterConfigUtils.getInstance().initEnvironmentConfig(MsgCenterEnvironmentConfig.build()
                .setAppName("DemoTest")
                .setAppKey("test2021!@#")
                .setHost("http://beta-vip-msg-center.jd.com/client")
                .setIsDebug(true));

        MsgCenterConfigUtils.getInstance().setUserInfoI(new UserInfoI() {
            @Override
            public String getUserPin() {
                // 主工程调用获取pin的方法，返回pin给消息中心
                return LocalCacheUtils.getInstance().getPin();
            }

            @Override
            public boolean isLogin() {
                // 主工程调用获取是否已经登录的方法，返回登录状态给消息中心
                if (TextUtils.isEmpty(LocalCacheUtils.getInstance().getPin())) {
                    return false;
                }
                return true;
            }
        });

        MsgCenterConfigUtils.getInstance().setRouteI(new JumpRouteI() {
            @Override
            public void detailPageToLandPage(JSONObject data) {
                // 跳转跳转详情页面落地页
                LogUtil.d("跳转跳转详情页面落地页...");
            }
        });

        MsgCenterConfigUtils.getInstance().addMatObserver(new MatObserverI() {
            @Override
            public void matObserver(Map<String, Object> map) {
                // 拿到埋点相关参数
            }
        });
    }
}
