.data
L0:
.asciz "ciao mbare\n"

.text
.global main

main:
push {fp, lr, v1, v2, v3 ,v4, v5}
add fp, sp, #24
ldr a1, =L0
bl printf(PLT)
mov a1, #0
sub sp, fp, #24
pop {fp, pc, v1, v2, v3 ,v4, v5}
