using System;
using System.Collections.Generic;

namespace exceptions
{
    public class Provider
    {
        public enum Status
        {
            Online,
            Offline,
            Restricted
        }

        public Status status;

        public Provider(string naam, int connectieSnelheid)
        {
            Naam = naam;
            ConnectieSnelheid = connectieSnelheid;
            Smartphones = new List<Smartphone>();
        }

        public string Naam { get; set; }

        public int ConnectieSnelheid { get; set; }

        public List<Smartphone> Smartphones { get; set; }

        private void VoegSmartphoneToe(Smartphone smartphone)
        {
            try
            {
                if (status != Status.Online) throw new OfflineSatusException("Provider niet offline.");
                if (smartphone.Verbind(ConnectieSnelheid))
                    throw new VerbindingsFoutException("Apparaat niet compatibel met het netwerk.");
            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message);
            }

            Smartphones.Add(smartphone);
        }

        public string GeefOmschrijving()
        {
            var str = Naam + "\n" +
                      "\t- Connectiesnelheid: " + ConnectieSnelheid + "\n" +
                      "\t- Status: " + status + "\n" +
                      "\t- Overzicht smartphones:\n";
            foreach (var telefon in Smartphones) str += "\t\t" + telefon.GeefOmschrijving() + "\n";
            return str;
        }
    }
}