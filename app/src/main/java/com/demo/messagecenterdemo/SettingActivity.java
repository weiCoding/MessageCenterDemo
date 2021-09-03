package com.demo.messagecenterdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.jingdong.app.mall.bundle.CommonMessageCenter.MsgCenterConfig;
import com.jingdong.app.mall.bundle.CommonMessageCenter.config.MsgCenterThemeConfig;
import com.jingdong.app.mall.bundle.CommonMessageCenter.utils.MsgCenterConfigUtils;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("消息中心配置");
        ImageView imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initLoginView();
        initThemeView();
        initNetwork();
    }

    private void initLoginView() {

        Switch switchLogin = findViewById(R.id.switch_login);
        switchLogin.setChecked(MsgCenterConfig.getInstance().isShowLogin());
        switchLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MsgCenterConfig.getInstance().setShowLogin(isChecked);
            }
        });
    }

    private void initThemeView() {

        Switch switchTheme = findViewById(R.id.switch_theme);
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MsgCenterConfigUtils.getInstance().setThemeConfig(
                            MsgCenterThemeConfig.build()
                                    .setThemeColor(getResources().getColor(R.color.red))
                                    .setHeaderBg(ResourcesCompat.getDrawable(getResources(), R.drawable.message_main_head_bg_red, null))
                                    .setShowTitle(true)
                                    .setShowClearBtn(true)
                                    .setShowSettingBtn(true)
                                    .setShowBackBtn(true));
                } else {
                    MsgCenterConfigUtils.getInstance().setThemeConfig(MsgCenterThemeConfig.build().
                            setThemeColor(getResources().getColor(R.color.theme_color)).
                            setHeaderBg(ResourcesCompat.getDrawable(getResources(), R.drawable.message_main_head_bg, null)));
                }
            }
        });
    }

    private void initNetwork() {

        Switch switchTheme = findViewById(R.id.switch_network);
        switchTheme.setChecked(MsgCenterConfig.getInstance().isLocalOrNetwork());
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MsgCenterConfig.getInstance().setLocalOrNetwork(isChecked);
            }
        });
    }
}