PATH := ./tools/i586-elf-4.8.1-Linux-x86_64/bin/:${PATH}

prebuild:
	mkdir -p build/
	@echo ${PATH}
boot: prebuild
	nasm -felf boot.asm -o build/boot.o
kernel: prebuild
	i586-elf-gcc -c kernel.c -o build/kernel.o -std=gnu99 -ffreestanding -O2 -Wall -Wextra
linker: prebuild
	i586-elf-gcc -T linker.ld -o build/myos.bin -ffreestanding -O2 -nostdlib build/boot.o build/kernel.o -lgcc
emulator:
	qemu-system-i386 -kernel build/myos.bin
all: boot kernel linker emulator

iso: prebuild
	mkdir -p build/isodir/boot/grub/
	echo "menuentry \"myos\" {\n\tmultiboot /boot/myos.bin\n}" > build/isodir/boot/grub/grub.cfg
	cp build/myos.bin build/isodir/boot/myos.bin
	grub-mkrescue -o build/myos.iso build/isodir
emulator-iso:
	qemu-system-i386 -cdrom build/myos.iso

clean:
	rm -rf build/
