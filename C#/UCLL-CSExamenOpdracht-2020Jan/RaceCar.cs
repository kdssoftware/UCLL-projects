using System;

namespace ExamenOpdracht1
{
    public class RaceCar : Car, IDakarRacer
    {
        public string RaceNumber { get; set; }
        public int TotalTime { get; set; }

        public RaceCar(string raceNumber, bool forWheelDrive, int hp) : base(hp, forWheelDrive)
        {
            RaceNumber = raceNumber;
        }

        public void Race()
        {
            TotalTime = new Random().Next(0, 320);
        }

        public override string ToString()
        {
            return "RaceWagen " + RaceNumber + " \t\t - Totaal tijd: " + TotalTime + "\t\t" + base.ToString();
        }
    }
}