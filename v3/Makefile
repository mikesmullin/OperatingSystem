BASENAME=boot/$(BOOT)

all:
	nasm "$(BASENAME).asm" -f bin -o "$(BASENAME).bin"
qemu: all
	qemu-system-x86_64 "$(BASENAME).bin"
bochs: all
	bochs 'display_library: sdl' 'floppya: 1_44="$(BASENAME).bin", status=inserted' 'boot: floppy'

