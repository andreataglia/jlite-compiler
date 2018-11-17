.data
L0:
.asciz "%i\n"
L1:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #44
ldr r6, =#8083
mov r5, r6
str r5, [r11, #-40]
ldr r4, [r11, #-40]
mov r5, #-1
mul r4, r5
mov r7, r4
str r7, [r11, #-32]
ldr r7, =#20292
mov r6, r7
str r6, [r11, #-36]
mov r4, #255
mov r8, r4
str r8, [r11, #-44]
ldr r7, [r11, #-44]
mov r8, #-1
mul r7, r8
mov r5, r7
str r5, [r11, #-48]
ldr r6, [r11, #-48]
mov r7, #255
add r5, r6, r7
mov r4, r5
str r4, [r11, #-52]
ldr r5, [r11, #-52]
ldr r6, =#256
add r4, r5, r6
mov r8, r4
str r8, [r11, #-56]
ldr r4, [r11, #-56]
ldr r5, =#256
sub r8, r4, r5
mov r7, r8
str r7, [r11, #-60]
ldr r0, =L0
ldr r6, [r11, #-60]
mov r1, r6
bl printf(PLT)
mov r4, #5
mov r5, #3
add r8, r4, r5
mov r7, r8
str r7, [r11, #-64]
ldr r8, [r11, #-64]
mov r4, #2
mul r8, r4
mov r6, r8
str r6, [r11, #-68]
ldr r0, =L1
ldr r5, [r11, #-68]
mov r1, r5
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
