using System;

namespace interfacesENabstract
{
    public class Circle : Shape
    {
        public Circle(decimal radius, string naam, ColorTypes colorType, int colorDepth) : base(naam, colorType,
            colorDepth)
        {
            Radius = radius;
        }

        public decimal Radius { get; set; }

        public override decimal GetSurface()
        {
            return 4.0M * Radius;
        }

        public override decimal GetCircumference()
        {
            return (decimal) Math.Pow((double) Radius, 2);
        }

        public override string ToString()
        {
            return "Cirkel omtrek: " + GetCircumference() + " / oppervlakte: " + GetSurface() + " / " + base.ToString();
        }
    }
}