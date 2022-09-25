package com.example.workflow;

import com.example.workflow.Models.DaoModels.Elisa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

  public static void main(String... args) throws IOException, InterruptedException {
    SpringApplication.run(Application.class, args);

//    String resultVariable = "{\"pos\":1,\"value\":0.45};{\"pos\":2,\"value\":1.02};{\"pos\":73,\"value\":0.11};{\"pos\":74,\"value\":0.165};{\"pos\":75,\"value\":0.247};{\"pos\":76,\"value\":0.371};{\"pos\":77,\"value\":0.556};{\"pos\":78,\"value\":0.835};{\"pos\":79,\"value\":1.252};{\"pos\":80,\"value\":1.879};{\"pos\":81,\"value\":0.12};{\"pos\":82,\"value\":0.175};{\"pos\":83,\"value\":0.257};{\"pos\":84,\"value\":0.381};{\"pos\":85,\"value\":0.566};{\"pos\":86,\"value\":0.845};{\"pos\":87,\"value\":1.262};{\"pos\":88,\"value\":1.889};{\"pos\":89,\"value\":0.09};{\"pos\":90,\"value\":0.145};{\"pos\":91,\"value\":0.227};{\"pos\":92,\"value\":0.351};{\"pos\":93,\"value\":0.536};{\"pos\":94,\"value\":0.815};{\"pos\":95,\"value\":1.232};{\"pos\":96,\"value\":1.859}";
//    String standardsDataVariable = "[{\"position\":73,\"concentration\":2.0,\"measValue\":0.11},{\"position\":74,\"concentration\":3.0,\"measValue\":0.165},{\"position\":75,\"concentration\":4.5,\"measValue\":0.247},{\"position\":76,\"concentration\":6.75,\"measValue\":0.371},{\"position\":77,\"concentration\":10.1,\"measValue\":0.556},{\"position\":78,\"concentration\":15.2,\"measValue\":0.835},{\"position\":79,\"concentration\":22.8,\"measValue\":1.252},{\"position\":80,\"concentration\":34.2,\"measValue\":1.879},{\"position\":81,\"concentration\":2.0,\"measValue\":0.12},{\"position\":82,\"concentration\":3.0,\"measValue\":0.175},{\"position\":83,\"concentration\":4.5,\"measValue\":0.257},{\"position\":84,\"concentration\":6.75,\"measValue\":0.381},{\"position\":85,\"concentration\":10.1,\"measValue\":0.566},{\"position\":86,\"concentration\":15.2,\"measValue\":0.845},{\"position\":87,\"concentration\":22.8,\"measValue\":1.262},{\"position\":88,\"concentration\":34.2,\"measValue\":1.889},{\"position\":89,\"concentration\":2.0,\"measValue\":0.08},{\"position\":90,\"concentration\":3.0,\"measValue\":0.135},{\"position\":91,\"concentration\":4.5,\"measValue\":0.217},{\"position\":92,\"concentration\":6.75,\"measValue\":0.341},{\"position\":93,\"concentration\":10.1,\"measValue\":0.526},{\"position\":94,\"concentration\":15.2,\"measValue\":0.805},{\"position\":95,\"concentration\":22.8,\"measValue\":1.222},{\"position\":96,\"concentration\":34.2,\"measValue\":1.849}]";
//    String samplesDataVariable = "[{\"pos\":1,\"sampleId\":3,\"name\":\"Prov3\",\"measValue\":0.53},{\"pos\":2,\"sampleId\":4,\"name\":\"Prov4\",\"measValue\":0.17}]";
//    String query = "{\"query\":\"query{elisas(where:{id:{eq:" + elisaId + "}}){id,tests{id,sampleId,elisaId,elisaPlatePosition,status,sample{id,name}}}}\"}";
//    String testsVariable = "[{\"id\":138,\"sampleId\":3,\"elisaId\":100,\"sampleName\":\"Prov3\",\"measureValue\":0.0,\"concentration\":0.0,\"platePosition\":1,\"status\":\"In Progress\"},{\"id\":139,\"sampleId\":4,\"elisaId\":100,\"sampleName\":\"Prov4\",\"measureValue\":0.0,\"concentration\":0.0,\"platePosition\":2,\"status\":\"In Progress\"}]";
//    String plateVariable = "{\"elisaId\":124,\"wells\":[{\"pos\":1,\"wellName\":\"A1\",\"reagent\":\"Prov3\"},{\"pos\":2,\"wellName\":\"B1\",\"reagent\":\"Prov4\"},{\"pos\":73,\"wellName\":\"A10\",\"reagent\":\"0.25 ug\\\\ml\"},{\"pos\":74,\"wellName\":\"B10\",\"reagent\":\"0.5 ug\\\\ml\"},{\"pos\":75,\"wellName\":\"C10\",\"reagent\":\"1.0 ug\\\\ml\"},{\"pos\":76,\"wellName\":\"D10\",\"reagent\":\"2.0 ug\\\\ml\"},{\"pos\":77,\"wellName\":\"E10\",\"reagent\":\"4.0 ug\\\\ml\"},{\"pos\":78,\"wellName\":\"F10\",\"reagent\":\"8.0 ug\\\\ml\"},{\"pos\":79,\"wellName\":\"G10\",\"reagent\":\"16.0 ug\\\\ml\"},{\"pos\":80,\"wellName\":\"H10\",\"reagent\":\"32.0 ug\\\\ml\"},{\"pos\":81,\"wellName\":\"A11\",\"reagent\":\"0.25 ug\\\\ml\"},{\"pos\":82,\"wellName\":\"B11\",\"reagent\":\"0.5 ug\\\\ml\"},{\"pos\":83,\"wellName\":\"C11\",\"reagent\":\"1.0 ug\\\\ml\"},{\"pos\":84,\"wellName\":\"D11\",\"reagent\":\"2.0 ug\\\\ml\"},{\"pos\":85,\"wellName\":\"E11\",\"reagent\":\"4.0 ug\\\\ml\"},{\"pos\":86,\"wellName\":\"F11\",\"reagent\":\"8.0 ug\\\\ml\"},{\"pos\":87,\"wellName\":\"G11\",\"reagent\":\"16.0 ug\\\\ml\"},{\"pos\":88,\"wellName\":\"H11\",\"reagent\":\"32.0 ug\\\\ml\"},{\"pos\":89,\"wellName\":\"A12\",\"reagent\":\"0.25 ug\\\\ml\"},{\"pos\":90,\"wellName\":\"B12\",\"reagent\":\"0.5 ug\\\\ml\"},{\"pos\":91,\"wellName\":\"C12\",\"reagent\":\"1.0 ug\\\\ml\"},{\"pos\":92,\"wellName\":\"D12\",\"reagent\":\"2.0 ug\\\\ml\"},{\"pos\":93,\"wellName\":\"E12\",\"reagent\":\"4.0 ug\\\\ml\"},{\"pos\":94,\"wellName\":\"F12\",\"reagent\":\"8.0 ug\\\\ml\"},{\"pos\":95,\"wellName\":\"G12\",\"reagent\":\"16.0 ug\\\\ml\"},{\"pos\":96,\"wellName\":\"H12\",\"reagent\":\"32.0 ug\\\\ml\"}]}";
//    String elisaVariable = "{\"id\":1188,\"tests\":[{\"id\":1310,\"sampleId\":3,\"elisaId\":1188,\"sampleName\":\"Prov3\",\"measureValue\":0.57,\"concentration\":110.32257,\"platePosition\":1,\"status\":\"In Progress\"},{\"id\":1311,\"sampleId\":4,\"elisaId\":1188,\"sampleName\":\"Prov4\",\"measureValue\":0.17,\"concentration\":32.903225,\"platePosition\":2,\"status\":\"In Progress\"}]}";
//    boolean experimentOkVariable = false;

//    ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    GraphQL graphQL = new GraphQL();
//    int id = 37;
//
//    String query = "{\"query\":\"mutation{updateElisaStatus(elisaId:" + id + ",status:\\\"In Review\\\"){elisa{id,status,tests{id,sampleId,elisaId,elisaPlatePosition,status,sample{id,name}}}}}\"}";
//    JSONObject response = graphQL.sendQuery(query);
//
//    JSONObject elisaJson = response.getJSONObject("data").getJSONObject("updateElisaStatus").getJSONObject("elisa");
//    Elisa elisa = objectMapper.readValue(elisaJson.toString(), new TypeReference<>() {});

  }
}


