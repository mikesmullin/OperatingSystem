import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Asm
{
	private final List<Byte> bytes = new ArrayList<>();
	private int cursor = 0;

	public Asm setCursor(final int position)
	{
		this.cursor = position;
		return this;
	}

	public Asm write(final byte[] b)
	{
		for (int i = 0; i < b.length; i++)
		{
			write(b[i]);
		}
		return this;
	}

	public Asm write(final byte b)
	{
		// if cursor position is beyond byte array capacity,
		// pad until capacity is equal
		while (this.bytes.size() < cursor)
		{
			this.bytes.add((byte) 0x00);
		}

		this.bytes.add(cursor, b);
		cursor++;
		return this;
	}

	public Asm publish(final Path path)
	{
		final byte[] b = new byte[this.bytes.size()];
		for (int i = 0; i < this.bytes.size(); i++)
		{
			b[i] = this.bytes.get(i);
		}
		FileSystem.writeBytesToFile(path, b);
		return this;
	}

	private Map<String, Integer> labels = new HashMap<>();

	/**
	 * Label the current position of the cursor.
	 *
	 * @param name Name of the label.
	 *             Used to refer back to this cursor location from other methods later.
	 */
	public Asm label(final String name)
	{
		labels.put(name, cursor);
		return this;
	}


	// x86 16-bit

	public Asm jump(final String labelName)
	{
		final int position = labels.get(labelName);
		final int offset = cursor - position - 2;
		return jump(Asm.signedByte(offset));
	}

	/**
	 * Jump short
	 *
	 * @param rel8 byte offset
	 */
	public Asm jump(final byte rel8)
	{
		write(new byte[]{
			(byte) 0xEB, // JMP rel8

			// A relative address in the range from 128 bytes
			// before the end of the instruction to 127 bytes
			// after the end of the instruction.
			rel8 // 8-bit signed char decimal value
		});
		return this;
	}

	/**
	 * Nomenclature
	 *
	 * Computer Architecture:
	 *
	 *   | bits | name    |
	 *   |    1 | bit     |
	 *   |    4 | nibble  |
	 *   |    8 | byte    |
	 *   |   12 | tribble |
	 *
	 * Intel Architecture:
	 *
	 *   | bytes | name  | abbrev |
	 *   |     1 | byte  | cb     |
	 *   |     2 | word  | cw     |
	 *   |     4 | dword | cd     |
	 *   |     6 |       | cp     |
	 *   |     8 | qword | co     |
	 *   |    10 |       | ct     |
	 *
	 * IA requires signed integers via Two's-Compliment.
	 * IA requires Least-Significant Byte First.
	 */

	/**
	 * Convert signed integer to single byte.
	 *
	 * @param number An integer between -128 to 127.
	 *               Throws exception outside this range.
	 * @return Bytecode representation.
	 */
	public static byte signedByte(final int number)
	{
		if (number < -128 || number > 127)
		{
			throw new RuntimeException("Number " + number + " outside range.");
		}
		return (byte) (number < 0 ? number + 256 : number);
	}

//	public static byte[] signedWord(final int i)
//	{
//		return new byte[]{(byte) 0xFE};
//	}
}
