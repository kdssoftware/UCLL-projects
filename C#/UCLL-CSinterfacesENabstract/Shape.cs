namespace interfacesENabstract
{
    public abstract class Shape : IColorObject
    {
        public Shape(string naam, ColorTypes colorType, int colorDepth)
        {
            ColorType = colorType;
            ColorDepth = colorDepth;
            Naam = naam;
        }

        public string Naam { get; set; }

        public ColorTypes ColorType { get; set; }

        public int ColorDepth { get; set; }

        public override string ToString()
        {
            var str = "Kleur type: " + ColorType + " / Kleur Diepte " + ColorDepth;
            if (Naam != null || !Naam.Trim().Equals("")) return "Naam: " + Naam + " / " + str;

            return str;
        }

        public abstract decimal GetSurface();
        public abstract decimal GetCircumference();
    }
}