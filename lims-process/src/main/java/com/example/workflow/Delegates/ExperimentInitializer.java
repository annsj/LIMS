package com.example.workflow.Delegates;
import com.example.workflow.DataAccessFiles.IDataAccess;
import com.example.workflow.Models.DaoModels.Test;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.json.*;
import java.util.ArrayList;

import static org.camunda.spin.Spin.*;


@Component("ExperimentInitializer")
public class ExperimentInitializer implements  JavaDelegate{

    private final IDataAccess dataAccess;

    public ExperimentInitializer(IDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        String[] samplesInput;
        try{
            samplesInput = ((String) delegateExecution.getVariable("samples")).split(";");
        }
        catch (NullPointerException e){
            throw e;
        }

        if (samplesInput.length > 72){
            throw new Exception("Too many samples (" + samplesInput.length + ") provided, max number of samples is 72.");
        }

        int elisaId = dataAccess.postElisa();
        delegateExecution.setVariable("elisaId", elisaId);
        delegateExecution.setProcessBusinessKey(String.valueOf(elisaId));

        ArrayList<Test> testList = new ArrayList<>();

        int position = 1;
        for (String sample :samplesInput){
            JSONObject sampleJson = new JSONObject(sample);
            Test test = dataAccess.postTest(
                    sampleJson.getInt("id"),
                    sampleJson.getString("name"),
                    position,
                    elisaId
            );
            testList.add(test);
            position++;
        }

        String tests = JSON(testList).toString();
        delegateExecution.setVariable("tests", tests);
    }
}
