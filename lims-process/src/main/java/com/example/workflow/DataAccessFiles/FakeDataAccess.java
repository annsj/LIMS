// Use this class to run process without access to Data base

package com.example.workflow.DataAccessFiles;

import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FakeDataAccess implements IDataAccess{

    @Override
    public int postElisa() throws IOException, InterruptedException {
        return 1;
    }

    @Override
    public Test postTest(int sampleId, String sampleName, int position, int elisaId) throws IOException, InterruptedException {
        Test test = new Test(12345, sampleId, sampleName, elisaId, position, "In progress");
        return test;
    }

    @Override
    public Elisa updateElisaStatus(int elisaId, String status) throws IOException, InterruptedException {

        Test test1 = new Test(12345, 987, "Prov987", elisaId, 1, "In progress");
        Test test2 = new Test(12346, 988, "Prov988", elisaId, 2, "In progress");

        ArrayList<Test> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);

        Elisa elisa = new Elisa();
        elisa.setId(elisaId);
        elisa.setTests(tests);
        elisa.setStatus(status);

        return elisa;
    }

    @Override
    public JSONObject saveElisaResult(Elisa elisa, String elisaStatus, String testStatus) throws IOException, InterruptedException {

        for (Test test : elisa.getTests()){
            test.setStatus(testStatus);
        }

        elisa.setStatus(elisaStatus);

        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JSONObject elisaJson = new JSONObject(mapper.writeValueAsString(elisa));

        return elisaJson;
    }
}
