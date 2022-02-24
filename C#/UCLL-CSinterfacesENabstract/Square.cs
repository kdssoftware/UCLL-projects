using System;

namespace interfacesENabstract
{
    public class Square : Shape
    {
        public Square(decimal sideLenght, string naam, ColorTypes colorType, int colorDepth) : base(naam, colorType,
            colorDepth)
        {
            SideLenght = sideLenght;
        }

        public decimal SideLenght { get; set; }

        public override decimal GetSurface()
        {
            return 2.0M * (decimal) Math.PI * SideLenght;
        }

        public override decimal GetCircumference()
        {
            return (decimal) Math.PI * (decimal) Math.Pow((double) SideLenght, 2);
        }

        public override string ToString()
        {
            return "Vierkant omtrek: " + GetCircumference() + " / oppervlakte: " + GetSurface() + " / " +
                   base.ToString();
        }
    }
}