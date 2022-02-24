using System.Collections.Generic;

namespace luchthaven
{
    public class TechnischeDienst
    {

        public bool voerOnderhoudUit(Vliegtuig vliegtuig)
        {
            if (!vliegtuig.Airborn && vliegtuig.OnderhoudNodig!=OnderhoudType.Geen)
            {
                return true;
            }
            return false;
        }

        public int voerOnderhoudUit(List<Vliegtuig> vliegtuigen)
        {
            int totaal = 0;
            foreach (var vliegtuig in vliegtuigen)
            {
                if (!vliegtuig.Airborn && vliegtuig.OnderhoudNodig!=OnderhoudType.Geen)
                {
                    totaal++;
                    vliegtuig.OnderhoudNodig = OnderhoudType.Geen;
                }
            }
            return totaal;
        }
    }
}