using System;

namespace exceptions
{
    [Serializable]
    public class OfflineSatusException : Exception
    {
        public OfflineSatusException()
        {
        }

        public OfflineSatusException(string message) : base(message)
        {
        }

        public OfflineSatusException(string message, Exception inner) : base(message, inner)
        {
        }
    }

    [Serializable]
    public class VerbindingsFoutException : Exception
    {
        public VerbindingsFoutException()
        {
        }

        public VerbindingsFoutException(string message) : base(message)
        {
        }

        public VerbindingsFoutException(string message, Exception inner) : base(message, inner)
        {
        }
    }

    [Serializable]
    public class ConnectieSnelheidException : Exception
    {
        public ConnectieSnelheidException()
        {
        }

        public ConnectieSnelheidException(string message) : base(message)
        {
        }

        public ConnectieSnelheidException(string message, Exception inner) : base(message, inner)
        {
        }
    }
}