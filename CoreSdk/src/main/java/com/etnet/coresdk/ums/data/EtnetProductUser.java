package com.etnet.coresdk.ums.data;

public class EtnetProductUser {
    private String userId;
    private String companyId;
    private String sessionId;
    private String token;
    private String packageToken;
    private String moudleId;

    public EtnetProductUser() {}

    public EtnetProductUser(String userId, String companyId, String sessionId, String token, String packageToken) {
        this.userId = userId;
        this.companyId = companyId;
        this.sessionId = sessionId;
        this.token = token;
        this.packageToken = packageToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPackageToken() {
        return packageToken;
    }

    public void setPackageToken(String packageToken) {
        this.packageToken = packageToken;
    }

    public String getMoudleId() {
        return moudleId;
    }

    public void setMoudleId(String moudleId) {
        this.moudleId = moudleId;
    }

    @Override
    public String toString() {
        return "EtnetProductUser{" +
                "userId='" + userId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", token='" + token + '\'' +
                ", packageToken='" + packageToken + '\'' +
                ", moudleId='" + moudleId + '\'' +
                '}';
    }
}
