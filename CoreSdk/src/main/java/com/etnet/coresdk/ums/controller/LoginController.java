package com.etnet.coresdk.ums.controller;

import com.etnet.coresdk.ums.data.EtnetProductUser;
import com.etnet.coresdk.ums.service.LoginService;

public class LoginController {
    LoginService loginService = new LoginService();

    public String login(EtnetProductUser etnetProductUser){
        String jsonString = loginService.login(etnetProductUser);
        return jsonString;
    }
}
