import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystem
{
	public static String readFileToString(final Class cls, final String file)
	{
		return new String(readFileToBytes(getResourcePath(cls, file)), StandardCharsets.UTF_8);
	}

	public static String readFileToString(final String file)
	{
		return new String(readFileToBytes(getResourcePath(file)), StandardCharsets.UTF_8);
	}

	private static void abortDueToMissingFile(final String file, final Exception e)
	{
		Process.die(new RuntimeException("Unable to find file \"" + file + "\".", e));
	}

	public static Path getResourcePath(final Class cls, final String... path)
	{
		final String file = String.join("/", path);
		final URL resource = cls.getResource(file);
		return getResourcePath(file, resource);
	}

	public static Path getResourcePath(final String file) {
		final ClassLoader classLoader = FileSystem.class.getClassLoader();
		final URL resource = classLoader.getResource(file);
		return getResourcePath(file, resource);
	}

	private static Path getResourcePath(final String file, final URL resource)
	{
		try
		{
			if (resource != null)
			{
				final Path path = new File(resource.getFile()).toPath();
				if (Files.exists(path))
				{
					return path;
				}
			}
			abortDueToMissingFile(file, null);
		}
		catch (final Exception e)
		{
			abortDueToMissingFile(file, e);
		}
		return new File("").toPath(); // will never be reached
	}

	public static FileReader getFileReader(final Path file)
	{
		try
		{
			return new FileReader(file.toFile());
		}
		catch (final FileNotFoundException e)
		{
			abortDueToMissingFile(file.toString(), e);
			return null;
		}
	}

	public static byte[] readFileToBytes(final Path file)
	{
		try
		{
			return Files.readAllBytes(file);
		}
		catch (final IOException e)
		{
			abortDueToMissingFile(file.toString(), e);
			return new byte[0]; // will never be reached
		}
	}

	/**
	 * Write string to local file system.
	 */
	public static void writeStringToFile(final Path file, final String content)
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(file.toFile());
			writer.write(content);
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
				writer.close();
		}
	}

	/**
	 * Write bytes to local file system.
	 */
	public static void writeBytesToFile(final Path file, final byte[] content)
	{
		FileOutputStream writer = null;
		try
		{
			writer = new FileOutputStream(file.toFile());
			writer.write(content);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
				try
				{
					writer.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
		}
	}

	/**
	 * Delete file from local file system.
	 */
	public static void unlink(final Path path)
	{
		try {
			Files.delete(path);
		}
		catch (final IOException e) {
			abortDueToMissingFile(path.toString(), e);
		}
	}
}
