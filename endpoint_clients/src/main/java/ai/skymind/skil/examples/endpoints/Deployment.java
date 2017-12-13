package ai.skymind.skil.examples.endpoints;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;

public class Deployment {

    private String host;
    private String port;

    public Deployment() {
        this.host = "localhost";
        this.port = "9008";
    }

    public Deployment(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public JSONArray getAllDeployments() {
        JSONArray deployments = new JSONArray();

        try {
            deployments =
                    Unirest.get(MessageFormat.format("http://{0}:{1}/deployments", host, port))
                            .header("accept", "application/json")
                            .header("Content-Type", "application/json")
                            .asJson()
                            .getBody().getArray();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return deployments;
    }

    public JSONObject getDeploymentById(int id) {
        JSONObject deployment = new JSONObject();

        try {
            deployment =
                    Unirest.get(MessageFormat.format("http://{0}:{1}/deployment/{2}", host, port, id))
                            .header("accept", "application/json")
                            .header("Content-Type", "application/json")
                            .asJson()
                            .getBody().getObject();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return deployment;
    }

    public JSONObject addDeployment(String name) {
        JSONObject addedDeployment = new JSONObject();

        try {
            addedDeployment =
                    Unirest.post(MessageFormat.format("http://{0}:{1}/deployment", host, port))
                            .header("accept", "application/json")
                            .header("Content-Type", "application/json")
                            .body(new JSONObject() //Using this because the field functions couldn't get translated to an acceptable json
                                    .put("name", name)
                                    .toString())
                            .asJson()
                            .getBody().getObject();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return addedDeployment;
    }

    public JSONArray getModelsForDeployment(int id) {
        JSONArray allModelsOfDeployment = new JSONArray();

        try {
            allModelsOfDeployment =
                    Unirest.get(MessageFormat.format("http://{0}:{1}/deployment/{2}/models", host, port, id))
                            .header("accept", "application/json")
                            .header("Content-Type", "application/json")
                            .asJson()
                            .getBody().getArray();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return allModelsOfDeployment;
    }
}
