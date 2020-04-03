package com.ldc.wandroidkt.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class ApiCookie implements CookieJar {
    private static final String TAG = "ApiCookie";
    private static final String info = "Cookie 日志:";
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url.host(), cookies);

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        final List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
