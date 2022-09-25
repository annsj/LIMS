using HotChocolate;
using HotChocolate.Data;
using LimsDataAccess.Data;
using LimsDataAccess.Models;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace LimsDataAccess.GraphQL
{
    public class Query
    {

        //Injektion av context görs med [ScopedService] som parameter i metoden
        //vilket gör att injection via konstruktorn inte behövs

        //private readonly LimsContext _context;

        //public Query(LimsContext context)
        //{
        //    _context = context;
        //}


        [UseDbContext(typeof(LimsContext))]
        [UseProjection]  //Gör att child objects kommer med
        [UseFiltering]
        [UseSorting]
        //[Service] tillgängligt via Hotchocolate, gör att context inte behöver injiceras från
        //konstruktor utan direkt som parameter i metoden
        public IQueryable<Elisa> GetElisas([ScopedService] LimsContext context)
        {
            var elisas = context.Elisa;

            return elisas;
        }


        [UseDbContext(typeof(LimsContext))]
        [UseProjection]
        [UseFiltering]
        [UseSorting]
        public IQueryable<Sample> GetSamples([ScopedService] LimsContext context)
        {
            return context.Sample;
        }


        [UseDbContext(typeof(LimsContext))]
        [UseProjection]
        [UseFiltering]
        [UseSorting]
        public IQueryable<Test> GetTests([ScopedService] LimsContext context)
        {
            return context.Test;
        }
    }
}
