.data
L0:
.asciz "false\n"
L1:
.asciz "true\n"
L2:
.asciz "false\n"
L3:
.asciz "true\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #56
mov r6, #4
mov r5, r6
str r5, [r11, #-32]
mov r8, #5
mov r7, r8
str r7, [r11, #-36]
mov r5, #1
mov r4, r5
str r4, [r11, #-40]
mov r7, #0
mov r6, r7
str r6, [r11, #-44]
ldr r5, [r11, #-40]
ldr r6, [r11, #-44]
and r5, r5, r6
mov r8, r5
str r8, [r11, #-52]
ldr r4, [r11, #-52]
ldr r5, [r11, #-44]
orr r4, r4, r5
mov r7, r4
str r7, [r11, #-56]
ldr r8, [r11, #-40]
ldr r4, [r11, #-44]
orr r8, r8, r4
mov r6, r8
str r6, [r11, #-60]
ldr r7, [r11, #-56]
ldr r8, [r11, #-60]
orr r7, r7, r8
mov r5, r7
str r5, [r11, #-48]
mov r5, #0
ldr r6, [r11, #-32]
ldr r7, [r11, #-36]
cmp r6, r7
movlt r5, #1
mov r4, r5
str r4, [r11, #-64]
ldr r5, [r11, #-64]
ldr r6, [r11, #-48]
and r5, r5, r6
mov r8, r5
str r8, [r11, #-40]
mov r8, #0
ldr r4, [r11, #-32]
ldr r5, [r11, #-36]
cmp r4, r5
movlt r8, #1
mov r7, r8
str r7, [r11, #-68]
ldr r8, [r11, #-68]
ldr r4, [r11, #-48]
and r8, r8, r4
mov r6, r8
str r6, [r11, #-72]
ldr r5, [r11, #-72]
cmp r5, #1
beq .1
ldr r0, =L0
bl printf(PLT)
b .2

.1:
ldr r0, =L1
bl printf(PLT)

.2:
mov r7, #0
ldr r8, [r11, #-32]
ldr r4, [r11, #-36]
cmp r8, r4
movgt r7, #1
mov r6, r7
str r6, [r11, #-76]
ldr r7, [r11, #-48]
ldr r8, [r11, #-76]
orr r7, r7, r8
mov r5, r7
str r5, [r11, #-80]
ldr r4, [r11, #-80]
cmp r4, #1
beq .3
ldr r0, =L2
bl printf(PLT)
b .4

.3:
ldr r0, =L3
bl printf(PLT)

.4:
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

BoolOps_func:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
str r1, [r11, #-32]
sub r13, r13, #12
mov r7, #6
ldr r8, [r11, #-32]
add r6, r7, r8
mov r5, r6
str r5, [r11, #-36]
ldr r4, [r11, #-36]
mov r0, r4
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
