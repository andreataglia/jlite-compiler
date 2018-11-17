.data
prgm.S0:
.asciz "%i\n"

prgm.I2:
.word 333

prgm.I0:
.word 383

prgm.I1:
.word 367

.text
.global main

main:
@ Function AdditionsSubstractionsOrderTest.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #1
push {a1}
mov a1, #2
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #3
pop {a2}
sub a1, a2, a1
push {a1}
mov a1, #7
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #6
pop {a2}
sub a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #89
push {a1}
mov a1, #2
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
push {a1}
mov a1, #6
pop {a2}
mul a1, a2, a1
push {a1}
mov a1, #8
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, =prgm.I0
ldr a1, [a1]
push {a1}
mov a1, #7
push {a1}
mov a1, #2
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
push {a1}
mov a1, #3
pop {a2}
mul a1, a2, a1
pop {a2}
sub a1, a2, a1
push {a1}
mov a1, #9
push {a1}
ldr a1, =prgm.I1
ldr a1, [a1]
pop {a2}
mul a1, a2, a1
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, =prgm.I2
ldr a1, [a1]
push {a1}
mov a1, #9
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
pop {a2}
sub a1, a2, a1
push {a1}
mov a1, #112
pop {a2}
add a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function AdditionsSubstractionsOrderTest.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
