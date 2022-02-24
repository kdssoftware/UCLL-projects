using System;
using System.Collections.Generic;

namespace interfacesENabstract
{
    internal class Program
    {
        public static List<IColorObject> lijst = new List<IColorObject>();

        public static void Main(string[] args)
        {
            do
            {
                switch (Menu())
                {
                    case 1:
                        vorm();
                        break;
                    case 2:
                        foto();
                        break;
                    case 3:
                        overzicht();
                        break;
                    case 4:
                        Environment.Exit(0);
                        break;
                }
            } while (true);
        }

        private static void overzicht()
        {
            Console.Write("Overzicht kleurobjecten:\n");
            foreach (var ding in lijst) Console.Write(ding + "\n");
            verder();
        }

        private static void foto()
        {
            Console.Write("Foto locatie: ");
            var location = Console.ReadLine();
            Console.Write("Kleurtype:\n1. Zwart-wit\n2. Kleur\n");
            var keuze = 0;
            var kleurDiepte = 0;
            var kleurtype = ColorTypes.BlackAndWhite;
            int.TryParse(Console.ReadLine(), out keuze);
            switch (keuze)
            {
                case 1:
                    kleurtype = ColorTypes.BlackAndWhite;
                    break;
                case 2:
                    kleurtype = ColorTypes.Color;
                    Console.Write("Kleur diepte: ");
                    int.TryParse(Console.ReadLine(), out kleurDiepte);
                    break;
                default:
                    Console.Write("Deze optie is niet mogelijk\n");
                    Console.ReadLine();
                    Console.Clear();
                    foto();
                    break;
            }

            lijst.Add(new Picture(location, kleurtype, kleurDiepte));
            verder();
        }

        private static void NieuwVierkant()
        {
            Console.Write("Lengte Zijde: ");
            int zijde;
            int.TryParse(Console.ReadLine(), out zijde);
            Console.Write("Naam vorm: ");
            var naam = Console.ReadLine();
            Console.Write("Kleurtype:\n1. Zwart-wit\n2. Kleur\n");
            var keuze = 0;
            var kleurDiepte = 0;
            var kleurtype = ColorTypes.BlackAndWhite;
            int.TryParse(Console.ReadLine(), out keuze);
            switch (keuze)
            {
                case 1:
                    kleurtype = ColorTypes.BlackAndWhite;
                    break;
                case 2:
                    kleurtype = ColorTypes.Color;
                    Console.Write("Kleur diepte: ");
                    int.TryParse(Console.ReadLine(), out kleurDiepte);
                    break;
                default:
                    Console.Write("Deze optie is niet mogelijk\n");
                    verder();
                    Console.Clear();
                    NieuwVierkant();
                    break;
            }

            lijst.Add(new Square(zijde, naam, kleurtype, kleurDiepte));
        }

        private static void NieuwCirkel()
        {
            Console.Write("Straal: ");
            int radius;
            int.TryParse(Console.ReadLine(), out radius);
            Console.Write("Naam vorm: ");
            var naam = Console.ReadLine();
            Console.Write("Kleurtype:\n1. Zwart-wit\n2. Kleur\n");
            var keuze = 0;
            var kleurDiepte = 0;
            var kleurtype = ColorTypes.BlackAndWhite;
            int.TryParse(Console.ReadLine(), out keuze);
            switch (keuze)
            {
                case 1:
                    kleurtype = ColorTypes.BlackAndWhite;
                    break;
                case 2:
                    kleurtype = ColorTypes.Color;
                    Console.Write("Kleur diepte: ");
                    int.TryParse(Console.ReadLine(), out kleurDiepte);
                    break;
                default:
                    Console.Write("Deze optie is niet mogelijk\n");
                    verder();
                    Console.Clear();
                    NieuwCirkel();
                    break;
            }

            lijst.Add(new Circle(radius, naam, kleurtype, kleurDiepte));
        }

        private static void vorm()
        {
            Console.Write("1. Vierkant\n2. Cirkel\n");
            int keuze;
            int.TryParse(Console.ReadLine(), out keuze);
            switch (keuze)
            {
                case 1:
                    NieuwVierkant();
                    break;
                case 2:
                    NieuwCirkel();
                    break;
                default:
                    Console.Write("Deze optie is niet mogelijk\n");
                    vorm();
                    break;
            }

            verder();
        }

        private static void verder()
        {
            Console.Write("Druk op een toets om verder te gaan");
            Console.ReadLine();
        }

        private static int Menu()
        {
            Console.Clear();
            var str = " *** Beheer kleurobjecten ***\n1. Voeg vorm toe\n2. Voeg foto toe\n3. Geef overzicht\n4. Stop\n";
            Console.Write(str);
            int keuze;
            int.TryParse(Console.ReadLine(), out keuze);
            return keuze;
        }
    }
}