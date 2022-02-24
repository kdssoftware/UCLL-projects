namespace ExamenOpdracht1
{
    public class Bike : Vehicle
    {
        public bool syncBrakeSystem;

        public Bike(int hp, bool syncBrakeSystem) : base(hp)
        {
            this.syncBrakeSystem = syncBrakeSystem;
        }

        public override string ToString()
        {
            return "| PK: " + hp + " | Sync brake system: " + syncBrakeSystem;
        }
    }
}