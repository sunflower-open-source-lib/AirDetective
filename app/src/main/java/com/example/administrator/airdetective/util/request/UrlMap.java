package com.example.administrator.airdetective.util.request;

import java.util.HashMap;
import java.util.Map;

public class UrlMap {
    private Map<String, String> urlMap;

    private static UrlMap url;
    private String urlHead = "http://lg32.natapp1.cc";
//    private String urlHead = "http://192.168.199.100:8080";
//private String urlHead = "http://www.gavinwang.cn:8080";
//    private String urlHead = "http://gdqkf9.natappfree.cc";

    private UrlMap() {
        urlMap = new HashMap<> ();
        initMap ();
    }

    public static UrlMap getUrlMap(){
        if (url == null) {
            url = new UrlMap ();
            return url;
        }
        else
            return url;
    }

    private void initMap() {
        urlMap.put ( "login", urlHead + "/login/" );
        urlMap.put ( "register", urlHead + "/register" );
        urlMap.put ( "getFamilyInfo", urlHead + "/getmyfamilies/" );
        urlMap.put ( "createFamily", urlHead + "/createfamily/" );
        urlMap.put ( "joinFamily", urlHead + "/joinfamily/" );
        urlMap.put ( "getAirInfo", urlHead + "/getairinfo/" );
        urlMap.put ( "addRoom", urlHead + "/addroom/" );
        urlMap.put ( "getDailyAirInfo", urlHead + "/getdayilyairinfo" );
        urlMap.put ( "getTimingAirInfo", urlHead + "/gettimingairinfo" );
        urlMap.put ( "getDeviceDataAirInfo", urlHead + "/getdevicedatainfo" );
    }

    public String getUrl(String key) {
        return urlMap.get ( key );
    }
}