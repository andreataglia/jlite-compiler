.data
L0:
.asciz "%i"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
sub r13, r13, #12
ldr r0, [r11, #-36]
mov r1, #55
mov r0, r1
ldr r0, =L0
ldr r1, [r11, #-36]
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
