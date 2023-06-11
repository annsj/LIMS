package com.example.workflow.DataAccessFiles;

import com.example.workflow.Models.DaoModels.Elisa;
import com.example.workflow.Models.DaoModels.Test;
import org.json.JSONObject;

import java.io.IOException;

public interface IDataAccess {

    public int postElisa()
            throws IOException, InterruptedException;

    public Test postTest(int sampleId, String sampleName, int position, int elisaId)
            throws IOException, InterruptedException;

    public Elisa updateElisaStatus(int elisaId)
            throws IOException, InterruptedException;

    public JSONObject saveElisaResult(Elisa elisa, String elisaStatus, String testStatus)
            throws IOException, InterruptedException;
}
