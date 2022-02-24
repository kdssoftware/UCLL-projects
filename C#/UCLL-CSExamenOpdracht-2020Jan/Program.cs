using System;
using System.Collections;
using System.Linq;

namespace ExamenOpdracht1
{
    internal class Program
    {
        public static ArrayList myRacers = new ArrayList();

        private static void Main(string[] args)
        {
            var db = new ucllexamen20200123Context();
            opladen(db);
            while (true)
            {
                switch (Message("*** DAKAR ***\n1. Voeg racer toe\n2. Race!\n3. Rangschikking\n4. Stop\n"))
                {
                    case "1":
                        VoegRacerToe(db);
                        break;
                    case "2":
                        Race(db);
                        break;
                    case "3":
                        Rangschikking(db);
                        break;
                    case "4":
                        Environment.Exit(0);
                        return;
                    default:
                        Message("Deze keuze is niet gekent");
                        break;
                }

                Console.Clear();
            }
        }

        public static void opladen(ucllexamen20200123Context db)
        {
            var racers_ = from r in db.Racers
            select new
            {
                r.Hp,
                r.RaceNumber,
                r.SyncBrakeSystem,
                r.ForWeelDrive,
                r.RaceType
            };
            foreach (var r in racers_)
                if (r.RaceType.Equals("RaceBike"))
                    myRacers.Add(new RaceBike(r.RaceNumber, r.Hp, r.SyncBrakeSystem));
                else if (r.RaceType.Equals("RaceCar")) myRacers.Add(new RaceCar(r.RaceNumber, r.ForWeelDrive, r.Hp));
        }

        public static void VoegRacerToe(ucllexamen20200123Context db)
        {
            var keuze = Message("\t1. Racewagen\n\t2. Racemotor\n\t > ");
            var raceNummer = Message("\tRace nummer: ");
            var aantalPk = int.Parse(Message("\tAantal pk: "));
            switch (keuze)
            {
                case "1":
                    var rc = new Racers();
                    rc.RaceType = "RaceCar";
                    rc.RaceNumber = raceNummer;
                    rc.Hp = aantalPk;
                    rc.ForWeelDrive = Message("\tVierwiel aandrijving (j/n): ").Trim().ToLower().Equals("j");
                    rc.SyncBrakeSystem = false;
                    rc.InRace = false;
                    //db.Add(rc);
                    //Kan niet toevoegen aan database, geen INSERT permission
                    myRacers.Add(new RaceCar(rc.RaceNumber, rc.ForWeelDrive, rc.Hp));
                    break;
                case "2":
                    var rb = new Racers();
                    rb.RaceType = "RaceBike";
                    rb.RaceNumber = raceNummer;
                    rb.Hp = aantalPk;
                    rb.ForWeelDrive = false;
                    rb.SyncBrakeSystem = Message("\tSync brake system (j/n): ").Trim().ToLower().Equals("j");
                    rb.InRace = false;
                    //db.Add(rb);
                    //Kan niet toevoegen aan database, geen INSERT permission
                    myRacers.Add(new RaceBike(rb.RaceNumber, rb.Hp, rb.SyncBrakeSystem));
                    break;
            }

            db.SaveChanges();
            Message("\tDe racer werd toegevoegd\nDruk op een toets om door te gaan");
        }

        public static void Race(ucllexamen20200123Context db)
        {
            foreach (var r in myRacers)
                if (r is RaceBike)
                {
                    var rb = (RaceBike) r;
                    rb.Race();
                }
                else if (r is RaceCar)
                {
                    var rc = (RaceCar) r;
                    rc.Race();
                }

            Message("Druk op een toets om door te gaan");
        }

        public static void Rangschikking(ucllexamen20200123Context db)
        {
            //BIj het rangschikken bubblesort gebruikt ipv .OrderBy(r=>r.TotalTime) via LINQ
            var currentRace = new ArrayList();
            var bestTotalTime = -1;
            RaceCar tempCar;
            RaceBike tempBike;
            for (var write = 0; write < myRacers.Count; write++)
            for (var sort = 0; sort < myRacers.Count - 1; sort++)    
            {
                int nowTotalTime, nextTotalTime;
                if (myRacers[sort] is RaceCar)
                {
                    var r = (RaceCar) myRacers[sort];
                    nowTotalTime = r.TotalTime;
                }
                else
                {
                    var r = (RaceBike) myRacers[sort];
                    nowTotalTime = r.TotalTime;
                }

                if (myRacers[sort + 1] is RaceCar)
                {
                    var r = (RaceCar) myRacers[sort + 1];
                    nextTotalTime = r.TotalTime;
                }
                else
                {
                    var r = (RaceBike) myRacers[sort + 1];
                    nextTotalTime = r.TotalTime;
                }

                if (nowTotalTime > nextTotalTime)
                {
                    var usingCar = false;
                    tempCar = null;
                    tempBike = null;
                    if (myRacers[sort + 1] is RaceCar)
                    {
                        usingCar = true;
                        tempCar = (RaceCar) myRacers[sort + 1];
                    }
                    else
                    {
                        tempBike = (RaceBike) myRacers[sort + 1];
                    }

                    myRacers[sort + 1] = myRacers[sort];
                    if (usingCar)
                        myRacers[sort] = tempCar;
                    else
                        myRacers[sort] = tempBike;
                }
            }

            foreach (var r in myRacers)
                if (r is RaceCar)
                {
                    var rc = (RaceCar) r;
                    Console.WriteLine(rc.ToString());
                }
                else if (r is RaceBike)
                {
                    var rb = (RaceBike) r;
                    Console.WriteLine(rb.ToString());
                }

            Message("\nDruk op een toets om door te gaan");
        }

        public static string Message(string message)
        {
            Console.Write(message);
            return Console.ReadLine();
        }
    }
}