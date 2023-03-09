package com.simplequiz.app;

public class SecurityHeader {
    private String userID;
    private String deviceOrProgramID;
    private String token;
    private Boolean isEncrypted;
    private String hashValue;
    private String apiVersionNumber;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeviceOrProgramID() {
        return deviceOrProgramID;
    }

    public void setDeviceOrProgramID(String deviceOrProgramID) {
        this.deviceOrProgramID = deviceOrProgramID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    public String getApiVersionNumber() {
        return apiVersionNumber;
    }

    public void setApiVersionNumber(String apiVersionNumber) {
        this.apiVersionNumber = apiVersionNumber;
    }

}
