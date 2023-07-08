using LimsUI.Gateways.GatewayInterfaces;
using LimsUI.Models.ProcessModels;
using LimsUI.Models.ProcessModels.Variables;
using LimsUI.Models.UIModels;
using LimsUI.ParseFiles;
using LimsUI.Utilities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;


namespace LimsUI.Pages.ElisaPages
{
    public class SendRawDataModel : PageModel
    {
        private readonly IProcessGateway _processGateway;
        //private IParser _parser;

        public SendRawDataModel(IProcessGateway processGateway/*, IParser parser*/)
        {
            _processGateway = processGateway;
            //_parser = parser;
        }

        public Elisa Elisa { get; set; }

        public List<StandardData> StandardDatas { get; set; }

        [BindProperty]
        public IFormFile SelectedFile { get; set; }

        //public List<string> ResultLines { get; set; }




        public void OnGet()
        {
        }

        public async Task<IActionResult> OnPost()
        {
            //TODO:
            //make it possible for user to select files from different instrument,
            //instantiate parser type based on user selection

            //_parser = new Instrument1Parser(SelectedFile);
            IParser parser = new Instrument1Parser(SelectedFile);

            int elisaId = parser.GetElisaId();
            string samplesDataValue = parser.GetSamplesDataValue();
            string standardsDataValue = parser.GetStandardsDataValue();

            var sendRawDataBody =
                new SendRawDataBody(elisaId, samplesDataValue, standardsDataValue);

            SendRawDataReturnValues sendRawDataReturnValues =
                await _processGateway.SendRawData(sendRawDataBody);

            //SendRawDataReturnValues sendRawDataReturnValues = TestData.MakeSendRawDataReturnValuesExample();

            HttpContext.Session.SetSendRawDataReturnValues(
                "SendRawDataReturnValues", sendRawDataReturnValues);


            return Redirect($"./ReviewResult/");
        }
    }
}
