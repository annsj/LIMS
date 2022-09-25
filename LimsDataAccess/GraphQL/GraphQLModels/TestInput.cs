

namespace LimsDataAccess.GraphQL.GraphQLModels
{
    public class TestInput
    {
        public int? Id { get; set; }
        public int? SampleId { get; set; }
        public int? ElisaId { get; set; }
        public string SampleName { get; set; }
        public int? ElisaPlatePosition { get; set; }
        public float? MeasureValue { get; set; }
        public float? Concentration { get; set; }
        public string Status { get; set; }

    }
}
