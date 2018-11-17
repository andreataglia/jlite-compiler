.data
L0:
.asciz "Square of d smaller than sum of squares\n\n"
L1:
.asciz "%i\n"
L2:
.asciz "%i\n"
L3:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #44
mov r6, #1
mov r5, r6
str r5, [r11, #-36]
mov r8, #2
mov r7, r8
str r7, [r11, #-40]
mov r5, #3
mov r4, r5
str r4, [r11, #-44]
mov r7, #4
mov r6, r7
str r6, [r11, #-48]
mov r4, #0
mov r8, r4
str r8, [r11, #-32]
mov r0, #8
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-52]
ldr r7, [r11, #-52]
mov r4, #17
mov r8, r4
str r8, [r7, #4]
mov r0, #8
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-64]
ldr r4, [r11, #-52]
ldr r4, [r4, #4]
mov r7, r4
str r7, [r11, #-68]
ldr r7, [r11, #-64]
mov r0, r7
ldr r8, [r11, #-36]
mov r1, r8
ldr r4, [r11, #-40]
mov r2, r4
mov r5, #1
mov r3, r5
mov r6, #2
push {r6}
mov r7, #3
push {r7}
ldr r8, [r11, #-68]
push {r8}
mov r4, #5
push {r4}
mov r5, #6
push {r5}
bl addSquares(PLT)
mov r5, r0
str r5, [r11, #-56]
ldr r8, [r11, #-64]
mov r0, r8
ldr r4, [r11, #-48]
mov r1, r4
bl square(PLT)
mov r6, r0
str r6, [r11, #-60]
ldr r7, [r11, #-52]
ldr r7, [r7, #4]
mov r5, r7
str r5, [r11, #-48]
mov r8, #0
ldr r4, [r11, #-56]
ldr r5, [r11, #-60]
cmp r4, r5
movne r8, #1
cmp r8, #1
beq .1
ldr r0, =L0
bl printf(PLT)
b .2

.1:
ldr r0, =L1
ldr r1, [r11, #-56]
bl printf(PLT)

.2:
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

square:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
str r1, [r11, #-32]
sub r13, r13, #12
ldr r8, [r11, #-32]
ldr r4, [r11, #-32]
mul r8, r4
mov r6, r8
str r6, [r11, #-36]
ldr r5, [r11, #-36]
mov r0, r5
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

add:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
str r1, [r11, #-32]
str r2, [r11, #-36]
sub r13, r13, #16
ldr r8, [r11, #-32]
ldr r4, [r11, #-36]
add r7, r8, r4
mov r6, r7
str r6, [r11, #-40]
ldr r5, [r11, #-40]
mov r0, r5
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

addSquares:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
str r1, [r11, #-32]
str r2, [r11, #-36]
str r3, [r11, #-40]
ldr r4, [r11, #20]
str r4, [r11, #-44]
ldr r4, [r11, #16]
str r4, [r11, #-48]
ldr r4, [r11, #12]
str r4, [r11, #-52]
ldr r4, [r11, #8]
str r4, [r11, #-56]
ldr r4, [r11, #4]
str r4, [r11, #-60]
sub r13, r13, #36
ldr r0, =L2
ldr r1, [r11, #-48]
bl printf(PLT)
ldr r0, =L3
ldr r1, [r11, #-52]
bl printf(PLT)
ldr r6, [r11, #-56]
mov r0, r6
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
