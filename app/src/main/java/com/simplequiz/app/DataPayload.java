package com.simplequiz.app;

        import java.util.ArrayList;
        import java.util.HashMap;

public class DataPayload {

    private String apiMethodName;
    ArrayList<Object> mainParameters = new ArrayList<>();
    ArrayList<Object> subParameters = new ArrayList<>();
    HashMap<String, String> keyValueParameters = new HashMap<>();

    public String getApiMethodName() {
        return apiMethodName;
    }

    public void setApiMethodName(String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    public ArrayList<Object> getMainParameters() {
        return mainParameters;
    }

    public void setMainParameters(ArrayList<Object> mainParameters) {
        this.mainParameters = mainParameters;
    }

    public ArrayList<Object> getSubParameters() {
        return subParameters;
    }

    public void setSubParameters(ArrayList<Object> subParameters) {
        this.subParameters = subParameters;
    }

    public HashMap<String, String> getKeyValueParameters() {
        return keyValueParameters;
    }

    public void setKeyValueParameters(HashMap<String, String> keyValueParameters) {
        this.keyValueParameters = keyValueParameters;
    }
}
