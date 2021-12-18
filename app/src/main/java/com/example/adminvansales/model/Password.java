package com.example.adminvansales.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Password {
    String PASSWORDTYPE;
    String USER_PASSWORD;

    public String getPASSWORDTYPE() {
        return PASSWORDTYPE;
    }

    public void setPASSWORDTYPE(String PASSWORDTYPE) {
        this.PASSWORDTYPE = PASSWORDTYPE;
    }

    public String getUSER_PASSWORD() {
        return USER_PASSWORD;
    }

    public void setUSER_PASSWORD(String USER_PASSWORD) {
        this.USER_PASSWORD = USER_PASSWORD;
    }

    public JSONObject getJsonObject(){

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("USER_PASSWORD", USER_PASSWORD);
            jsonObject.put("PASSWORDTYPE", PASSWORDTYPE);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
