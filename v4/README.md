# ASM via Java

I'm interested in writing a simple bootloader and operating system for a specific architecture
which is Intel x86_64, or basically what would easily run on most commodity hardware today, or
inside of a virtual machine.

Reading the 
[Intel x86 Instruction Set](docs/intel x86 instruction set.pdf) 
isn't so bad, especially with [cheat sheets](http://ref.x86asm.net/coder32.html).

However the problem with those reference manuals, as well as traditional assembly languages like
NASM and GAS, are they're very old-school. For example, the Intel docs are devoid of hyperlinks.
They should be a very dynamic HTML format but even the cheat sheet is not utilizing modern features
of HTML5 + CSS + Javascript. Its clear these guys are systems programmers, and have not the inter-disciplinary
skillset of user space browser application development.

Editors for ASM may support syntax highlighting but that's about it. No click-through, indexing, outline view,
interactive debugging, strong typing, or many of the features of modern IDEs.

AFAICT, there's really no reason for this. My best guess is that its such an old, boring topic, and the people
who know it best don't know the web technology well, and so the state of the art there is stuck in 1980.

This repository is an attempt to leap frog the experience of writing machine code, but simplifying the targets 
that can be built, entrusting the human to perform optimizations (a mistake, but a compromise for now), 
in order to write it in Java 8.

I feel like this will enable me to write software like bootloaders and a simple operating system much easier
than it would be by following the traditional approach of combining assembly and c with linkers. IMO it will
be possible to write something like a simple network router operating system with this type of approach. I
doubt it would ever reach the level of something full-featured like a Windows or Linux Desktop OS; nobody is
going to abandon the Linux kernel.

As an educational exercise, I'm publishing in the hope that others may also find this interesting and fun.


## Install

Install latest stable Java.

## Build

Compile [Main.java](src/Main.java) and execute it.

