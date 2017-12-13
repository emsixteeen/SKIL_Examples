package ai.skymind.skil.examples.endpoints;

import org.json.JSONObject;

public class Model {

    private String host;
    private String port;

    public Model() {
        this.host = "localhost";
        this.port = "9008";
    }

    public Model(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public JSONObject addModelToDeployment(int deploymentID /*, model details*/) {
        return null;
    }
}
