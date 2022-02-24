using System;
using System.Collections.Generic;

namespace ExamenOpdracht1
{
    public partial class Racers
    {
        public int Id { get; set; }
        public string RaceType { get; set; }
        public string RaceNumber { get; set; }
        public int Hp { get; set; }
        public bool ForWeelDrive { get; set; }
        public bool SyncBrakeSystem { get; set; }
        public bool InRace { get; set; }
    }
}