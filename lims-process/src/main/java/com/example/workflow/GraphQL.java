package com.example.workflow;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GraphQL {

    private static final String LIMS_API_URL = "http://localhost:5000/graphql/";
    private HttpClient client = HttpClient.newHttpClient();


    public JSONObject sendQuery(String query) throws IOException, InterruptedException {

        //String q = "{\"query\":\"mutation{addElisa{elisa{id,status,dateAdded}}}\\n\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LIMS_API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(query))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject responseJson = new JSONObject(response.body());

        return responseJson;
    }
}
