package com.example.workflow.Delegates;

import com.example.workflow.DataAccessFiles.FakeDataAccess;
import com.example.workflow.DataAccessFiles.GraphQLClient;
import com.example.workflow.DataAccessFiles.GraphQLDataAccess;
import com.example.workflow.DataAccessFiles.IDataAccess;
import com.example.workflow.Models.DaoModels.Elisa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("ResultSaver")
public class ResultSaver implements JavaDelegate {

    private final IDataAccess dataAccess;

    public ResultSaver() {
        this.dataAccess = new GraphQLDataAccess();
    }
//    public ResultSaver() {
//        this.dataAccess = new FakeDataAccess();
//    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        TypedValue elisaVariable = delegateExecution.getVariableTyped("elisa");
        Elisa elisa = (Elisa) elisaVariable.getValue();

        boolean experimentOkVariable = (boolean) delegateExecution.getVariable("experimentOk");

        String elisaStatus = null;
        String testStatus = null;

        if (experimentOkVariable) {
            elisaStatus = "Approved";
            testStatus = "Approved";
        }
        else{
            elisaStatus = "Failed";
            testStatus = "Failed";
        }

        JSONObject elisaJson = dataAccess.saveElisaResult(elisa, elisaStatus, testStatus);

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        elisa = objectMapper.readValue(elisaJson.toString(), new TypeReference<>() {});

        ObjectValue elisaValue = Variables.objectValue(elisa)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        delegateExecution.setVariable("elisa", elisaValue);
    }
}
