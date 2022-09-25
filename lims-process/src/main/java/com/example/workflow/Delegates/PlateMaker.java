package com.example.workflow.Delegates;


import com.example.workflow.Models.*;
import com.example.workflow.Models.DaoModels.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("PlateMaker")
public class PlateMaker implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        int elisaId = Integer.parseInt(delegateExecution.getVariable("elisaId").toString());

        String testsVariable = delegateExecution.getVariable("tests").toString();
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ArrayList<Test> tests = objectMapper.readValue(testsVariable, new TypeReference<>() {});

        Plate plate = new Plate(elisaId, 2.0F, 2.0F, tests);

        ObjectValue plateValue = Variables.objectValue(plate)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();

        delegateExecution.setVariable("plate", plateValue);
    }
}
