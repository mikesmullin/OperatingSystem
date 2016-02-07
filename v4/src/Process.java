public class Process
{
	public static void die(final String reason)
	{
		die(reason, null);
	}

	public static void die(final Throwable reason)
	{
		die(null, reason);
	}

	public static void die(final String reason, final Throwable detail)
	{
		if (detail != null)
		{
			detail.printStackTrace();
		}
		try
		{
			// necessary or messages will print out-of-order.
			// not totally sure why but must be multiple threads somewhere
			Thread.sleep(250);
		}
		catch (final InterruptedException ignored)
		{
		}
		Logger.err("Aborting." + (reason == null ? "" : " Reason: " + reason));
		System.exit(1);
	}
}
