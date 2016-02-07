import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Logger
{
	private static final long started = System.currentTimeMillis();

	private enum Type
	{
		INFO,
		STDIN,
		STDOUT,
		STDERR,
		ERR
	}

	private static String host;

	public static Class<Logger> setHost(final String host)
	{
		Logger.host = host;
		return Logger.class;
	}

	public static void info(final String msg)
	{
		write(Type.INFO, msg);
	}

	public static void stdin(final String msg)
	{
		write(Type.STDIN, msg);
	}

	public static void stdout(final String msg)
	{
		write(Type.STDOUT, msg);
	}

	public static void stderr(final String msg)
	{
		write(Type.STDERR, msg);
	}

	public static void err(final String msg)
	{
		write(Type.ERR, msg);
	}

	public static void out(final String msg)
	{
		System.out.println(msg);
	}

	private static final Pattern NEWLINE_PATTERN = Pattern.compile("\\n");

	private static void write(final Type type, final String msg)
	{
		final String msDiff = "" + (System.currentTimeMillis() - started);
		final String pad = "\n" + String.join(" ", Collections.nCopies(
			msDiff.length() + 3 +
				(host == null ? 0 : host.length() + 1) +
				type.toString().length() + 3 + 1, ""));
		final StringBuffer sb = new StringBuffer();

		sb
			.append(Color.WHITE)
			.append(msDiff)
			.append("ms")
			.append(Color.RESET)
			.append(" ");
		if (host != null)
			sb
				.append(Rainbow.getColor(host))
				.append(host)
				.append(Color.RESET)
				.append(" ");
		sb
			.append(Color.GRAY)
			.append("[")
			.append(type.toString().toLowerCase())
			.append("]")
			.append(Color.RESET)
			.append(" ");
		switch (type)
		{
			case INFO:
				sb.append(Color.BROWN);
				break;
			case STDIN:
				sb.append(Color.CYAN);
				break;
			case STDOUT:
				sb.append(Color.MAGENTA);
				break;
			case STDERR:
			case ERR:
				sb.append(Color.LIGHT_RED);
				break;
		}

		final Matcher matcher = NEWLINE_PATTERN.matcher(msg);
		sb
			.append(matcher.replaceAll(pad)) // pad msg newlines w/ spaces
			.append(Color.RESET)
			.append("\n");

		System.out.print(sb.toString());
	}

	/**
	 * Index of random input strings to foreground color,
	 * in contrast with each other and a black background.
	 */
	private static final class Rainbow
	{
		private static int id = 0;
		private static HashMap<String, Color> hash = new HashMap<>();
		private static Color[] rainbow = new Color[]{
			Color.BLUE, Color.MAGENTA, Color.CYAN, Color.RED,
			Color.GREEN, Color.BROWN, Color.YELLOW,
			Color.LIGHT_BLUE, Color.LIGHT_PURPLE, Color.LIGHT_CYAN,
			Color.LIGHT_RED, Color.LIGHT_GREEN
		};

		public static Color getColor(final String s)
		{
			Color color = hash.get(s);
			if (color == null)
			{
				hash.put(s, color = rainbow[id++ % rainbow.length]);
			}
			return color;
		}
	}

	/**
	 * ANSI compatible color strings.
	 */
	public enum Color
	{
		RESET("\u001b[0m"),
		BLACK("\u001b[0;30m"),
		RED("\u001b[0;31m"),
		GREEN("\u001b[0;32m"),
		BROWN("\u001b[0;33m"),
		BLUE("\u001b[0;34m"),
		MAGENTA("\u001b[0;35m"),
		CYAN("\u001b[0;36m"),
		GRAY("\u001b[0;37m"),
		DARK_GRAY("\u001b[1;30m"),
		LIGHT_RED("\u001b[1;31m"),
		LIGHT_GREEN("\u001b[1;32m"),
		YELLOW("\u001b[1;33m"),
		LIGHT_BLUE("\u001b[1;34m"),
		LIGHT_PURPLE("\u001b[1;35m"),
		LIGHT_CYAN("\u001b[1;36m"),
		WHITE("\u001b[1;37m");

		private final String color;

		Color(final String color)
		{
			this.color = color;
		}

		public String toString()
		{
			return color;
		}
	}
}
