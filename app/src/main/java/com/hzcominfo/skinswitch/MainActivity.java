package com.hzcominfo.skinswitch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import com.hzcominfo.skin.library.SkinActivity;
import com.hzcominfo.skin.library.utils.PreferencesUtils;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置默认的 日间/夜间 模式
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //根据保存的模式进行设置
        boolean isNight = PreferencesUtils.getBoolean(this, "isNight");
        if (isNight) {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //是否开启换肤模式
    @Override
    protected boolean openChangeSkin() {
        return true;
    }

    public void dayOrNight(View view) {
        //技术当前页面的皮肤模式
        int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //根据当前的皮肤模式进行取反设置
        switch (uiMode){
            case Configuration.UI_MODE_NIGHT_NO: //当前白天模式
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                PreferencesUtils.putBoolean(this, "isNight", true);
                break;
            case Configuration.UI_MODE_NIGHT_YES: //当前夜间模式
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                PreferencesUtils.putBoolean(this, "isNight", false);
                break;
        }
    }
}
