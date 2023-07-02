using LimsUI.Models.ProcessModels;
using LimsUI.Models.UIModels;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using System;
using LimsUI.Models.ProcessModels.Variables;
using System.Text.Json;
using FluentAssertions;

namespace TestLimsUI
{
    [TestClass]
    public class UnitTest1
    {

        [TestMethod]
        public void TestCreateStartElisaBody()
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
    }
}
