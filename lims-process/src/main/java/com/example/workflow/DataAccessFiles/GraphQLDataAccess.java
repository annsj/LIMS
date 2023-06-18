package com.example.workflow.DataAccessFiles;

import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;

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
    public Elisa updateElisaStatus(int elisaId, String status) throws IOException, InterruptedException {
        //ELISAns status uppdateras i databasen, svaret innehåller ELISAn och dess tester.
        String query = "{\"query\":\"mutation{updateElisaStatus(elisaId:" + elisaId + ",status:\\\"" + status + "\\\"){elisa{id,status,tests{id,sampleId,elisaId,elisaPlatePosition,status,sample{id,name}}}}}\"}";
        String query2 = "{\"query\":\"mutation{updateElisaStatus(elisaId:" + elisaId + ",status:\\\"In Review\\\"){elisa{id,status,tests{id,sampleId,elisaId,elisaPlatePosition,status,sample{id,name}}}}}\"}";
        JSONObject response = graphQLClient.sendQuery(query);

        JSONObject elisaJson = response.getJSONObject("data").getJSONObject("updateElisaStatus").getJSONObject("elisa");
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Elisa elisa = objectMapper.readValue(elisaJson.toString(), new TypeReference<>() {});

        return elisa;
    }

    @Override
    public JSONObject saveElisaResult(Elisa elisa, String elisaStatus, String testStatus) throws IOException, InterruptedException {
        String query = "{\"query\":\"mutation{saveElisaResult(elisaInput:" + "{id:" + elisa.getId() + ",status:\\\"" + elisaStatus + "\\\",testInputs:" + getTestString(elisa.getTests(), testStatus) + "}){elisa{id,status,tests{id,elisaPlatePosition,sampleId,elisaId,status,measureValue,concentration,sample{id,name,concentration}}}}}\"}";

        JSONObject response = graphQLClient.sendQuery(query);

        JSONObject elisaJson = response
                .getJSONObject("data")
                .getJSONObject("saveElisaResult")
                .getJSONObject("elisa");

        return elisaJson;
    }

    private String getTestString(ArrayList<Test> tests, String status){

        String s = "[";

        for (Test test : tests) {
            s+= "{id:" + test.getId();
            s+= ",concentration:" + test.getConcentration();
            s+= ",measureValue:" + test.getMeasureValue();
            s+= ",status:\\\"" + status + "\\\"},";
        }

        s += "]";

        return s;
    }

}
