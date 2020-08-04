package com.hzcominfo.skin.library;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import com.hzcominfo.skin.library.core.CustomAppCompatViewInflater;
import com.hzcominfo.skin.library.core.ViewsMatch;
import com.hzcominfo.skin.library.utils.ActionBarUtils;
import com.hzcominfo.skin.library.utils.NavigationUtils;
import com.hzcominfo.skin.library.utils.StatusBarUtils;

/**
 * 换肤Activity父类
 *
 * 用法：
 * 1、继承此类
 * 2、重写openChangeSkin()方法
 */
public class SkinActivity extends AppCompatActivity {

    private CustomAppCompatViewInflater viewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //在父类的onCreate之前设置Factory2，具体作用参考源码：super.onCreate -> delegate.installViewFactory() ->AppCompatDelegateImpl-installViewFactory
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //判断是否换肤
        if (openChangeSkin()) {
            if (viewInflater == null) {
                viewInflater = new CustomAppCompatViewInflater(context); //自定义的控件加载器
            }
            //设置控件名字和属性
            viewInflater.setName(name);
            viewInflater.setAttrs(attrs);
            //调用控件匹配方法
            return viewInflater.autoMatch();
        }
        //如果开发者关闭换肤功能，使用父类的方法
        return super.onCreateView(parent, name, context, attrs);
    }

    /**
     * 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     */
    protected boolean openChangeSkin() {
        return false;
    }

    /**
     * 开放给开发者设置 日间/夜间 模式的接口
     * @param nightMode 日间/夜间 模式
     */
    protected void setDayNightMode(@AppCompatDelegate.NightMode int nightMode) {

        final boolean isPost21 = Build.VERSION.SDK_INT >= 21;

        getDelegate().setLocalNightMode(nightMode);

        if (isPost21) {
            // 换状态栏
            StatusBarUtils.forStatusBar(this);
            // 换标题栏
            ActionBarUtils.forActionBar(this);
            // 换底部导航栏
            NavigationUtils.forNavigation(this);
        }

        //获取当前页面
        View decorView = getWindow().getDecorView();
        //回调控件换肤接口
        applyDayNightForView(decorView);
    }

    /**
     * 回调接口 具体控件实现换肤操作
     */
    protected void applyDayNightForView(View view) {
        if (view instanceof ViewsMatch) {
            ViewsMatch viewsMatch = (ViewsMatch) view;
            viewsMatch.skinnableView();
        }
        //如果是ViewGroup递归取出子View
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applyDayNightForView(parent.getChildAt(i));
            }
        }
    }
}
