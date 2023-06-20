// Use this class to run process without access to Data base

package com.example.workflow.DataAccessFiles;

import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

// NOTE: Only one Fake process can run at the same time because of hard coaded ElisaId which is used to set business key which has to be unique among running processes.

//Uncomment below line to use FakeDataAccess for implementation of IDataAccess
//@Component
public class FakeDataAccess implements IDataAccess{

    @Override
    public int postElisa() {
        return 9992;
    }

    @Override
    public Test postTest(int sampleId, String sampleName, int position, int elisaId) {
        Test test = new Test(98981, sampleId, sampleName, elisaId, position, "In progress");
        return test;
    }

    @Override
    public Elisa updateElisaStatus(int elisaId, String status) {

        Test test1 = new Test(98981, 1, "FakeProv1", elisaId, 1, status);
        Test test2 = new Test(98982, 2, "FakeProv2", elisaId, 2, status);

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
    public JSONObject saveElisaResult(Elisa elisa, String elisaStatus, String testStatus) throws IOException {

        for (Test test : elisa.getTests()){
            test.setStatus(testStatus);
        }

        elisa.setStatus(elisaStatus);

        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JSONObject elisaJson = new JSONObject(writer.writeValueAsString(elisa));

        return elisaJson;
    }
}
