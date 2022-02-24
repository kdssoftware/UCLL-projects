namespace ExamenOpdracht1
{
    public interface IDakarRacer
    {
        string RaceNumber { get; set; }

        int TotalTime { get; set; }

        void Race();
    }
}