package com.example.workflow.DataAccessFiles;

import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import org.json.JSONObject;

import java.io.IOException;

public class GraphQLDataAccess implements IDataAccess{

    private GraphQLClient graphQLClient = new GraphQLClient();

    @Override
    public int postElisa() throws IOException, InterruptedException {

        String query = "{\"query\":\"mutation{addElisa{elisa{id,status,dateAdded}}}\"}";
        JSONObject response = graphQLClient.sendQuery(query);

        int id = response.getJSONObject("data")
                .getJSONObject("addElisa")
                .getJSONObject("elisa")
                .getInt("id");

        return id;
    }

    @Override
    public Test postTest(int sampleId, String sampleName, int position, int elisaId)
            throws IOException, InterruptedException {

        String query = "{\"query\":\"mutation{addTest(input:{" +
                "sampleId:" + sampleId +
                ",elisaPlatePosition:" + position +
                ",elisaId:" + elisaId +
                "}){test{id,sampleId,elisaId,elisaPlatePosition,status,dateAdded}}}\"}";

        JSONObject response = graphQLClient.sendQuery(query);

        JSONObject testJson = response.getJSONObject("data")
                .getJSONObject("addTest")
                .getJSONObject("test");

        int id = testJson.getInt("id");
        String status = testJson.getString("status");

        //TODO: get values from response
        Test test = new Test(id, sampleId, sampleName, elisaId, position, status);

        return test;

    }

    @Override
    public Elisa updateElisaStatus(int elisaId) {
        return null;
    }

    @Override
    public JSONObject saveElisaResult(Elisa elisa, String elisaStatus, String testStatus) {
        return null;
    }

}
