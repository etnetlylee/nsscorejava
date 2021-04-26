package coreInterfaceLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginResponse {
    String _uid;
    String _valid;
    String _mtstoken;
    String _encpwd;
    String _token;
    MultiCxn _multiCxn;
    List<FeatureModule> _modules;
    String _productId;

    public LoginResponse(String uid, String valid, String mtstoken, String encpwd,
                         String token, MultiCxn multiCxn, List<FeatureModule> modules, String productId) {
        this._uid = uid;
        this._valid = valid;
        this._mtstoken = mtstoken;
        this._encpwd = encpwd;
        this._token = token;
        this._multiCxn = multiCxn;
        this._modules = modules;
        this._productId = productId;
    }

    public static LoginResponse fromJson(Map<String, Object> json) {
        List<FeatureModule> tempModules = new ArrayList<FeatureModule>();
        if (json.get("modules") != null) {
            List<FeatureModule> featureModulesList = (List<FeatureModule>) json.get("modules");
            for (FeatureModule module : featureModulesList) {
                final FeatureModule featureModule = module;
                if (featureModule != null) {
                    tempModules.add(featureModule);
                }
            }
        }
        MultiCxn tempMultiCxn = null;
        MultiCxn passMultiCxn = null;
        if (json.get("multiCxn") != null) {
            tempMultiCxn = (MultiCxn) json.get("multiCxn");
            passMultiCxn = new MultiCxn(tempMultiCxn._sector, tempMultiCxn._news, tempMultiCxn._chart);
        }

        return new LoginResponse(
                String.valueOf(json.get("uid")),
                String.valueOf(json.get("valid")),
                String.valueOf(json.get("mtstoken")),
                String.valueOf(json.get("encpw")),
                String.valueOf(json.get("token")),
                passMultiCxn,
                tempModules,
                String.valueOf(json.get("productId"))
        );
    }

    public String getUserId() {
        return this._uid;
    }

    public boolean isValid() {
        return this._valid == "Y" ? true : false;
    }

    public String getMtsToken() {
        return this._mtstoken;
    }

    public String getEncryptedPassword() {
        return this._encpwd;
    }

    public String getToken() {
        return this._token;
    }

    public String getProductId() {
        return this._productId;
    }

}
