package ai.skymind.skil.examples;

import ai.skymind.skil.examples.endpoints.Authorization;
import ai.skymind.skil.examples.endpoints.Deployment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) {
        /*---------------------------------------------------------------------------------*/
        /*--------------------------------AUTH ENDPOINTS-----------------------------------*/
        /*---------------------------------------------------------------------------------*/
        Authorization authorization = new Authorization();

        String authToken = authorization.getAuthToken("admin", "admin");
        System.out.println(MessageFormat.format("Auth Token: {0}", authToken));

        /*---------------------------------------------------------------------------------*/
        /*--------------------------------DEPLOYMENT ENDPOINTS-----------------------------*/
        /*---------------------------------------------------------------------------------*/
        Deployment deployment = new Deployment();

        //There can be only 2 deployments in SKIL CE
        JSONObject addedDeployment = deployment.addDeployment("New deployment");
        System.out.println(addedDeployment.toString(4));

        JSONArray deployments = deployment.getAllDeployments();
        System.out.println(deployments.toString(4));

        for (int i = 0; i < deployments.length(); i++) {
            int id = deployments.getJSONObject(i).getInt("id");
            JSONObject deploymentById = deployment.getDeploymentById(id);
            System.out.println(deploymentById.toString(4));
        }

        for (int i = 0; i < deployments.length(); i++) {
            int id = deployments.getJSONObject(i).getInt("id");
            JSONArray modelsForDeployment = deployment.getModelsForDeployment(id);
            System.out.println(modelsForDeployment.toString(4));
        }
    }
}