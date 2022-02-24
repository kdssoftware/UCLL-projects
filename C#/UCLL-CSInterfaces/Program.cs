using System;
using System.Collections;

namespace interfaces
{
    internal class Program
    {
        public static ArrayList berichten = new ArrayList();

        public static void Main(string[] args)
        {
            var isRunning = true;
            do
            {
                Menu();
                var menuChoice = 0;
                if (!int.TryParse(Console.ReadLine(), out menuChoice))
                {
                    Console.Write("Onbekende Menukeuze, probeer opnieuw");
                    verder();
                }
                else
                {
                    switch (menuChoice)
                    {
                        case 1:
                            CreateNew("post-it");
                            break;
                        case 2:
                            CreateNew("tweet");
                            break;
                        case 3:
                            Detail();
                            break;
                        case 4:
                            Environment.Exit(0);
                            break;
                        default:
                            Console.Write("Onbekende Menukeuze, probeer opnieuw");
                            verder();
                            break;
                    }
                }

                Console.Clear();
            } while (isRunning);
        }

        private static void CreateNew(string type)
        {
            string[] text;
            if (type.Equals("tweet"))
                text = new[] {"Account:  ", "Hashtag: ", "Boodschap: "};
            else
                text = new[] {"Van:  ", "Locatie: ", "Boodschap: "};
            string[] input = {"", "", ""};
            var error = false;
            for (var i = 0; i < text.Length; i++)
            {
                Console.Write(text[i]);
                var waarde = Console.ReadLine();
                if (waarde.Trim().Equals(""))
                {
                    Console.WriteLine("Waarde is leeg!");
                    error = true;
                    break;
                }

                input[i] = waarde;
            }

            if (error)
            {
                verder();
            }
            else //No error;
            {
                if (type.Equals("tweet"))
                    berichten.Add(new Tweet(input[0], input[1], input[2]));
                else
                    berichten.Add(new PostIt(input[0], input[1], input[2]));
            }

            verder();
        }

        private static void Detail()
        {
            Console.WriteLine("Overzicht berichten:");
            var index = 1;
            var choice = -1;
            foreach (var bericht in berichten)
            {
                if (bericht is Tweet)
                    Console.WriteLine(index + ". " + "Tweet - " + ((Tweet) bericht).hashtag);
                else if (bericht is PostIt) Console.WriteLine(index + ". " + "PostIt - " + ((PostIt) bericht).locatie);
                index++;
            }

            do
            {
                Console.Write("Nummer bericht: ");
            } while (!int.TryParse(Console.ReadLine(), out choice) || choice < 1 || choice > index - 1);

            var _object = zoekBerichten(choice - 1);
            if (_object is Tweet)
            {
                var t = (Tweet) _object;
                Console.Write(t.GetMessageInfo());
            }
            else if (_object is PostIt)
            {
                var p = (PostIt) _object;
                Console.Write(p.GetMessageInfo());
            }

            verder();
        }

        private static object zoekBerichten(int index)
        {
            var i = 0;
            foreach (var b in berichten)
            {
                if (i == index) return b;
                i++;
            }

            return null;
        }

        private static void Menu()
        {
            Console.Write(
                " *** Bericht center ***\n1. Plak een post-it\n2. Post een tweet\n3. Bericht detail\n4. Stop\n> ");
        }

        private static void verder()
        {
            Console.WriteLine("Druk op een toets om verder te gaan");
            Console.ReadKey();
        }
    }
}