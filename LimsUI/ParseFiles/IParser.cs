using Microsoft.AspNetCore.Http;
using System.Collections.Generic;

namespace LimsUI.ParseFiles
{
    public interface IParser
    {
        //List<string> ReadFileToLines(IFormFile file);
        int GetElisaId();
        string GetSamplesDataValue();
        string GetStandardsDataValue();

    }
}
