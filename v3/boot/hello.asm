[bits 16]                               ; 16-bit code
[org 0x7c00]                            ; BIOS loads us at 0x07c0:0000

initialize_bios:
        mov si, welcome                 ; first argument to function is string to print
        call print

halt:
        hlt                             ; halt CPU (saves power vs. infinite loop)
        jmp halt                        ; loop if halt interrupted

print:                                  ; Print string in SI with bios
        mov al, [si]
        inc si
        or al, al
        jz exit_function                ; end at NUL
        mov ah, 0x0e                    ; op 0x0e
        mov bh, 0x00                    ; page number
        mov bl, 0x07                    ; color
        int 0x10                        ; INT 10 - BIOS print char
        jmp print
exit_function:
        ret

data:
        welcome db 'Hello World!', 0    ; welcome message

times 510 - ($ - $$) db 0               ; should fill to 510 bytes. For demo changed to 200 bytes.
dw 0xaa55                               ; boot signature (fills to 512 bytes)