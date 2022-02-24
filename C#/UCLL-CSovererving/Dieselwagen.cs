using System;

namespace overerving
{
    public class Dieselwagen : Wagen
    {
        public decimal Nox;

        public Dieselwagen(decimal catalogusPrijs, string nummerplaat, decimal nox) : base(catalogusPrijs, nummerplaat)
        {
            if (nox <= 0) throw new Exception("Waarde van C02 mag niet negatief zijn");
            this.nox = nox;
        }

        private decimal nox
        {
            get => Nox;
            set => Nox = value;
        }

        public string ToString()
        {
            return base.ToString() + "NOx: " + nox;
        }

        public override decimal VAA()
        {
            return catalogusPrijs * nox / 1000;
        }
    }
}