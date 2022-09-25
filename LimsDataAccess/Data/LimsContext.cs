using LimsDataAccess.Models;
using Microsoft.EntityFrameworkCore;

namespace LimsDataAccess.Data
{
    public class LimsContext : DbContext
    {
        public LimsContext(DbContextOptions<LimsContext> options)
            : base(options)
        {
        }

        public DbSet<Elisa> Elisa { get; set; }
        public DbSet<Test> Test { get; set; }
        public DbSet<Sample> Sample { get; set; }

    }
}
