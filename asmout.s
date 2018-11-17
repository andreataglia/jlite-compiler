.data
L0:
.asciz "%i\n"
L1:
.asciz "%i\n"
L2:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #16
mov r6, #0
mov r5, r6
str r5, [r11, #-32]
mov r8, #1
mov r7, r8
str r7, [r11, #-36]
ldr r0, =L0
ldr r4, [r11, #-32]
mov r1, r4
bl printf(PLT)
ldr r0, =L1
ldr r5, [r11, #-36]
mov r1, r5
bl printf(PLT)
ldr r8, [r11, #-32]
ldr r4, [r11, #-36]
orr r8, r8, r4
mov r6, r8
str r6, [r11, #-40]
ldr r0, =L2
ldr r5, [r11, #-40]
mov r1, r5
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
