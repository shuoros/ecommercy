package io.github.shuoros.ecommercy.control.util;

import org.json.JSONObject;

public class Request {

    public static JSONObject deserialize(String request) {
        JSONObject json;
        try {
            json = new JSONObject(request);
        } catch (Exception e) {
            return null;
        }
        return json;
    }

}
