using GraphQL.Server.Ui.Voyager;
using LimsDataAccess.Data;
using LimsDataAccess.GraphQL;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

namespace LimsDataAccess
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            //AddPooledDbContextFactory ist�llet f�r AddDbContext f�r att kunna hantera att fler anrop sker samtidigt
            services.AddPooledDbContextFactory<LimsContext>(options =>
                   options.UseSqlServer(Configuration.GetConnectionString("LimsContext")));

            services
                .AddGraphQLServer()
                .AddQueryType<Query>()
                .AddMutationType<Mutation>()
                .AddProjections()
                .AddFiltering()
                .AddSorting();

        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            //Tar bort redirection f�r att kunna anropa localhost fr�n Java-projekt, Certifikat-problem annars
            //app.UseHttpsRedirection();

            app.UseRouting();


            //https://localhost:5001/graphQL/
            //http://localhost:5000/graphql/
            app.UseEndpoints(endpoints =>
            {
                endpoints.MapGraphQL();
            });


            //https://localhost:5001/ui/voyager
            //http://localhost:5000/ui/voyager
            app.UseGraphQLVoyager(new VoyagerOptions());
        }
    }
}
