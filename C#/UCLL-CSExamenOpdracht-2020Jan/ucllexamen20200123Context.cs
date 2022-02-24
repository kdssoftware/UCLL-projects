using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace ExamenOpdracht1
{
    public partial class ucllexamen20200123Context : DbContext
    {
        public ucllexamen20200123Context()
        {
        }

        public ucllexamen20200123Context(DbContextOptions<ucllexamen20200123Context> options)
            : base(options)
        {
        }

        public virtual DbSet<Racers> Racers { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http: //go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseSqlServer(
                    "server=185.115.217.152;database=ucll.examen20200123;user id=ucll;password=Ucll2020.");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Racers>(entity =>
            {
                entity.Property(e => e.RaceNumber)
                    .IsRequired()
                    .IsUnicode(false);

                entity.Property(e => e.RaceType)
                    .IsRequired()
                    .IsUnicode(false);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}