using System;

namespace overerving
{
    public abstract class Wagen //Parent
    {
        public decimal CatalogusPrijs;
        public string Nummerplaat;

        public Wagen(decimal catalogusPrijs, string nummerplaat)
        {
            if (catalogusPrijs == null || catalogusPrijs <= 0 || nummerplaat.Trim().Equals(""))
                throw new Exception("Vul alle waarden juist in.");
            this.nummerplaat = nummerplaat;
            this.catalogusPrijs = catalogusPrijs;
        }

        protected decimal catalogusPrijs
        {
            get => CatalogusPrijs;
            set => CatalogusPrijs = value;
        }

        protected string nummerplaat
        {
            get => Nummerplaat;
            set => Nummerplaat = value;
        }

        public string ToString()
        {
            return "Nummerplaat: " + nummerplaat + " / Catalogusprijs: " + catalogusPrijs + " / ";
        }

        public abstract decimal VAA();
    }
}