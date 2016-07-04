[bits 16]
[org 0x7c00]
jmp 0x0000:start

start:
  mov ax, 0xa000
  mov es, ax
  call govideo
  mov bx, 0
  mov cx, 0
  mov dl, 0xe
  call plotpixel
  call waitkey

govideo:
  push ax
  mov ax,0013h
  int 10h
  pop ax
  ret

waitkey:
  mov ah,06
  mov dl,0ffh
  int 21h
  jz waitkey
  mov ax,0003h
  int 10h
  mov ax,4c00h
  int 21h

plotpixel:
  push dx
  mov ax,320
  xor dx,dx
  mul bx
  mov si,ax
  add si,cx
  pop dx
  mov [es:si],dl
  ret

times 510 - ($ - $$) db 0
dw 0xaa55
