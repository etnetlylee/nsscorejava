package com.etnet.coresdk.coreController;

import java.util.HashMap;
import java.util.Map;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreModel.NssCoreContext;

import static com.etnet.coresdk.constants.AsaConstant.LANG_EN;
import static com.etnet.coresdk.constants.AsaConstant.LANG_SC;
import static com.etnet.coresdk.constants.AsaConstant.LANG_TC;
import static com.etnet.coresdk.language.Languages.LangEn;
import static com.etnet.coresdk.language.Languages.LangSc;
import static com.etnet.coresdk.language.Languages.LangTc;

public class CultureController extends ContextProvider {
    Map<String, String> _currentLang = new HashMap<String, String>();

    @Override
    public void setContext(NssCoreContext context) {}

    public void setLanguage(String lang) {
        switch (lang) {
            case LANG_TC:
                _currentLang = LangTc;
                break;
            case LANG_SC:
                _currentLang = LangSc;
                break;
            case LANG_EN:
                _currentLang = LangEn;
                break;
            default:
                _currentLang = LangEn;
                break;
        }
    }

    public String getWord(String key, String lang) {
        Map<String, String> searchLang = _currentLang;
        if (lang != null) {
            if (lang == LANG_TC) {
                searchLang = LangTc;
            } else if (lang == LANG_SC) {
                searchLang = LangSc;
            } else if (lang == LANG_EN) {
                searchLang = LangEn;
            }
        }

        if (searchLang.containsKey(key)) {
            return searchLang.get(key);
        } else {
            return null;
        }
    }
}
