using LimsUI.Models.ProcessModels.Variables;
using System.Collections.Generic;
using System.Globalization;

namespace LimsUI.Models.ProcessModels
{
    public class SendRawDataBody
    {
        public string messageName { get; set; }
        public SendRawDataBodyCorrelationkeys correlationKeys { get; set; }
        public SendRawDataBodyProcessvariables processVariables { get; set; }
        public bool resultEnabled { get; set; }
        public bool variablesInResultEnabled { get; set; }

        public SendRawDataBody(){ }

        public SendRawDataBody(
            int elisaIdValue, 
            string samplesDataValue, 
            string standardsDataValue)
        {
            messageName = "receiveData";
            correlationKeys = new SendRawDataBodyCorrelationkeys
            {
                elisaId = new ElisaId
                {
                    type = "Integer",
                    value = elisaIdValue
                }
            };
            processVariables = new SendRawDataBodyProcessvariables
            {
                samplesData = new Samplesdata
                {
                    type = "String",
                    value = samplesDataValue
                },
                standardsData = new Standardsdata
                {
                    type = "String",
                    value = standardsDataValue
                }
            };
            resultEnabled = true;
            variablesInResultEnabled = true;
        }
    }


    public class SendRawDataBodyCorrelationkeys
    {
        public ElisaId elisaId { get; set; }
    }


    public class SendRawDataBodyProcessvariables
    {
        public Standardsdata standardsData { get; set; }
        public Samplesdata samplesData { get; set; }
    }
}

