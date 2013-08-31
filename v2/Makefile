NOPXE = -netdev user,id=hostnet0 -device virtio-net-pci,romfile=,netdev=hostnet0
all: build vm
build:
	nasm -f bin -o myfirst.bin myfirst.asm
vm:
# skipping iPXE boot saves 5-10 seconds which speeds up testing iterations
# i like that we pass the asm binary as the hard disk image and it boots
	qemu-system-i386 $(NOPXE) myfirst.bin
