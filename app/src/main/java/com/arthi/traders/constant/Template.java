package com.arthi.traders.constant;

public interface Template {

    interface VolleyRetryPolicy{
        int SOCKET_TIMEOUT = 1000 * 100;
        int RETRIES = 0;
    }

    interface Query{
        String KEY_IMAGE = "image";
        String KEY_DIRECTORY = "directory";
        String VALUE_DIRECTORY = "Uploads";
        String KEY_CODE = "Message";
        String KEY_MESSAGE = "Success";
        String VALUE_CODE_SUCCESS = "Success";
        String VALUE_CODE_FAILED = "1";
        String VALUE_CODE_MISSING = "0";
    }


}
