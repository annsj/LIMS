package com.example.workflow.Delegates;

import com.example.workflow.GraphQL;
import com.example.workflow.Models.*;
import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component("ResultCalculator")
public class ResultCalculator implements JavaDelegate {

    private ArrayList<Test> tests;
    private  ObjectMapper objectMapper;
    private GraphQL graphQL;
    private Elisa elisa;
    private StandardCurve stdCurve;

    public ResultCalculator() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.graphQL = new GraphQL();
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        //Hämta processvariabel elisaId
        int elisaId = Integer.parseInt(delegateExecution.getVariable("elisaId").toString());

        //Spara ny status för Elisan med GraphQL-anrop till API för datahantering, svaret innehåller Elisan med tester
        elisa = updateElisaStatus(elisaId);

        //Hämta processvariabel med rådata för standardkurva, innehåller position, koncentration, mätvärde
        String standardsRawData = delegateExecution.getVariable("standardsData").toString();

        stdCurve = calculateStandardCurve(standardsRawData);

        //Hämta processvariabel med rådata för prover, innehåller position, sampleId, provets namn, mätvärde
        String samplesRawData = delegateExecution.getVariable("samplesData").toString();

        setConcAndMeasValueForElisaTests(samplesRawData);

        //Gör elisa till ett objekt som kan sparas i processen.
        ObjectValue elisaObject = Variables.objectValue(elisa)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        //Spara Elisan med beräknat resultat som processvariabel "elisa".
        delegateExecution.setVariable("elisa", elisaObject);
    }



    private Elisa updateElisaStatus(int elisaId) throws IOException, InterruptedException {

        //ELISAns status uppdateras i databasen, svaret innehåller ELISAn och dess tester.
        String query = "{\"query\":\"mutation{updateElisaStatus(elisaId:" + elisaId + ",status:\\\"In Review\\\"){elisa{id,status,tests{id,sampleId,elisaId,elisaPlatePosition,status,sample{id,name}}}}}\"}";
        JSONObject response = graphQL.sendQuery(query);

        JSONObject elisaJson = response.getJSONObject("data").getJSONObject("updateElisaStatus").getJSONObject("elisa");
        Elisa elisa = objectMapper.readValue(elisaJson.toString(), new TypeReference<>() {});

        return elisa;
    }


    private StandardCurve calculateStandardCurve(String standardsDataVariable) throws JsonProcessingException {

        StandardData[] stdDatas = objectMapper.readValue(standardsDataVariable, new TypeReference<>() { });

        //Gör ny standardkurva med värden från instrumentet
        StandardCurve stdCurve = new StandardCurve(stdDatas);

        return stdCurve;
    }


    private void setConcAndMeasValueForElisaTests(String samplesRawData){

        JSONArray samplesRawDataArray = new JSONArray(samplesRawData);

        //Ta fram rätt test från ELISAns testLista mhja sampleId i rådata från mätinstrumentet
        //Sätt mätvärde till värde från instrument
        // Beräkna koncentrationen, ge koncentrationen till testet
        for (Object sampleRawDataOject : samplesRawDataArray){
            JSONObject rawData = (JSONObject) sampleRawDataOject;

            Test test = elisa.getTests().stream()
                    .filter(t -> t.getSampleId() == rawData.getInt("sampleId"))
                    .findFirst()
                    .get();

            test.setMeasureValue(rawData.getFloat("measValue"));
            test.setConcentration(stdCurve.calculateConc(test.getMeasureValue()));
        }
    }
}
