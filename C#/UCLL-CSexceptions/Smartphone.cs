using System;

namespace exceptions
{
    public class Smartphone
    {
        private int connectieSnelheid;

        public Smartphone(int connectieSnelheid, string model)
        {
            this.connectieSnelheid = connectieSnelheid;
            Model = model;
        }

        public int ConnectieSnelheid
        {
            get => connectieSnelheid;
            set
            {
                try
                {
                    if (value < 0) throw new ConnectieSnelheidException("ConnectieSnelheid negatief");
                    connectieSnelheid = value;
                }
                catch (ConnectieSnelheidException err)
                {
                    Console.WriteLine(err.Message);
                }
            }
        }

        public string Model { get; set; }

        public bool Verbind(int connectieSnelheid)
        {
            return this.connectieSnelheid >= connectieSnelheid;
        }

        public string GeefOmschrijving()
        {
            return "Smartphone " + Model + " / Connectie snelh.: " + connectieSnelheid;
        }
    }
}