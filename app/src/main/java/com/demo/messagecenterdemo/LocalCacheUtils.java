package com.demo.messagecenterdemo;

import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.SharedPreferencesUtil;

/**
 * Author: weichangzhou3
 * Date: 2021/8/26 11:00
 * Description: 本地缓存
 */
public class LocalCacheUtils {

    private static volatile LocalCacheUtils instance;

    public static LocalCacheUtils getInstance() {
        synchronized (LocalCacheUtils.class) {
            if (instance == null) {
                instance = new LocalCacheUtils();
            }
            return instance;
        }
    }

    public String getPin() {
        return SharedPreferencesUtil.getString("pin", "");
    }

    public void setPin(String pin) {
        SharedPreferencesUtil.putString("pin", pin);
    }
}
