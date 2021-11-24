package com.hanix.randomgame.common.constants;

import com.hanix.randomgame.common.app.RandomApplication;

public class URLApi {

    public static final String  SERVER_URL = "";

    public static final String PLAY_STORE_REAL =  AppConstants.STORE_URL_GOOGLE + "com.hanix.randomgame";
    public static final String PLAY_STORE_DEV = AppConstants.STORE_URL_GOOGLE + "com.hanix.randomgame";

    /** 서버 url **/
    // url 취득

    public static String getStoreURL() {
        if(RandomApplication.getInstance().isDebuggable) {
            return PLAY_STORE_DEV;
        }
        return PLAY_STORE_REAL;
    }

    public static String getServerUrl( ) {
        if(RandomApplication.getInstance().isDebuggable) {
            return SERVER_URL;
        }
        return "";
    }


}
