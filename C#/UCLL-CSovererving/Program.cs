using System;
using System.Collections;

namespace overerving
{
    internal class Program
    {
        private static readonly ArrayList wagens = new ArrayList();

        public static void Main(string[] args)
        {
            var run = true;
            do
            {
                Console.Write(Menu());
                switch (Console.ReadLine())
                {
                    case "1":
                        WagenToevoegen();
                        Verder();
                        break;
                    case "2":
                        Overzicht();
                        Verder();
                        break;
                    case "3":
                        TotaalVAA();
                        Verder();
                        break;
                    case "4":
                        Environment.Exit(0);
                        break;
                    default:
                        Console.WriteLine("Deze optie niet gevonden.");
                        break;
                }

                Console.Clear();
            } while (run);
        }

        private static void TotaalVAA()
        {
            decimal d = 0;
            foreach (var w in wagens) d += ((Wagen) w).VAA();
            Console.WriteLine("Het totaal te betalen VAA: " + d);
        }

        public static void WagenToevoegen()
        {
            Console.Write("Type\n1. Diesel\n2. Benzine\n> ");
            var option = Console.ReadLine();
            Console.Write("NummerPlaat: ");
            var nummerplaat = Console.ReadLine();
            Console.Write("Catalogusprijs: ");
            decimal catalogusprijs = -1;
            try
            {
                catalogusprijs = decimal.Parse(Console.ReadLine());
            }
            catch (FormatException e)
            {
                Console.Write("");
            }

            decimal nr = -1;
            try
            {
                if (option == "2")
                {
                    Console.Write("CO2: ");
                    try
                    {
                        nr = decimal.Parse(Console.ReadLine());
                    }
                    catch (FormatException e)
                    {
                        Console.Write("");
                    }

                    wagens.Add(new BenzineWagen(catalogusprijs, nummerplaat, nr));
                }
                else if (option == "1")
                {
                    Console.Write("NOx: ");
                    try
                    {
                        nr = decimal.Parse(Console.ReadLine());
                    }
                    catch (FormatException e)
                    {
                        Console.Write("");
                    }

                    wagens.Add(new Dieselwagen(catalogusprijs, nummerplaat, nr));
                }
                else
                {
                    Console.WriteLine("Type niet gekend!");
                    WagenToevoegen();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public static void Overzicht()
        {
            var str = string.Empty;
            foreach (var wagen in wagens)
            {
                if (wagen is Dieselwagen) str += ((Dieselwagen) wagen).ToString() + "\n";

                if (wagen is BenzineWagen) str += ((BenzineWagen) wagen).ToString() + "\n";
            }

            Console.Write(str);
        }

        public static void Verder()
        {
            Console.WriteLine("Druk op een toets om verder te gaan.");
            Console.ReadKey();
        }

        public static string Menu()
        {
            return
                " ** VAA berekening **\n1. Geef een wagen in.\n2. Overzicht wagens\n3. Totaal VAA\n4. Stop programma\n> ";
        }
    }
}