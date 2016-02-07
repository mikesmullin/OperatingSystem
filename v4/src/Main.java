import java.nio.file.Path;
import java.nio.file.Paths;

// TODO: publish as an example of better ASM language tooling

public class Main
{
	public static void main(final String args[])
	{
		// output a boot loader bin file to disk
		Logger.info("composing binfile in-memory from hard-coded bytes...");
		final Path path = Paths.get("./boot/hello.bin");
		final byte[] BOOTLOADER_MAGIC_BYTES = new byte[]{(byte) 0x55, (byte) 0xAA};
		final int BOOTLOADER_MAGIC_OFFSET = 510;
		final Asm asm = new Asm();

		// generate opcode instructions
//		loopBootLoader(asm);
		helloBootLoader(asm);

		asm
			// BIOS identifies as boot loader by
			// locating magic bytes at specific offset
			.setCursor(BOOTLOADER_MAGIC_OFFSET)
			.write(BOOTLOADER_MAGIC_BYTES);

		Logger.info("writing binfile to disk as " + path);
		asm
			.publish(path);

		Logger.info("done!");
	}

	public static void loopBootLoader(final Asm asm)
	{
		asm
			// perform an infinite loop
			.label("loop")
			.jump("loop");
	}

	// TODO: define a simple hello world example
	public static void helloBootLoader(final Asm asm)
	{
		// BIOS interrupt value indicating Tele-type mode
		final byte BIOS_TELETYPE_ROUTINE = (byte) 0x0E;
		asm
			// print text to screen and loop
			.write(new byte[]{
				(byte) 0xB4, // MOV next byte into 8-bit AH register
				BIOS_TELETYPE_ROUTINE,

				(byte) 0xB0, // MOV next byte into 8-bit AL register
				(byte) 0x48, // 'H'

				(byte) 0xCD, // INT
				(byte) 0x10, // #10; tells BIOS to print single character
				             // on screen and advance cursor

				(byte) 0xB0,
				(byte) 0x65, // 'e'

				(byte) 0xCD,
				(byte) 0x10,

				(byte) 0xB0,
				(byte) 0x6C, // 'l'

				(byte) 0xCD,
				(byte) 0x10,

				(byte) 0xB0,
				(byte) 0x6C, // 'l'

				(byte) 0xCD,
				(byte) 0x10,

				(byte) 0xB0,
				(byte) 0x6F, // 'o'

				(byte) 0xCD,
				(byte) 0x10,

				(byte) 0xE9, // JMP rel16
				(byte) 0xFD, // 16-bit signed int = -3
				(byte) 0xFF  // "
			});

//			// perform an infinite loop
//			.label("loop")
//			.jump("loop");
	}
}
