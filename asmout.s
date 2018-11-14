.data
L0:
.asciz "appostoooooo semu!"
L1:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
sub r13, r13, #56
mov r0, #20
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-36]
mov r0, #8
bl malloc(PLT)
mov r7, r0
str r7, [r11, #-32]
ldr r4, [r11, #-32]
mov r0, #20
bl malloc(PLT)
mov r5, r0
str r5, [r4, #4]
ldr r7, [r11, #-36]
mov r0, #8
bl malloc(PLT)
mov r8, r0
str r8, [r7, #16]
ldr r7, [r11, #-36]
ldr r7, [r7, #16]
mov r5, r7
str r5, [r11, #-48]
ldr r8, [r11, #-48]
mov r0, #20
bl malloc(PLT)
mov r4, r0
str r4, [r8, #-4]
ldr r8, [r11, #-32]
ldr r8, [r8, #4]
mov r6, r8
str r6, [r11, #-52]
ldr r4, [r11, #-52]
mov r6, #20
mov r5, r6
str r5, [r4, #-4]
ldr r4, [r11, #-36]
ldr r4, [r4, #16]
mov r7, r4
str r7, [r11, #-56]
ldr r7, [r11, #-56]
ldr r7, [r7, #-4]
mov r5, r7
str r5, [r11, #-60]
ldr r8, [r11, #-60]
mov r5, #10
mov r4, r5
str r4, [r8, #-4]
ldr r8, [r11, #-36]
ldr r8, [r8, #16]
mov r6, r8
str r6, [r11, #-64]
ldr r4, [r11, #-64]
mov r6, #99
mov r5, r6
str r5, [r4, #-4]
ldr r4, [r11, #-36]
ldr r4, [r4, #16]
mov r7, r4
str r7, [r11, #-68]
ldr r7, [r11, #-68]
ldr r7, [r7, #-4]
mov r5, r7
str r5, [r11, #-72]
ldr r8, [r11, #-72]
mov r5, r0
mov r4, r5
str r4, [r8, #-4]
ldr r8, [r11, #-36]
ldr r8, [r8, #16]
mov r6, r8
str r6, [r11, #-76]
ldr r6, [r11, #-76]
ldr r6, [r6, #-4]
mov r4, r6
str r4, [r11, #-80]
ldr r4, [r11, #-80]
mov r5, #1
sub r8, r4, r5
mov r7, r8
str r7, [r11, #-40]
ldr r0, =L1
ldr r1, [r11, #-40]
bl printf(PLT)
ldr r0, [r11, #-44]
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
