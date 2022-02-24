namespace ExamenOpdracht1
{
    public class Car : Vehicle
    {
        public bool forWheelDrive;

        public Car(int hp, bool forWheelDrive) : base(hp)
        {
            this.forWheelDrive = forWheelDrive;
        }

        public override string ToString()
        {
            return "| PK: " + hp + " | 4wiel brake system: " + forWheelDrive;
        }
    }
}