using System.Collections.Generic;


namespace LimsDataAccess.GraphQL.GraphQLModels
{

    public class ElisaInput
    {
        public int  Id { get; set; }
        public string Status { get; set; }
        public List<TestInput> TestInputs { get; set; }
    }
}
