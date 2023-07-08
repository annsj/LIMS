using LimsUI.Models.ProcessModels;
using LimsUI.Models.UIModels;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using System;
using LimsUI.Models.ProcessModels.Variables;
using System.Text.Json;
using FluentAssertions;
using LimsUI.ParseFiles;
using Microsoft.AspNetCore.Http;
using System.IO;
using System.Threading.Tasks;
using System.Threading;

namespace TestLimsUI
{
    [TestClass]
    public class BodyCreatorTest
    {

        [TestMethod]
        public void TestCreateStartElisaBodyFromSampleList()
        {
            StartElisaBody expectedBody = new StartElisaBody
            {
                variables = new StartElisaVariables
                {
                    samples = new Samples
                    {
                        type = "String",
                        value = "{\"id\":1,\"name\":\"Prov1\"};{\"id\":2,\"name\":\"Prov2\"}"
                    }
                },
                withVariablesInReturn = true
            };

            List<Sample> samples = new List<Sample>();
            samples.Add(new Sample
            {
                Id = 1,
                Name = "Prov1",
                DateAdded = new DateTime(2023, 07, 01)
            });
            samples.Add(new Sample
            {
                Id = 2,
                Name = "Prov2",
                DateAdded = new DateTime(2023, 07, 01)
            });

            StartElisaBody actualBody = new StartElisaBody(samples);

            //string expJson = JsonSerializer.Serialize(expectedVariables);
            //string actualJson = JsonSerializer.Serialize(actualBody.variables);
            //Assert.AreEqual(expJson, actualJson);

            actualBody.Should().BeEquivalentTo(expectedBody);
        }


        [TestMethod]
        public void TestCreateSendRawDataBody()
        {
            string samplesDataVariableValue = "[{\"position\":1,\"sampleId\":1,\"name\":\"Prov1\",\"measValue\":0.57},{\"position\":2,\"sampleId\":2,\"name\":\"Prov2\",\"measValue\":1.17}]";
            string standardsDataVariableValue = "[{\"position\":73,\"concentration\":2.0,\"measValue\":0.01},{\"position\":74,\"concentration\":4.0,\"measValue\":0.02},{\"position\":75,\"concentration\":8.0,\"measValue\":0.04},{\"position\":76,\"concentration\":16.0,\"measValue\":0.08},{\"position\":77,\"concentration\":32.0,\"measValue\":0.16},{\"position\":78,\"concentration\":64.0,\"measValue\":0.32},{\"position\":79,\"concentration\":128.0,\"measValue\":0.64},{\"position\":80,\"concentration\":256.0,\"measValue\":1.28},{\"position\":81,\"concentration\":2.0,\"measValue\":0.012},{\"position\":82,\"concentration\":4.0,\"measValue\":0.024},{\"position\":83,\"concentration\":8.0,\"measValue\":0.048},{\"position\":84,\"concentration\":16.0,\"measValue\":0.096},{\"position\":85,\"concentration\":32.0,\"measValue\":0.192},{\"position\":86,\"concentration\":64.0,\"measValue\":0.384},{\"position\":87,\"concentration\":128.0,\"measValue\":0.768},{\"position\":88,\"concentration\":256.0,\"measValue\":1.536},{\"position\":89,\"concentration\":2.0,\"measValue\":0.009},{\"position\":90,\"concentration\":4.0,\"measValue\":0.018},{\"position\":91,\"concentration\":8.0,\"measValue\":0.036},{\"position\":92,\"concentration\":16.0,\"measValue\":0.072},{\"position\":93,\"concentration\":32.0,\"measValue\":0.144},{\"position\":94,\"concentration\":64.0,\"measValue\":0.288},{\"position\":95,\"concentration\":128.0,\"measValue\":0.576},{\"position\":96,\"concentration\":256.0,\"measValue\":1.152}]";
            SendRawDataBody expectedBody = new SendRawDataBody
            {
                messageName = "receiveData",
                correlationKeys = new SendRawDataBodyCorrelationkeys
                {
                    elisaId = new ElisaId
                    {
                        type = "Integer",
                        value = 1
                    }
                },
                processVariables = new SendRawDataBodyProcessvariables
                {
                    samplesData = new Samplesdata
                    {
                        type = "String",
                        value = samplesDataVariableValue
                    },
                    standardsData = new Standardsdata
                    {
                        type = "String",
                        value = standardsDataVariableValue
                    }

                },
                resultEnabled = true,
                variablesInResultEnabled = true

            };


            IFormFile file = TestFormFile();
            IParser parser = new Instrument1Parser(file);

            int elisaId = parser.GetElisaId();
            string samplesDataValue = parser.GetSamplesDataValue();
            string standardsDataValue = parser.GetStandardsDataValue();

            SendRawDataBody actualBody =
                new SendRawDataBody(elisaId, samplesDataValue, standardsDataValue);

            actualBody.Should().BeEquivalentTo(expectedBody);
        }


        private static IFormFile TestFormFile()
        {
            string content = File.ReadAllText("C:\\C#\\LIMS\\TestLimsUI\\instrument1_test_result.csv");
            string fileName = "instrument1_test_result.csv";
            MemoryStream stream = new MemoryStream();
            StreamWriter writer = new StreamWriter(stream);
            writer.Write(content);
            writer.Flush();
            stream.Position = 0;
            IFormFile file = new FormFile(stream, 0, stream.Length, "result", fileName);
            return file;
        }
    }
}






