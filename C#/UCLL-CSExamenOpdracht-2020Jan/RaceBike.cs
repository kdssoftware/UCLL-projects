using System;

namespace ExamenOpdracht1
{
    public class RaceBike : Bike, IDakarRacer
    {
        public string RaceNumber { get; set; }
        public int TotalTime { get; set; }

        public RaceBike(string raceNumber, int hp, bool syncBrakeSystem) : base(hp, syncBrakeSystem)
        {
            RaceNumber = raceNumber;
        }

        public override string ToString()
        {
            return "RaceMotor " + RaceNumber + " \t\t - Totaal tijd: " + TotalTime + "\t\t" + base.ToString();
        }


        public void Race()
        {
            TotalTime = new Random().Next(0, 480);
        }
    }
}