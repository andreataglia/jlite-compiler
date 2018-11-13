.data
L0:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
sub r13, r13, #20
mov r0, #16
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-36]
mov r0, #8
bl malloc(PLT)
mov r7, r0
str r7, [r11, #-32]
ldr r5, =#909
mov r4, r5
str r4, [r11, #-44]
ldr r6, [r11, #-32]
ldr r4, [r11, #-44]
mov r5, #-1
mul r4, r5
mov r7, r4
str r7, [r6, #0]
ldr r8, [r11, #-32]
ldr r8, [r8, #0]
mov r6, r8
str r6, [r11, #-40]
ldr r0, =L0
ldr r1, [r11, #-40]
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
