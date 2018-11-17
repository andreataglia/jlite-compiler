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
mov r6, #5
mov r5, r6
str r5, [r11, #-40]
mov r0, #12
bl malloc(PLT)
mov r7, r0
str r7, [r11, #-32]
ldr r5, [r11, #-32]
mov r0, r5
bl Compute_printField(PLT)
ldr r0, =L0
ldr r1, [r11, #-40]
bl printf(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Compute_square:
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

Compute_add:
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

Compute_printField:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #8
mov r7, #4
mov r6, r7
str r6, [r11, #-32]
ldr r0, =L1
ldr r1, [r11, #-32]
bl printf(PLT)
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Mbare_printField2:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #8
ldr r5, [r11, #-28]
ldr r5, [r5, #-4]
mov r8, r5
str r8, [r11, #-32]
ldr r0, =L2
ldr r1, [r11, #-32]
bl printf(PLT)
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
