package com.example.workflow.Delegates;
import com.example.workflow.GraphQL;
import com.example.workflow.Models.DaoModels.Test;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.springframework.stereotype.Component;
import org.json.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.camunda.spin.Spin.*;


@Component("ExperimentInitializer")
public class ExperimentInitializer implements  JavaDelegate{

    private GraphQL graphQL;
    private int elisaId;

    public ExperimentInitializer() {
        this.graphQL = new GraphQL();
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        //TODO: här bör det framgå att ELISAn sparas i DB
        elisaId = createElisa();
        delegateExecution.setVariable("elisaId", elisaId);
        delegateExecution.setProcessBusinessKey(String.valueOf(elisaId));

        String[] samplesInput = ((String) delegateExecution.getVariable("samples")).split(";");
        //TODO: felhantering om fler än 72 prover, dvs samplesInput.length > 72

        //TODO: här bör det framgå att testerna sparas i DB
        ArrayList<Test> testList = createTestList(samplesInput);
        String tests = JSON(testList).toString();
        delegateExecution.setVariable("tests", tests);
    }


    private int createElisa() throws IOException, InterruptedException {

        String query = "{\"query\":\"mutation{addElisa{elisa{id,status,dateAdded}}}\"}";
        JSONObject response = graphQL.sendQuery(query);

        int id = response.getJSONObject("data")
                .getJSONObject("addElisa")
                .getJSONObject("elisa")
                .getInt("id");

        return id;
    }

    private ArrayList<Test> createTestList(String[] samplesInput) throws IOException, InterruptedException {

        ArrayList<Test> testList = new ArrayList<>();
        int position = 1;

        for (String input : samplesInput){
            JSONObject inputJson = new JSONObject(input);
            Test test = createTest(inputJson.getInt("id"), inputJson.getString("name"), position);
            testList.add(test);
            position++;
        }

        return testList;
    }

    private Test createTest(int sampleId, String sampleName, int position) throws IOException, InterruptedException {

        String query = "{\"query\":\"mutation{addTest(input:{sampleId:" + sampleId +
                                                            ",elisaId:" + elisaId +
                                                            ",elisaPlatePosition:" + position +
                                                            "}){test{id,sampleId,elisaId,elisaPlatePosition,status,dateAdded}}}\"}";

        JSONObject response = graphQL.sendQuery(query);

        JSONObject testJson = response.getJSONObject("data")
                                      .getJSONObject("addTest")
                                      .getJSONObject("test");

        int id = testJson.getInt("id");
        String status = testJson.getString("status");

        Test test = new Test(id, sampleId, sampleName, elisaId, position, status);

        return test;
    }
}
