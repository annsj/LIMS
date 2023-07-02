using LimsUI.Gateways.GatewayInterfaces;
using LimsUI.Models.ProcessModels;
using LimsUI.Models.ProcessModels.Variables;
using LimsUI.Models.UIModels;
using LimsUI.Utilities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;

namespace LimsUI.Pages.ElisaPages
{
    public class StartElisaModel : PageModel
    {
        private readonly ILogger<StartElisaModel> _logger;
        private readonly IDataAccessGateway _dataAccessGateway;
        private readonly IProcessGateway _processGateway;

        public StartElisaModel(ILogger<StartElisaModel> logger, IDataAccessGateway dataAccessGateway,
            IProcessGateway processGateway)
        {
            _logger = logger;
            _dataAccessGateway = dataAccessGateway;
            _processGateway = processGateway;
        }


        public List<Sample> Samples { get; set; }

        [BindProperty]
        public List<int> SelectedIds { get; set; }

        public List<Sample> SelectedSamples { get; set; }

        public int? ElisaId { get; set; }

        public Layout Layout { get; set; }

        public string ErrorMessage { get; set; }


        public async Task<IActionResult> OnGet()
        {
            Samples = await _dataAccessGateway.GetSamples();
            Samples = Samples.Where(s => s.Concentration == null).ToList();

            HttpContext.Session.SetSamples("Samples", Samples);

            return Page();
        }

        public async Task<IActionResult> OnPost()
        {
            Samples = HttpContext.Session.GetSamples("Samples");
            if (Samples == null)
            {
                Samples = await _dataAccessGateway.GetSamples();
            }

            if (SelectedIds.Any() == false)
            {
                ErrorMessage = "Välj minst ett prov";
                return Page();
            }

            MakeSelectedSamplesList();

            StartElisaReturnValues response = await _processGateway.StartElisa(SelectedSamples);

            ElisaId = response.variables.elisaId.value;

            Layout = JsonSerializer.Deserialize<Layout>(
                response.variables.plate.value.ToString(),
                new JsonSerializerOptions{ PropertyNameCaseInsensitive = true});

            return Page();
        }


        private void MakeSelectedSamplesList()
        {
            SelectedSamples = new List<Sample>();

            foreach (var sample in Samples)
            {
                if (SelectedIds.Contains(sample.Id))
                {
                    SelectedSamples.Add(sample);
                }
            }
        }
    }
}
