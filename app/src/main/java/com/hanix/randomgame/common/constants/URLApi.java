package com.hanix.randomgame.common.constants;

import com.hanix.randomgame.common.app.RandomApplication;

public class URLApi {

    public static final String PLAY_STORE_REAL =  AppConstants.STORE_URL_GOOGLE + "com.union.closetclient";
    public static final String PLAY_STORE_DEV = AppConstants.STORE_URL_GOOGLE + "com.union.closetclient";

    /** 서버 url **/
    // url 취득

    public static String getStoreURL() {
        if(RandomApplication.getInstance().isDebuggable) {
            return PLAY_STORE_DEV;
        }
        return PLAY_STORE_REAL;
    }
}
