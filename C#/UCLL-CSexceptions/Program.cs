using System;

namespace exceptions
{
    internal class Program
    {
        public const string VERDER = "Druk op een toets om verder te gaan";

        public const string STATUS = "Status provider\n" +
                                     " 1. Online\n" +
                                     " 2. Offline\n" +
                                     " 3. Restricted\n" +
                                     " > ";

        public static Provider provider;

        public static void Main(string[] args)
        {
            do
            {
                Console.WriteLine();
                switch (Menu(geefMenu()))
                {
                    case 1:
                        MaakProviderAan();
                        break;
                    case 2:
                        VoegSmartphoneToe();
                        break;
                    case 3:
                        GeefOverzichtProvider();
                        break;
                    case 4:
                        Environment.Exit(0);
                        break;
                }
            } while (true);
        }

        private static void GeefOverzichtProvider()
        {
            Console.WriteLine(provider.GeefOmschrijving());
            Console.Write(VERDER);
            Console.ReadLine();
        }

        private static void VoegSmartphoneToe()
        {
            Console.Write("Model:");
            var model = Console.ReadLine();
            Console.Write("Connectiesnelheid: ");
            int connectiesnelheid;
            int.TryParse(Console.ReadLine(), out connectiesnelheid);
            try
            {
                if (provider.status != Provider.Status.Online)
                    throw new OfflineSatusException("Netwerk is niet online");

                if (connectiesnelheid < 0)
                    throw new ConnectieSnelheidException("Connectiesnelheid mag niet negatief zijn");

                if (connectiesnelheid < provider.ConnectieSnelheid)
                    throw new ConnectieSnelheidException("Apparaat niet combatibel");
                provider.Smartphones.Add(new Smartphone(connectiesnelheid, model));
            }
            catch (Exception err)
            {
                Console.WriteLine("Fout tijdens smartphone initialisatie: " + err.Message);
                Console.WriteLine("De smartphone werd niet toegevoegd.");
            }

            Console.WriteLine(VERDER);
            Console.ReadLine();
        }

        private static void MaakProviderAan()
        {
            if (provider == null)
            {
                Console.Write("Geef de naam van de provider: ");
                var naam = Console.ReadLine();
                Console.Write("Provider connectie snelheid: ");
                int connectieSnelheid;
                int.TryParse(Console.ReadLine(), out connectieSnelheid);
                provider = new Provider(naam, connectieSnelheid);
                Console.WriteLine("Een nieuwe provider werd aangemaakt");
            }
            else
            {
                Console.Write(STATUS);
                var optie = 0;
                int.TryParse(Console.ReadLine(), out optie);
                switch (optie)
                {
                    case 1:
                        provider.status = Provider.Status.Online;
                        break;
                    case 2:
                        provider.status = Provider.Status.Offline;
                        break;
                    case 3:
                        provider.status = Provider.Status.Restricted;
                        break;
                    default:
                        Console.Write("Deze optie is niet gekend.");
                        break;
                }
            }

            Console.WriteLine(VERDER);
            Console.ReadLine();
        }

        public static int Menu(string MENU)
        {
            var optie = 0;
            Console.Clear();
            Console.Write(MENU);
            try
            {
                optie = int.Parse(Console.ReadLine());
                if (optie <= 0 || optie > 4) throw new Exception();
            }
            catch (Exception)
            {
                Console.WriteLine("Geen geldige optie");
                Menu(MENU);
            }

            return optie;
        }

        public static string geefMenu()
        {
            var MENU = " Provider beheer\n" +
                       "1. " + (provider == null ? "Maak " : "Pas ") + " provider aan\n" +
                       "2. Voeg smartphone toe\n" +
                       "3. Geef overzicht provider\n" +
                       "4. Stop\n" +
                       " >";
            return MENU;
        }
    }
}