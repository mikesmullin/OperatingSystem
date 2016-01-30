all:
	nasm boot_sect.asm -f bin -o boot_sect.bin
qemu: all
	qemu-system-x86_64 boot_sect.bin
bochs: all
	bochs

