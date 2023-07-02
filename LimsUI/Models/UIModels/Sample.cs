using System;
using System.ComponentModel;

namespace LimsUI.Models.UIModels
{
    public class Sample
    {
        public int Id { get; set; }

        [DisplayName("Prov")]
        public string Name { get; set; }

        public float? Concentration { get; set; }
        
        [DisplayName("Skapat (datum)")]
        public DateTime DateAdded { get; set; }

        //public List<Test> Tests { get; set; }




    }
}
