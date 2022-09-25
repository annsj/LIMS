using LimsDataAccess.Models;

namespace LimsDataAccess.GraphQL.GraphQLModels
{
    public class SamplePayload
    {
        public Sample Sample { get; set; }

        public SamplePayload(Sample sample)
        {
            Sample = sample;
        }
    }
}
