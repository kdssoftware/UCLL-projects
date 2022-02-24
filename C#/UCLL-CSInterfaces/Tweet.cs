namespace interfaces
{
    public class Tweet : IBericht
    {
        public Tweet(string message, string hashtag, string account)
        {
            this.hashtag = hashtag;
            this.message = message;
            this.account = account;
        }

        public string hashtag { get; set; }

        public string account { get; set; }

        public string message { get; set; }

        public string GetMessageInfo()
        {
            return "--- Tweet ---\n" +
                   "Tweet account: " + hashtag + "\n" +
                   "Hashtag: " + hashtag + "\n" +
                   "account: " + account + "\n";
        }
    }
}