package com.example.workflow.DataAccessFiles;

import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import org.json.JSONObject;

import java.io.IOException;

public class GraphQLDataAccess implements IDataAccess{

    private GraphQLClient graphQLClient = new GraphQLClient();

    @Override
    public int createElisa() throws IOException, InterruptedException {

        String query = "{\"query\":\"mutation{addElisa{elisa{id,status,dateAdded}}}\"}";
        JSONObject response = graphQLClient.sendQuery(query);

        int id = response.getJSONObject("data")
                .getJSONObject("addElisa")
                .getJSONObject("elisa")
                .getInt("id");

        return id;
    }

    @Override
    public Test createTest(int sampleId, String sampleName, int position) {
        return null;
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
