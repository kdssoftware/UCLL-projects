using System;
using System.Collections.Generic;

namespace luchthaven
{
    public class ControleToren
    {
        private List<Vliegtuig> vliegtuigen = new List<Vliegtuig>();
        private TechnischeDienst td = new TechnischeDienst();
        
        public Vliegtuig zoekVliegtuig(string str)
        {
            Vliegtuig vt = null;
            foreach (var vliegtuig in vliegtuigen)
            {
                if (vliegtuig.Vluchtcode.Equals(str))
                {
                     vt = vliegtuig;
                }
            }
            return vt;
        }

        public string meldVliegtuigAan(string str)
        {
            string returnStr;
            if (str.Trim().Equals(""))
            {
                return "Lege vluchtcode, Vliegtuig niet aangemeld";
            }
            foreach (var vliegtuig in vliegtuigen)
            {
                if (vliegtuig.Vluchtcode.Equals(str))
                {
                    return "Vluchtcode werd reeds toegewezen.";
                }
            }
            Vliegtuig vliegtuigNieuw = new Vliegtuig(str,true,"geen");
            vliegtuigen.Add(vliegtuigNieuw);
            return "Vliegtuig met code " + str + " werd aangemeld.";

        }
        
        public string toestemmingTotLanden(string str)
        {
            foreach (var vliegtuig in vliegtuigen)
            {
                if (vliegtuig.Vluchtcode.Equals(str))
                {
                    
                    string returnStr = vliegtuig.Airborn
                        ? "Vliegtuig " + vliegtuig.Vluchtcode + " is geland."
                        : "Vliegtuig " + vliegtuig.Vluchtcode + " was reeds geland";
                    vliegtuig.land();
                    return returnStr;
                }
            }
            return "";
        }

        public string toestemmingTotOpstijgen(string str)
        {
            foreach (var vliegtuig in vliegtuigen)
            {
                if (vliegtuig.Vluchtcode.Equals(str))
                {
                    vliegtuig.stijgOp();
                    td.voerOnderhoudUit(vliegtuig);
                    string onderhoudStr ="";
                    if (!vliegtuig.OnderhoudNodig.Equals(OnderhoudType.Geen))
                    {
                        onderhoudStr = "(+ reparatie uitgevoerd)";
                    }
                    return "Vliegtuig "+str+" is opgestegen "+onderhoudStr;
                }
            }

            return "";
        }

        public string verwijderUitLuchtruim(string str)
        {
            
            foreach (var vliegtuig in vliegtuigen)
            {
                if (vliegtuig.Vluchtcode.Equals(str))
                {
                    vliegtuigen.Remove(vliegtuig);
                    return "Vliegtuig "+str+" verliet het luchtruim";
                }
            }

            return "";
        }

        public int repareerVliegtuigen()
        {
            return td.voerOnderhoudUit(vliegtuigen);
        }

        public string geefOverzicht()
        {
            string str = "";
            foreach (var vliegtuig in vliegtuigen)
            {
                str += vliegtuig.geefOmschrijving() + "\n";
            }

            if (str.Length == 0)
            {
                str = "Geen vliegtuigen in het overzicht";
            }
            return str;
        }
    }
}