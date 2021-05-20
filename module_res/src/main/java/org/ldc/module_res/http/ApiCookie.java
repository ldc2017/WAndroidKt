package org.ldc.module_res.http;

import android.util.Log;

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
        Log.e(info, String.format("saveFromResponse: %s   %s", TAG, info));
        cookieStore.put(url.host(), cookies);

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        Log.e(info, String.format("loadForRequest: %s   %s", TAG, info));
        final List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
