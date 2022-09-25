using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Http;
using System.IO;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using LimsUI.Models.ProcessModels;
using LimsUI.Models.ProcessModels.Variables;
using LimsUI.Models.UIModels;
using LimsUI.Gateways.GatewayInterfaces;
using System.Threading.Tasks;
using LimsUI.Utilities;
using System.Text.Json;

namespace LimsUI.Pages.ElisaPages
{
    public class ReviewResultModel : PageModel
    {



        private readonly IProcessGateway _processGateway;

        public ReviewResultModel(IProcessGateway processGateway)
        {
            _processGateway = processGateway;
        }

        [BindProperty]
        public int ElisaId { get; set; }

        public Elisa Elisa { get; set; }

        [BindProperty]
        public Elisa ReviewedResult { get; set; }

        public List<StandardData> StandardDatas { get; set; }

        //public List<Elisa> Elisas { get; set; }

        public List<int> ElisaIds { get; set; }


        public async Task<IActionResult> OnGet()
        {
            //Om man kommer hit efter att ha valt en fil p� SendRawData Page finns det en cookie som inneh�ller resultatet
            //f�r den fil man valde d�r och resultatet visas direkt f�r granskning.
                        
            Elisa = HttpContext.Session.GetElisaFromSendRawDataReturnValues("SendRawDataReturnValues");
            StandardDatas = HttpContext.Session.GetStandardDataFromSendRawDataReturnValues("SendRawDataReturnValues");


            //Om man g�r direkt till den h�r sidan utan att ha valt en fil p� SendRawDAata Page f�r man v�lja ELISA
            //fr�n en lista med ber�knade resultat som v�ntar p� granskning, listan skapas fr�n alla processinstanser
            //d�r variabeln elisa har status"I Review".

            if (Elisa == null && ElisaId == 0)
            {
                List<ProcessInstance> processInstances = await _processGateway.GetProcesses();

                List<string> instanceIds = new List<string>();
                ElisaIds = new List<int>();

                foreach (ProcessInstance instance in processInstances)
                {
                    string elisaVariable = await _processGateway.GetVariable(instance.id, "elisa");

                    GetElisaVariableReturnValues returnValues = JsonSerializer
                        .Deserialize<GetElisaVariableReturnValues>(elisaVariable,new JsonSerializerOptions { PropertyNameCaseInsensitive = true });

                    if (returnValues.value != null && returnValues.value.status == "In Review")
                    {
                        ElisaIds.Add(returnValues.value.id);
                    }
                }

                ElisaIds.Sort();

                return Page();
            }

            //Tar bort cookien n�r den anv�nts f�r att v�ntande resultat skall visas n�sta g�ng sidan laddas.
            HttpContext.Session.Remove("SendRawDataReturnValues");

            return Page();
        }


        public async Task<IActionResult> OnPost()
        {
            //Om man valt en ELISA fr�n en lista med resultat som v�ntar p� granskning och klickat p� "Visa resultat f�r granskning"
            //h�mtas det resultatet fr�n processen och visas f�r granskning.
            if (ReviewedResult.Id == 0)
            {
                Elisa = await _processGateway.GetResultForElisaId(ElisaId);
                StandardDatas = await _processGateway.GetStandardDatasForElisaId(ElisaId);
                return Page();
            }

            //Om man granskat ett resultat och klickat p� "Spara beslut" skickas beslutet till processen.
            ResultReviewedBody resultReviewedBody = MakeResultReviewedBody();
            ResultReviewedReturnValues resultReviewedReturnValues = await _processGateway.SendResultReviewed(resultReviewedBody);
            HttpContext.Session.SetResultReviewedReturnValues("ResultReviewedReturnValues", resultReviewedReturnValues);

            //Tar bort cookien n�r den anv�nts f�r att v�ntande resultat skall visas n�sta g�ng sidan laddas.
            HttpContext.Session.Remove("SendRawDataReturnValues");

            int elisaId = resultReviewedReturnValues.variables.elisaId.value;

            if (ReviewedResult.Redo)
            {
                return Redirect($"~/ElisaPages/ViewLayout/?ElisaId={elisaId}");
            }

            return Redirect($"~/ElisaPages/ElisaResult/");
        }



        private ResultReviewedBody MakeResultReviewedBody()
        {
            ResultReviewedBody body = new ResultReviewedBody
            {
                messageName = "resultReviewed",
                correlationKeys = new ResultReviewedBodyCorrelationkeys
                {
                    elisaId = new ElisaId
                    {
                        type = "Integer",
                        value = ReviewedResult.Id
                    }
                },
                processVariables = new ResultReviewedBodyProcessvariables
                {
                    experimentOk = new ExperimentOk
                    {
                        type = "Boolean",
                        value = ReviewedResult.Approved
                    },
                    redo = new Redo
                    {
                        type = "Boolean",
                        value = ReviewedResult.Redo
                    }
                },
                resultEnabled = true,
                variablesInResultEnabled = true
            };

            return body;
        }
    }
}
