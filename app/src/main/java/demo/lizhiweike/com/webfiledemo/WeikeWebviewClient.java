package demo.lizhiweike.com.webfiledemo;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Project Name:webFileDemo
 * Package Name:demo.lizhiweike.com.webfiledemo
 * Created by Administrator on 2017/8/30 0030 11:12 .
 * <p>
 * Copyright (c) 2016—2017 https://www.lizhiweike.com all rights reserved.
 */
public class WeikeWebviewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        return super.shouldOverrideUrlLoading(webView, s);
    }
}
