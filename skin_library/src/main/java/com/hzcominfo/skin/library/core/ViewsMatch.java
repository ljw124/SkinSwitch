package com.hzcominfo.skin.library.core;

/**
 * 控件匹配接口，仅用于匹配后的执行方法
 * ①换肤控件回调此接口方法（SkinActivity轮询子View时）;
 * ②对应的自定义控件实现此接口方法，具体实现换肤功能（如SkinnableButton类）。
 */
public interface ViewsMatch {

    /**
     * 控件换肤
     */
    void skinnableView();
}
