using LimsDataAccess.Models;

namespace LimsDataAccess.GraphQL.GraphQLModels
{
    public class ElisaPayload
    {
        public Elisa Elisa { get; set; }

        public ElisaPayload(Elisa elisa)
        {
            Elisa = elisa;
        }
    }
}
