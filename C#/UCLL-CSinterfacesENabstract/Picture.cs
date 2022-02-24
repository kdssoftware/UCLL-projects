namespace interfacesENabstract
{
    public class Picture : IColorObject
    {
        private Shape shape;

        public Picture(string location, ColorTypes colorType, int colorDepth)
        {
            Location = location;
            ColorType = colorType;
            ColorDepth = colorDepth;
        }

        public string Location { get; set; }

        public ColorTypes ColorType { get; set; }

        public int ColorDepth { get; set; }

        public override string ToString()
        {
            return "Foto locatie: " + Location + " / Kleur type: " + ColorType + " / Kleur Diepte " + ColorDepth;
        }
    }
}