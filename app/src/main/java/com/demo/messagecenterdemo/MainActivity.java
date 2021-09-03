package com.demo.messagecenterdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.demo.messagecenterdemo.login.LoginActivity;
import com.jingdong.app.mall.bundle.CommonMessageCenter.MsgCenterConfig;
import com.jingdong.app.mall.bundle.CommonMessageCenter.activity.MessageCenterActivity;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.LoginI;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.LoginSuccess;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.MatObserverI;
import com.jingdong.app.mall.bundle.CommonMessageCenter.interfaces.OnMsgResponseListenerI;
import com.jingdong.app.mall.bundle.CommonMessageCenter.service.MsgCenterInterfaceService;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.LogUtil;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.MsgCenterConfigUtils;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LoginSuccess mLoginSuccess;
    private Button mBtnLogin;
    List<Map<String, Object>> ddList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        MsgCenterConfig.getInstance().setLocalOrNetwork(true);

        initView();

        setMatData();

        MsgCenterConfigUtils.getInstance().setLoginListener(new LoginI() {
            @Override
            public void toLogin(LoginSuccess loginSuccess) {
                mLoginSuccess = loginSuccess;
                // 跳转登录
                LogUtil.d("跳转登录...");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void initView() {
        mBtnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String loginUser = LocalCacheUtils.getInstance().getPin();
        if (TextUtils.isEmpty(loginUser)) {
            mBtnLogin.setText("登录");
        } else {
            mBtnLogin.setText(loginUser + "已登录");
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_theme_one:
                intent.setClass(MainActivity.this, MessageCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.img_setting:
                intent.setClass(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dd:
                setDDMsgObserver();
                ToastUtils.showToast(this, "添加咚咚数据成功");
                break;
            case R.id.imgTestBtn:
                intent.setClass(MainActivity.this, ImgTestActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && mLoginSuccess != null) {
            // 登录成功之后返回登录结果
            mLoginSuccess.loginSuccess(new HashMap<String, Object>() {
                {
                    put("pin", LocalCacheUtils.getInstance().getPin());
                    put("token", "token");
                }
            });
        }
    }

    private void getRedPointNum() {
        MsgCenterInterfaceService.getUnreadMsgCount(new OnMsgResponseListenerI() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    int num = jsonObject.getInt("num");
                    boolean redPoint = jsonObject.getBoolean("redPoint");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getFirstLevelMsgList() {
        MsgCenterInterfaceService.getFirstLevelMsgList(new OnMsgResponseListenerI() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    JSONArray jsonArray = jsonObject.getJSONArray("msgBoxList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getSecondLevelMsgList() {
        String accountType = "1";
        String page = "0";
        String msgId = "12344";
        MsgCenterInterfaceService.getSecondLevelMsgList(accountType, page, msgId, new OnMsgResponseListenerI() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                    JSONArray jsonArray = jsonObject.getJSONArray("secondLevelList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cleanUnreadMsg() {
        MsgCenterInterfaceService.cleanUnReadMsg("", new OnMsgResponseListenerI() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String code = jsonObject.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置消息中心埋点
     */
    private void setMatData() {
        MsgCenterConfigUtils.getInstance().addMatObserver(new MatObserverI() {
            @Override
            public void matObserver(Map<String, Object> matMap) {
                // 获取埋点参数
                String eventId = (String) matMap.get("eventId");
                // TODO 宿主设置埋点方法
                LogUtil.d("添加埋点...");
            }
        });
    }

    private void setDDMsgObserver() {
        List<Map<String, Object>> ddList = new ArrayList<>();
        ddList.add(addDDMap());
        MsgCenterConfigUtils.getInstance().getDdMsgObserver().ddMsgListNotify(ddList);
    }

    private Map<String, Object> addDDMap() {

        Map<String, Object> data = new HashMap<>();
        data.put("accountName", "咚咚会话");
        data.put("iconUrl", "http://img10.360buyimg.com/yihaodianmsgcenter/jfs/t1/157293/17/12471/16907/604b00b2E326f0b6c/42fe88697ee0fb88.png");
        data.put("bubblesCount", 0);
        data.put("content", "咚咚会话消息内容");
        data.put("newMsgTime", 1617013321260L);
        data.put("isMute", 0);
        data.put("isTop", 0);
        data.put("ddSessionID", "233434");
        data.put("extraAttribute", null);

        return data;
    }
}