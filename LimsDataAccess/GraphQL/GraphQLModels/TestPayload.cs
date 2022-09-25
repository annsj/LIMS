using LimsDataAccess.Models;

namespace LimsDataAccess.GraphQL.GraphQLModels
{
    public class TestPayload
    {
        public Test Test { get; set; }

        public TestPayload(Test test)
        {
            Test = test;
        }
    }
}
