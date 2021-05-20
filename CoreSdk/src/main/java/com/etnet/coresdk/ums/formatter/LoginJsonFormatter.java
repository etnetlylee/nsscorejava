package com.etnet.coresdk.ums.formatter;

import com.etnet.coresdk.ums.data.EtnetProductUser;
import com.etnet.coresdk.ums.utilities.CommonFunction;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginJsonFormatter extends AbstructJsonFormatter {
    public static boolean parse(String JSONString, EtnetProductUser etnetProductUser) {
        boolean flag = false;
        try {
            if(StringUtils.isNotBlank(JSONString)){
                System.out.println("JSONString: " + JSONString);
                JSONObject jsonObject = new JSONObject(JSONString);
                if (jsonObject != null) {
                    String errCode = jsonObject.getJSONObject("error").getString("errCode");
                    if (CommonFunction.statusCodeSuccess.equals(errCode)){
                        etnetProductUser.setToken((String) getJsonObject(jsonObject, "token", String.class));
                        etnetProductUser.setSessionId((String) getJsonObject(jsonObject, "sessionId", String.class));
                        etnetProductUser.setMoudleId((String) getJsonObject(jsonObject, "productid", String.class));
                        etnetProductUser.setPackageToken((String) getJsonObject(jsonObject, "package", String.class));
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static String parseGetModules(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            if(obj.has("valid") && "Y".equalsIgnoreCase(obj.getString("valid"))){
                if(obj.has("modules")){
                    return obj.getJSONArray("modules").toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray().toString();
    }
}
