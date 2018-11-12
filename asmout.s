.data
L0:
.asciz "ciao mbare\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
sub r13, r13, #4
push {r4}
mov r4, #5
mov r7, r4
ldr r0, =L0
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
