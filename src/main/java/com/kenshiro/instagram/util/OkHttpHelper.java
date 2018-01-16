package com.kenshiro.instagram.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kenshiro.instagram.json.CardDetailJson;
import com.kenshiro.instagram.json.PageJson;
import com.kenshiro.instagram.json.SharedDataJson;
import com.kenshiro.instagram.json.VideoDetailJson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OkHttpHelper {

    private static final Pattern PATTERN = Pattern.compile("<script type=\"text/javascript\">window._sharedData = (.*);</script>");
    private static final Gson gson = new Gson();
    private static final String picRegex = "/\\w\\d*x\\d*";

    private static OkHttpClient client;
    static {
        client = new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.SOCKS,
                new InetSocketAddress(8081))).build();
    }

    public static OkHttpClient getClient() {
        return client;
    }

    private OkHttpHelper() {}

    public static SharedDataJson.EntryData.ProfilePage.User.Media getMainPageMedia(String url) throws IOException {
        SharedDataJson sharedData = getSharedData(url);
        return sharedData.getEntry_data().
                getProfilePage().get(0).getUser().getMedia();
    }

    public static SharedDataJson getSharedData(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        String html;
        try (Response response = client.newCall(request).execute()) {
            html = response.body().string();
        }
        Matcher matcher = PATTERN.matcher(html);
        matcher.find();
        SharedDataJson sharedDataJson = gson.fromJson(matcher.group(1), SharedDataJson.class);
        return sharedDataJson;
    }

    public static PageJson getPageJson(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        String html;
        try (Response response = client.newCall(request).execute()) {
            html = response.body().string();
        }
        return gson.fromJson(html, PageJson.class);
    }

    public static VideoDetailJson getDetailJson(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        String html;
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 404) {
                return null;
            }
            html = response.body().string();
        }
        return gson.fromJson(html, VideoDetailJson.class);
    }

    public static CardDetailJson getCardDetailJson(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        String html;
        try (Response response = client.newCall(request).execute()) {
            html = response.body().string();
        }
        try {
            return gson.fromJson(html, CardDetailJson.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
//        OkHttpHelper.getMainPagePics("https://www.instagram.com/katherinebaby.hjx/");
    }
}
