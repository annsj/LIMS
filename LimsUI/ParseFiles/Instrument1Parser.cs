using Microsoft.AspNetCore.Http;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace LimsUI.ParseFiles
{
    public class Instrument1Parser : IParser
    {
        public List<string> ResultLines { get; set; }

        public Instrument1Parser(IFormFile file)
        {
            Stream stream = file.OpenReadStream();
            StreamReader reader = new StreamReader(stream);

            ResultLines = new List<string>();
            string line;

            while ((line = reader.ReadLine()) != null)
            {
                ResultLines.Add(line);
            }
            reader.Close();
        }

        public int GetElisaId()
        {
            return int.Parse(ResultLines[1]);
        }

        public string GetSamplesDataValue()
        {
            string samplesDataValue = "[";

            foreach (var line in ResultLines)
            {
                string[] values = line.Split(";");

                //splitString.Length > 1 -> tar inte med inledande rader som innehåller elisaId, se wwwroot/result_exemple.csv
                //TryParse -> hoppa över rubikrader
                if (values.Length > 1 &&
                    int.TryParse(values[0], out int pos))
                {
                    //pos > 72 -> samples har position 1-72 
                    if (pos > 72)
                    {
                        break;
                    }

                    int sampleId = int.Parse(values[1]);
                    string name = values[2];
                    float measValue = float.Parse(values[3]);
                    samplesDataValue += $"{{" +
                        $"\"position\":{pos}," +
                        $"\"sampleId\":{sampleId}," +
                        $"\"name\":\"{name}\"," +
                        $"\"measValue\":{SetPointSeparator(measValue)}}},";
                }
            }

            samplesDataValue = samplesDataValue.Trim(',');
            samplesDataValue += "]";

            return samplesDataValue;
        }

        public string GetStandardsDataValue()
        {
            string standardsDataValue = "[";

            foreach (var line in ResultLines)
            {
                string[] values = line.Split(";");

                //values.Length > 1 -> tar inte med inledande rader som innehåller elisaId, se wwwroot/result_exemple.csv
                //TryParse -> hoppa över rubikrader
                //pos > 72 -> standards har position 73-96 
                if (values.Length > 1 &&
                    int.TryParse(values[0], out int pos) &&
                    pos > 72)
                {
                    float conc = float.Parse(values[1]);
                    float measValue = float.Parse(values[2]);
                    standardsDataValue += $"{{" +
                        $"\"position\":{pos}," +
                        $"\"concentration\":{conc.ToString("0.0", CultureInfo.CreateSpecificCulture("en-GB"))}," +
                        $"\"measValue\":{SetPointSeparator(measValue)}}},";
                }
            }

            standardsDataValue = standardsDataValue.Trim(',');
            standardsDataValue += "]";

            return standardsDataValue;
        }

        private string SetPointSeparator(float number)
        {
            return number.ToString(CultureInfo.CreateSpecificCulture("en-GB"));
        }

        //public List<string> ReadFileToLines(IFormFile file)
        //{
        //    Stream stream = file.OpenReadStream();
        //    StreamReader reader = new StreamReader(stream);

        //    var resultLines = new List<string>();
        //    string line;

        //    while ((line = reader.ReadLine()) != null)
        //    {
        //        resultLines.Add(line);
        //    }
        //    reader.Close();

        //    return resultLines;
        //}
    }
}
