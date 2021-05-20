package com.etnet.coresdk.ums.service;

import com.etnet.coresdk.ums.InitWebService;
import com.etnet.coresdk.ums.data.EtnetProductUser;
import com.etnet.coresdk.ums.utilities.HttpConnect;

import org.apache.commons.lang3.StringUtils;

public class QuoteService {
    public static final String QUOTE = "Feed/Quote";

    public String getQuote(EtnetProductUser etnetProductUser, String codeList){
        System.out.println("Enter SDK getQuote");
        // Config level
        HttpConnect.init("300", "150", "5000", "");
        String url = InitWebService.getConfig("ETNETPRODUCT");
        String companyId = InitWebService.getConfig("COMPANYCODE");
        String action = QUOTE;
        String userId = etnetProductUser.getUserId();
        String sessionId = etnetProductUser.getSessionId();

        //1.	/etnetProduct/Feed/Quote?company=CPY&userId=00001&sessionId=XYZ&code=1&groupId=rt&level=2&quoteMeter=N

        // Real time Snapshot
        String reqParam = "company=" + companyId + "&userId=" + userId + "&sessionId=" + sessionId + "&code=" + codeList + "&groupId=rt&level=1&quoteMeter=Y";
        System.out.println("Feed API reqParam: " + reqParam);
        String jsonString = HttpConnect.executePost(url, action, reqParam, 5000);
//        String jsonString = HttpConnect.executePostByHttpURLConnection(tempUrl);

        // Extract JsonObject
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                // TODO: parse Quote JSON
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

    public String getRTQuote(EtnetProductUser etnetProductUser, String codeList, String market, String level){
        System.out.println("Enter SDK getQuote");
        // Config level
        HttpConnect.init("300", "150", "5000", "");
        String url = InitWebService.getConfig("ETNETPRODUCT");
        String companyId = InitWebService.getConfig("COMPANYCODE");
        String action = QUOTE;
        String userId = etnetProductUser.getUserId();
        String sessionId = etnetProductUser.getSessionId();

        //1.	/etnetProduct/Feed/Quote?company=CPY&userId=00001&sessionId=XYZ&code=1&groupId=rt&level=2&quoteMeter=N

        // Real time Snapshot
        String reqParam = "company=" + companyId + "&userId=" + userId + "&sessionId="
                + sessionId + "&code=" + codeList + "&groupId=rt&level=" + level + "&quoteMeter=Y";
        System.out.println("Feed API reqParam: " + reqParam);
        String jsonString = HttpConnect.executePost(url, action, reqParam, 5000);
//        String jsonString = HttpConnect.executePostByHttpURLConnection(tempUrl);

        // Extract JsonObject
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                // TODO: parse Quote JSON
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

    public String getDLQuote(EtnetProductUser etnetProductUser, String codeList, String market, String level){
        System.out.println("Enter SDK getQuote");
        // Config level
        HttpConnect.init("300", "150", "5000", "");
        String url = InitWebService.getConfig("ETNETPRODUCT");
        String companyId = InitWebService.getConfig("COMPANYCODE");
        String action = QUOTE;
        String userId = etnetProductUser.getUserId();
        String sessionId = etnetProductUser.getSessionId();

        //1.	/etnetProduct/Feed/Quote?company=CPY&userId=00001&sessionId=XYZ&code=1&groupId=rt&level=2&quoteMeter=N

        // Real time Snapshot
        String reqParam = "company=" + companyId + "&userId=" + userId + "&sessionId=" + sessionId + "&code=" + codeList + "&groupId=dl&level=1&quoteMeter=Y";
        System.out.println("Feed API reqParam: " + reqParam);
        String jsonString = HttpConnect.executePost(url, action, reqParam, 5000);
//        String jsonString = HttpConnect.executePostByHttpURLConnection(tempUrl);

        // Extract JsonObject
        if (StringUtils.isNotEmpty(jsonString)) {
            try {
                // TODO: parse Quote JSON
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }
}
