package com.example.workflow.Delegates;
import com.example.workflow.DataAccessFiles.FakeDataAccess;
import com.example.workflow.DataAccessFiles.GraphQLDataAccess;
import com.example.workflow.DataAccessFiles.IDataAccess;
import com.example.workflow.DataAccessFiles.GraphQLClient;
import com.example.workflow.Models.DaoModels.Test;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.json.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.camunda.spin.Spin.*;


@Component("ExperimentInitializer")
public class ExperimentInitializer implements  JavaDelegate{

    private final IDataAccess dataAccess;

    public ExperimentInitializer() {
        dataAccess = new GraphQLDataAccess();
    }
//    public ExperimentInitializer() { dataAccess = new FakeDataAccess(); }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        int elisaId = dataAccess.postElisa();
        delegateExecution.setVariable("elisaId", elisaId);
        delegateExecution.setProcessBusinessKey(String.valueOf(elisaId));

        String[] samplesInput = ((String) delegateExecution.getVariable("samples")).split(";");
        //TODO: felhantering om fler än 72 prover, dvs samplesInput.length > 72

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
