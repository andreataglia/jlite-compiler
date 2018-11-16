.data
L0:
.asciz "%i\n"
L1:
.asciz "Square of d smaller than sum of squares\n\n"
L2:
.asciz "%i\n"
L3:
.asciz "there u go\n"
L4:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-24]
sub r13, r13, #40
mov r6, #1
mov r5, r6
str r5, [r11, #-32]
mov r8, #2
mov r7, r8
str r7, [r11, #-36]
mov r5, #3
mov r4, r5
str r4, [r11, #-40]
mov r7, #4
mov r6, r7
str r6, [r11, #-44]
mov r4, #0
mov r8, r4
str r8, [r11, #-28]
mov r0, #8
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-60]
ldr r4, [r11, #-60]
mov r0, r4
ldr r5, [r11, #-32]
mov r1, r5
ldr r6, [r11, #-36]
mov r2, r6
mov r7, #3
mov r3, r7
mov r8, #4
push {r8}
mov r4, #5
push {r4}
mov r5, #6
push {r5}
mov r6, #7
push {r6}
mov r7, #57
push {r7}
bl addSquares(PLT)
mov r7, r0
str r7, [r11, #-52]
ldr r5, [r11, #-60]
mov r0, r5
ldr r6, [r11, #-44]
mov r1, r6
bl square(PLT)
mov r8, r0
str r8, [r11, #-56]
mov r0, #8
bl malloc(PLT)
mov r7, r0
str r7, [r11, #-48]
ldr r4, [r11, #-48]
mov r6, #17
mov r5, r6
str r5, [r4, #4]
ldr r4, [r11, #-48]
ldr r4, [r4, #4]
mov r7, r4
str r7, [r11, #-44]
ldr r0, =L0
ldr r1, [r11, #-44]
bl printf(PLT)
mov r5, #0
ldr r6, [r11, #-52]
ldr r7, [r11, #-56]
cmp r6, r7
movne r5, #1
cmp r5, #1
beq .1
ldr r0, =L1
bl printf(PLT)
b .2

.1:
ldr r0, =L2
ldr r1, [r11, #-52]
bl printf(PLT)

.2:
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

square:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-84]
str r1, [r11, #-88]
sub r13, r13, #12
ldr r0, =L3
bl printf(PLT)
ldr r0, =L4
ldr r1, [r11, #-88]
bl printf(PLT)
ldr r5, [r11, #-88]
ldr r6, [r11, #-88]
mul r5, r6
mov r8, r5
str r8, [r11, #-92]
ldr r7, [r11, #-92]
mov r0, r7
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

add:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-96]
str r1, [r11, #-100]
str r2, [r11, #-104]
sub r13, r13, #16
ldr r5, [r11, #-100]
ldr r6, [r11, #-104]
add r4, r5, r6
mov r8, r4
str r8, [r11, #-108]
ldr r7, [r11, #-108]
mov r0, r7
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

addSquares:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-112]
str r1, [r11, #-116]
str r2, [r11, #-120]
str r3, [r11, #-124]
ldr r4, [r11, #4]
str r4, [r11, #-128]
ldr r4, [r11, #8]
str r4, [r11, #-132]
ldr r4, [r11, #12]
str r4, [r11, #-136]
ldr r4, [r11, #16]
str r4, [r11, #-140]
ldr r4, [r11, #20]
str r4, [r11, #-144]
sub r13, r13, #36
ldr r8, [r11, #-136]
mov r0, r8
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
