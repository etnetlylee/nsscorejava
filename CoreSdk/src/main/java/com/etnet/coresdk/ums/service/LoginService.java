package com.etnet.coresdk.ums.service;

import com.etnet.coresdk.ums.InitWebService;
import com.etnet.coresdk.ums.data.EtnetProductUser;
import com.etnet.coresdk.ums.formatter.LoginJsonFormatter;
import com.etnet.coresdk.ums.utilities.AESUtils;
import com.etnet.coresdk.ums.utilities.CommonFunction;
import com.etnet.coresdk.ums.utilities.HttpConnect;

import org.apache.commons.lang3.StringUtils;

public class LoginService {

    public String login(EtnetProductUser etnetProductUser){
        System.out.println("Enter SDK login");
        // Config level
        HttpConnect.init("300", "150", "5000", "");
        String url = InitWebService.getConfig("ETNETPRODUCT");
        String companyId = etnetProductUser.getCompanyId();
        String key = InitWebService.getConfig("AESKEY");
        String action = "User/VerifyUser";
        String userId = etnetProductUser.getUserId();
        String unencryptedString = "userId=" + userId + "&services=FEEDAPP&timestamp=" + CommonFunction.getTimestamp();
        AESUtils aesUtils = new AESUtils();
        aesUtils.load(key);
        String token = aesUtils.encrypt(unencryptedString);

        String reqParam = "company=" + companyId + "&token=" + token;
        String jsonString = HttpConnect.executePost(url, action, reqParam, 5000);
//        String jsonString = HttpConnect.executePostByHttpURLConnection(tempUrl);

        // Extract JsonObject
        if (StringUtils.isNotEmpty(jsonString)) {
           try {
               LoginJsonFormatter.parse(jsonString, etnetProductUser);
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
        System.out.println("etnetProductUser: " + etnetProductUser.toString());
        return jsonString;
    }
}
