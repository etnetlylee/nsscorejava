package com.etnet.coresdk.ums.utilities;

import android.content.Context;

import androidx.annotation.NonNull;

import com.etnet.coresdk.ums.InitWebService;
import com.etnet.coresdk.ums.controller.LoginController;
import com.etnet.coresdk.ums.controller.QuoteController;
import com.etnet.coresdk.ums.data.EtnetProductUser;

public class EtnetProductCoreUtil{
    LoginController loginController = new LoginController();
    QuoteController quoteController = new QuoteController();
    EtnetProductUser etnetProductUser = new EtnetProductUser();

    public EtnetProductCoreUtil() {
        init();
    }

    public EtnetProductCoreUtil(@NonNull Context refContext) {
//        mContext = refContext;
//        sdkInit(refCallBackDataType);
//        nssInit(userName, userPassword);
//        dataReceiveHandler(refCallBackDataType);
    }

    public EtnetProductUser getEtnetProductUser() {
        return etnetProductUser;
    }

    public void setEtnetProductUser(EtnetProductUser etnetProductUser) {
        this.etnetProductUser = etnetProductUser;
    }

    public void init(){
        InitWebService.init();
    }

    public String login(String userId) {
        etnetProductUser.setCompanyId(InitWebService.getConfig("COMPANYCODE"));
        etnetProductUser.setUserId(userId);
        String jsonString = loginController.login(etnetProductUser);
        return jsonString;
    }

    public String getQuote(String stockCode) {
        String jsonString = quoteController.getQuote(etnetProductUser, stockCode);
        return jsonString;
    }
}
