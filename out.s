.data
L 0:
.asciz ""\n>>>yes my first asm!\n\n""

.text
.global main
push {fp, lr, v1, v2, v3 ,v4, v5}
add fp, sp, #24
ldr a1, =L0
bl printf(PLT)
sub sp, fp, #24
pop {fp, pc, v1, v2, v3 ,v4, v5}
