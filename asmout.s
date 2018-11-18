.data
L0:
.asciz "cool!\n"
L1:
.asciz "i'm \"sorry\"\n"
L2:
.asciz "Siciliana\n"
L3:
.asciz "%i\n"

.text
.global main

main:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #12
mov r0, #8
bl malloc(PLT)
mov r5, r0
str r5, [r11, #-32]
mov r0, #0
bl malloc(PLT)
mov r7, r0
str r7, [r11, #-36]
ldr r6, [r11, #-36]
mov r0, r6
bl Shop_randomPizza(PLT)
mov r4, r0
str r4, [r11, #-32]
ldr r8, [r11, #-32]
mov r0, r8
bl Pizza_print(PLT)
mov r0, #0
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Shop_orderPizza:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
str r1, [r11, #-32]
str r2, [r11, #-36]
sub r13, r13, #32
ldr r6, [r11, #-32]
mov r0, r6
bl Pizza_isPizzaAvailable(PLT)
mov r4, r0
str r4, [r11, #-40]
ldr r4, [r11, #-40]
mov r5, #1
sub r4, r5, r4
mov r7, r4
str r7, [r11, #-44]
ldr r6, [r11, #-44]
cmp r6, #1
beq .1
ldr r0, =L0
bl printf(PLT)
mov r8, #1
mov r7, r8
str r7, [r11, #-48]
ldr r4, [r11, #-48]
mov r0, r4
b .2

.1:
ldr r0, =L1
bl printf(PLT)
mov r6, #0
mov r5, r6
str r5, [r11, #-52]
ldr r7, [r11, #-52]
mov r0, r7

.2:
mov r4, #1
mov r8, r4
str r8, [r11, #-56]
ldr r5, [r11, #-56]
mov r0, r5
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Shop_randomPizza:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #24
mov r0, #8
bl malloc(PLT)
mov r6, r0
str r6, [r11, #-36]
ldr r5, [r11, #-36]
mov r0, r5
bl Pizza_pizza(PLT)
mov r8, r0
str r8, [r11, #-32]
mov r0, #16
bl malloc(PLT)
mov r6, r0
ldr r8, [r11, #-32]
str r6, [r8, #0]
mov r5, #0
mov r4, r5
ldr r6, [r11, #-32]
str r4, [r6, #4]
ldr r4, [r11, #-32]
ldr r4, [r4, #0]
mov r7, r4
str r7, [r11, #-40]
mov r6, #11
mov r5, r6
ldr r7, [r11, #-40]
str r5, [r7, #0]
ldr r5, [r11, #-32]
ldr r5, [r5, #0]
mov r8, r5
str r8, [r11, #-44]
ldr r7, =L2
mov r6, r7
ldr r8, [r11, #-44]
str r6, [r8, #8]
ldr r6, [r11, #-32]
ldr r6, [r6, #0]
mov r4, r6
str r4, [r11, #-48]
mov r8, #2
mov r7, r8
ldr r4, [r11, #-48]
str r7, [r4, #4]
ldr r5, [r11, #-32]
mov r0, r5
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Pizza_pizza:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #8
mov r0, #8
bl malloc(PLT)
mov r6, r0
str r6, [r11, #-32]
ldr r8, [r11, #-32]
mov r0, r8
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Pizza_print:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #8
ldr r6, [r11, #-28]
ldr r6, [r6, #0]
ldr r6, [r6, #8]
mov r4, r6
str r4, [r11, #-32]
ldr r7, [r11, #-32]
mov r0, r7
bl printf(PLT)
ldr r4, [r11, #-28]
ldr r4, [r4, #0]
mov r0, r4
bl PizzaType_print(PLT)
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

Pizza_isPizzaAvailable:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #4
ldr r5, [r11, #-28]
ldr r5, [r5, #4]
mov r0, r5
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}

PizzaType_print:
push {r11, r14, r4, r5, r6, r7, r8}
add r11, r13, #24
str r0, [r11, #-28]
sub r13, r13, #8
ldr r7, [r11, #-32]
mov r6, r7
str r6, [r11, #-32]
ldr r0, =L3
ldr r8, [r11, #-32]
mov r1, r8
bl printf(PLT)
sub r13, r11, #24
pop {r11, r15, r4, r5, r6, r7, r8}
