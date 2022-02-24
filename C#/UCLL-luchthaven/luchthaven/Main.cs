using System;

namespace luchthaven
{
    class Program
    {
        static ControleToren ct = new ControleToren();
        static void Main(string[] args)
        {
            bool running = true;
            do
            {
                switch (showMenu())
                {
                    case 1: //Overzicht vliegtuigen
                        Console.WriteLine(ct.geefOverzicht());
                        verder();
                        break;
                    case 2: //Meld nieuw vliegtuig aan
                        Console.Write("Geef de code in van het inkomende vliegtuig: ");
                        Console.Write(
                            ct.meldVliegtuigAan(
                                    Console.ReadLine()
                            ) + "\n"
                        );
                        verder();
                        break;
                    case 3: //Toestemming tot landen
                        Console.Write("Toestemming voor landen voor code: ");
                        Console.Write(
                            ct.toestemmingTotLanden(
                                zoekVliegtuig(
                                    Console.ReadLine()
                                )
                            ) + "\n"
                        );
                        verder();
                        break;
                    case 4: //Toestemming tot opstijgen
                        Console.Write("Toestemming voor opstijgen voor code: ");
                        Console.Write(
                            ct.toestemmingTotOpstijgen(
                                zoekVliegtuig(
                                    Console.ReadLine()
                                )
                            ) + "\n"
                        );
                        verder();
                        break;
                    case 5: //Verwijder uit luchtruim
                        Console.Write("Te verwijderen vlucht code: ");
                        Console.Write(
                            ct.verwijderUitLuchtruim(
                                zoekVliegtuig(
                                    Console.ReadLine()
                                )
                            ) + "\n"
                        );
                        verder();
                        break;
                    case 6: //Onderhoud van alle vliegtuigen
                        Console.WriteLine("Aantal gerepareerde toestellen: "+ct.repareerVliegtuigen());
                        verder();
                        break;
                    case 7: //stop
                        running = false;
                        break;
                }
                Console.Clear();
            } while (running);
        }

        private static int showMenu()
        {
            string menu =
                "Controletoren menu\n" +
                "1. Overzicht vliegtuigen\n" +
                "2. Meld nieuw vliegtuig aan\n" +
                "3. Toestemming tot landen\n" +
                "4. Toestemming tot opstijgen\n" +
                "5. Verwijderen uit luchtruim\n" +
                "6. Onderhoud van alle vliegtuigen\n" +
                "7. Stop\n> ";
            int menuKeuze;
            Console.Write(menu);
            if (!int.TryParse(Console.ReadLine(), out menuKeuze) || menuKeuze <= 0 || menuKeuze > 7)
            {
                Console.WriteLine("Ongeldige keuze");
                Console.Clear();
                showMenu();
            }
            return menuKeuze;
        }
        
        private static void verder()
        {
            Console.WriteLine("Druk op een toets om verder te gaan");
            Console.ReadKey();
        }

        private static string zoekVliegtuig(string str)
        {
            Vliegtuig vt = ct.zoekVliegtuig(str);
            if(vt == null)
                Console.WriteLine("Vliegtuig " + str + " is niet gekend in het systeem");
            return str;
        }
    }
    
}