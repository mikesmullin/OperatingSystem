; taken from http://mikeos.berlios.de/write-your-own-os.html
; i like it because its minimalist; no multiboot, no libc
; just BIOS calls
  BITS 16
start:
  mov ax, 07c0h
  add ax, 288
  mov ss, ax
  mov sp, 4096
  mov ax, 07c0h
  mov ds, ax
  mov si, text_string
  call clear_screen
  call print_string
  jmp $
  text_string db 'This is my cool new OS2!', 0
clear_screen:
  mov ah, 07h
  int 10h
  ret
print_string:
  mov ah, 0Eh
.repeat:
  lodsb
  cmp al, 0
  je .done
  int 10h
  jmp .repeat
.done:
  ret
  times 510-($-$$) db 0
  dw 0xAA55
