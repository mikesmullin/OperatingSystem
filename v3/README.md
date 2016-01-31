# Simple Operating System

A fun educational exercise.

see also: [Writing a Simple Operating System from Scratch]
          (https://www.cs.bham.ac.uk/~exr/lectures/opsys/10_11/lectures/os-dev.pdf)
          by Nick Blundell

## Install

Need these dependencies on Ubuntu 12.04.

1) install a good modern assembler
```bash
# asm compiler
sudo apt-get install nasm
```

or try [PASM](http://pasm.pis.to/#test) the X86-64 (AMD64) assembler that runs in your browser.

2) choose an emulator

```bash
# bochs emulator, or;
sudo apt-get install bochs bochs-x bochs-sdl

# qemu emulator
sudo apt-get install qemu
```
note there is also a [browser-based x86 emulator](http://copy.sh/v86/)  
but i am developing for 64-bit so i don't use it much.

3) optionally choose one or more hex editors
```bash
# recommended
sudo apt-get install jeex

# various alternatives
sudo apt-get install ghex okteta bless wxhexeditor
```

and of course there is a really nice [browser-based hex editor](https://hexed.it/)

## Build

Choose one of the examples from the [boot/](boot/) directory,
and pass the basename of the file as the first argument below.

```bash
BOOT=hello make
```

## Test

```bash
BOOT=hello make bochs # or;
BOOT=hello make qemu
```

## Reference

see also:

- [NASM Language](http://www.nasm.us/xdoc/2.11.08/html/nasmdoc3.html)
