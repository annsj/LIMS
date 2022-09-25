using System;

namespace LimsDataAccess.Models
{
    public class Test
    {
        public int Id { get; set; }
        public int SampleId { get; set; }
        public int ElisaId { get; set; }
        public int ElisaPlatePosition { get; set; }
        public string Status { get; set; }
        public DateTime DateAdded { get; set; }
        public float? MeasureValue { get; set; }
        public float? Concentration { get; set; }
        public Sample Sample { get; set; }
        public Elisa Elisa { get; set; }

    }
}
