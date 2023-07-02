using LimsUI.Models.ProcessModels.Variables;
using LimsUI.Models.UIModels;
using System.Collections.Generic;

namespace LimsUI.Models.ProcessModels
{
    public class StartElisaBody
    {
        public StartElisaVariables variables { get; set; }
        public bool withVariablesInReturn { get; set; }

        public StartElisaBody(){ }


        public StartElisaBody(List<Sample> samples)
        {
            variables = new StartElisaVariables
            {
                samples = new Samples
                {
                    type = "String",
                    value = CreateSamplesValue(samples)
                }
            };

            withVariablesInReturn = true;
        }

        private static string CreateSamplesValue(List<Sample> samples)
        {
            string sampleValue = "";

            foreach (var sample in samples)
            {
                //https://docs.microsoft.com/en-us/dotnet/standard/base-types/composite-formatting#escaping-braces
                sampleValue += $"{{\"id\":{sample.Id},\"name\":\"{sample.Name}\"}};";
            }
            sampleValue = sampleValue.Trim(';');
            return sampleValue;
        }
    }

    public class StartElisaVariables
    {
        public Samples samples { get; set; }
    }


}

