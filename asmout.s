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
sub r13, r13, #8
mov r0, #0
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-32]
ldr r8, [r11, #-32]
mov r0, r8
mov r4, #1
mov r1, r4
bl Ciao_foo(PLT)
ldr r6, [r11, #-32]
mov r0, r6
bl Ciao_foo2(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Ciao_foo:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
str r1, [r11, #-32]
sub r13, r13, #40
mov r4, #10
mov r5, #1
sub r8, r4, r5
mov r7, r8
str r7, [r11, #-36]
ldr r8, [r11, #-36]
mov r4, #8
sub r7, r8, r4
mov r6, r7
str r6, [r11, #-40]
ldr r5, [r11, #-40]
mov r0, r5
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Ciao_foo2:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #100
mov r7, #0
mov r8, #4
mov r4, #2
cmp r8, r4
movle r7, #1
mov r6, r7
str r6, [r11, #-32]
mov r6, #0
mov r7, #9
mov r8, #8
cmp r7, r8
movne r6, #1
mov r5, r6
str r5, [r11, #-36]
ldr r6, [r11, #-32]
ldr r7, [r11, #-36]
orr r6, r6, r7
mov r4, r6
str r4, [r11, #-40]
ldr r5, [r11, #-40]
mov r6, #0
orr r5, r5, r6
mov r8, r5
str r8, [r11, #-44]
ldr r0, =L0
ldr r7, [r11, #-44]
mov r1, r7
bl printf(PLT)
mov r4, #0
mov r5, #10
mov r6, #9
cmp r5, r6
movlt r4, #1
mov r8, r4
str r8, [r11, #-48]
mov r4, #1
ldr r5, [r11, #-48]
and r4, r4, r5
mov r7, r4
str r7, [r11, #-52]
mov r0, #0
bl malloc(PLT)
mov r6, r0
str r6, [r11, #-56]
ldr r5, [r11, #-56]
mov r0, r5
mov r6, #2
mov r1, r6
bl Ciao_foo(PLT)
mov r8, r0
str r8, [r11, #-60]
mov r8, #0
ldr r4, [r11, #-60]
mov r5, #3
cmp r4, r5
movgt r8, #1
mov r7, r8
str r7, [r11, #-64]
ldr r8, [r11, #-52]
ldr r4, [r11, #-64]
and r8, r8, r4
mov r6, r8
str r6, [r11, #-68]
ldr r0, =L1
ldr r5, [r11, #-68]
mov r1, r5
bl printf(PLT)
mov r8, #4
mov r4, #3
add r7, r8, r4
mov r6, r7
str r6, [r11, #-72]
mov r6, #2
mov r5, r6
str r5, [r11, #-76]
ldr r4, [r11, #-76]
mov r5, #-1
mul r4, r5
mov r7, r4
str r7, [r11, #-80]
ldr r8, [r11, #-72]
ldr r4, [r11, #-80]
add r7, r8, r4
mov r6, r7
str r6, [r11, #-84]
ldr r0, =L2
ldr r5, [r11, #-84]
mov r1, r5
bl printf(PLT)
mov r8, #0
mov r4, #1
and r8, r8, r4
mov r6, r8
str r6, [r11, #-88]
mov r7, #1
ldr r8, [r11, #-88]
orr r7, r7, r8
mov r5, r7
str r5, [r11, #-92]
ldr r6, [r11, #-92]
mov r7, #0
orr r6, r6, r7
mov r4, r6
str r4, [r11, #-96]
ldr r8, [r11, #-96]
mov r0, r8
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
