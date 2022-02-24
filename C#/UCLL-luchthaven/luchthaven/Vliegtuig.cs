using System;

namespace luchthaven
{
    public class Vliegtuig
    {
        private string vluchtcode;
        private bool airborn;
        private OnderhoudType onderhoudNodig;
        public string Vluchtcode
        {
            get => vluchtcode;
            set => vluchtcode = value;
        }

        public bool Airborn
        {
            get => airborn;
            set => airborn = value;
        }

        public OnderhoudType OnderhoudNodig
        {
            get => onderhoudNodig;
            set => onderhoudNodig = value;
        }

        public Vliegtuig(string vluchtcode,bool airborn,string onderhoudNodig)
        {
            this.airborn = airborn;
            this.vluchtcode = setVluchtcode(vluchtcode);
            this.onderhoudNodig = setOnderhoudNodig(onderhoudNodig);
        }
        
        private string setVluchtcode(string str)
        {
            return str.Length > 10? str.Substring(0,10) : str;
        }

        private OnderhoudType setOnderhoudNodig(string str)
        {
            try
            {   
                switch (str.ToLower().Trim())
                {
                    case "geen":
                        return OnderhoudType.Geen;
                    case "klein":
                        return OnderhoudType.Klein;
                    case "groot":
                        return OnderhoudType.Groot;
                    default:
                        throw new Exception("Deze onderhoudskeuze is niet gekent, de onderhoudskeuze word standaard als \"geen\" gezet.");
                }
                
            }
            catch (Exception err)
            {
                Console.WriteLine(err.Message);
                return OnderhoudType.Geen;
            }
        }

        public void land()
        {
            airborn = false;
            int rand = new Random().Next(0,11);
            if (rand > 7)
            {
                onderhoudNodig = OnderhoudType.Groot;
            }else if (rand < 3 && rand > 7)
            {
                onderhoudNodig = OnderhoudType.Klein;
            }
            else
            {
                onderhoudNodig = OnderhoudType.Geen;
            }
        }

        public void stijgOp()
        {
            airborn = true;
        }

        public string geefOmschrijving()
        {
            string str = "Vliegtuig - ";
            str += vluchtcode;
            str += "\tOnderhoudnodig: ";
            str += onderhoudNodig.ToString();
            str += "\tAirborn: ";
            str += geefBoolAsString(airborn);
            return str;
        }

        private string geefBoolAsString(bool b)
        {
            switch (b)
            {
                case true:
                    return "ja";
                case false:
                    return "nee";
                default:
                    return "error";
            }
        }
    }
}