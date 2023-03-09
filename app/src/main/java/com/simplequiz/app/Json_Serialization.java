package com.simplequiz.app;


public class Json_Serialization {
    DataHeader dataHeader;
    SecurityHeader securityHeader;
    DataPayload dataPayload;

    // Getter Methods

    public DataHeader getDataHeader() {
        return dataHeader;
    }

    public SecurityHeader getSecurityHeader() {
        return securityHeader;
    }

    public DataPayload getDataPayload() {
        return dataPayload;
    }

    // Setter Methods

    public void setDataHeader(DataHeader dataHeaderObject) {
        this.dataHeader = dataHeaderObject;
    }

    public void setSecurityHeader(SecurityHeader securityHeaderObject) {
        this.securityHeader = securityHeaderObject;
    }

    public void setDataPayload(DataPayload dataPayloadObject) {
        this.dataPayload = dataPayloadObject;
    }
}
