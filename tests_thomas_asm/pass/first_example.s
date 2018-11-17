.data
prgm.S0:
.asciz "%i\n"

prgm.I0:
.word 340

prgm.I1:
.word 1000

.text
.global main

main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #0
str a1, [fp, #8]
mov a1, #6
mov a2, a1
mov a1, #-1
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
mov a1, #6
push {a1}
mov a1, #8
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
push {a1}
mov a1, #7
pop {a2}
add a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
prgm.1:
mov a1, #1
cmp a1, #0
beq prgm.2
ldr a1, [fp, #8]
push {a1}
ldr a1, =prgm.I0
ldr a1, [a1]
pop {a2}
add a1, a2, a1
str a1, [fp, #8]
ldr a1, [fp, #4]
str a1, [fp, #12]
ldr a1, [fp, #8]
str a1, [fp, #16]
ldr a1, =prgm.I1
ldr a1, [a1]
push {a1}
ldr a1, [fp, #8]
pop {a2}
cmp a1, a2
mov a1, #0
movgt a1, #1
cmp a1, #0
beq prgm.4
mov a1, #0
b mainExit
b prgm.3
prgm.4:
ldr a1, [fp, #8]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #16]
push {a1}
mov a1, #3
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
push {a1}
mov a1, #5
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mul a1, a2, a1
push {a1}
mov a1, #64
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #4
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
push {a1}
mov a1, #61
pop {a2}
mul a1, a2, a1
push {a1}
mov a1, #7
pop {a2}
sub a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
prgm.3:
b prgm.1
prgm.2:
mov a1, #0
b mainExit
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F0:
@ Function Dummy.dummy
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#8
ldr a1, [fp, #-24]
b .F0Exit
.F0Exit:
@ End of function Dummy.dummy
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
