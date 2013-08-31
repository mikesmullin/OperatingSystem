all: build vm_start
build:
	nasm -f bin -o myfirst.bin myfirst.asm
start:
# skipping iPXE boot saves 5-10 seconds which speeds up testing iterations
# i like that we pass the asm binary as the hard disk image and it boots
	qemu-system-i386 \
	 -netdev user,id=hostnet0 -device virtio-net-pci,romfile=,netdev=hostnet0 \
	 -monitor unix:/tmp/qemu-monitor,server,nowait \
	 myfirst.bin
reload: build reset
reset:
	echo system_reset | socat - UNIX-CONNECT:/tmp/qemu-monitor 2>&1 1>/dev/null
watch:
	./watch.sh myfirst.asm make reload
