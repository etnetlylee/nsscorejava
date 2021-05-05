package com.etnet.coresdk.coreInterfaceLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureModule {
    String _module;
    List<String> _item;

    public FeatureModule(String module, List<String> item) {
        this._module = module;
        this._item = item;
    }

//    factory FeatureModule.fromJson(dynamic json) {
//        if (json == null) {
//            return new FeatureModule(
//                    json['module'] as String, List.from(json['item']));
//        } else {
//            return null;
//        }
//    }

    public String module() {
        return this._module;
    }

    public List<String> item() {
        return this._item;
    }
}
