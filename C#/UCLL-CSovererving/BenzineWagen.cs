using System;

namespace overerving
{
    public class BenzineWagen : Wagen
    {
        public decimal Co2;

        public BenzineWagen(decimal catalogusPrijs, string nummerplaat, decimal co2) : base(catalogusPrijs, nummerplaat)
        {
            if (co2 <= 0) throw new Exception("Waarde van C02 mag niet negatief zijn");

            this.co2 = co2;
        }

        private decimal co2
        {
            get => Co2;
            set => Co2 = value;
        }

        public override decimal VAA()
        {
            return CatalogusPrijs * co2 / 1000;
        }

        public string ToString()
        {
            return base.ToString() + "CO2: " + co2;
        }
    }
}