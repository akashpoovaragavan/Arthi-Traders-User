package com.arthi.traders.constant;

import org.json.JSONException;
import org.json.JSONObject;

public class StringParser {


    public static String getCode(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            return String.valueOf(jsonObject.getString(Template.Query.KEY_CODE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMessage(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(Template.Query.KEY_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}