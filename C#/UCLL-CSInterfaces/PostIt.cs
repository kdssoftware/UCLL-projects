namespace interfaces
{
    public class PostIt : IBericht
    {
        public PostIt(string message, string locatie, string aanmaker)
        {
            this.message = message;
            this.locatie = locatie;
            this.aanmaker = aanmaker;
        }

        public string locatie { get; set; }

        public string aanmaker { get; set; }

        public string message { get; set; }

        public string GetMessageInfo()
        {
            return "--- Post-It ---\n" +
                   "Van: " + aanmaker + "\n" +
                   "Locatie: " + locatie + "\n" +
                   "Boodschap: " + message + "\n";
        }
    }
}