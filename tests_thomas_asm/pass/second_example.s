.data
prgm.S0:
.asciz "%i\n"

prgm.I0:
.word 9279420

.text
.global main

.F0:
@ Function Compute.square
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #0
str a1, [fp, #-24]
prgm.1:
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #-24]
pop {a2}
mul a1, a2, a1
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
cmp a1, #0
beq prgm.2
ldr a1, [fp, #-24]
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
str a1, [fp, #-24]
b prgm.1
prgm.2:
ldr a1, [fp, #-24]
b .F0Exit
.F0Exit:
@ End of function Compute.square
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#12
mov a1, #1
str a1, [fp, #-28]
prgm.3:
ldr a1, =prgm.I0
ldr a1, [a1]
push {a1}
ldr a1, [fp, #-28]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
push {a1}
mov a1, #1
pop {a2}
and a1, a1, a2
cmp a1, #0
beq prgm.4
ldr a1, [fp, #-28]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
push {a1}
mov a1, #2
pop {a2}
mul a1, a2, a1
str a1, [fp, #-28]
ldr a1, [fp, #-28]
str a1, [fp, #-32]
ldr a1, [fp, #-28]
push {a1}
ldr a1, [fp, #-32]
pop {a2}
cmp a1, a2
mov a1, #0
moveq a1, #1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #-32]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
b prgm.3
prgm.4:
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Dummy.dummy
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#8
ldr a1, [fp, #-28]
push {a1}
ldr a1, [fp, #-24]
pop {a2}
orr a1, a1, a2
cmp a1, #0
beq prgm.6
mov a1, #1
b .F1Exit
ldr a1, [fp, #-24]
str a1, [fp, #-24]
b prgm.5
prgm.6:
prgm.7:
ldr a1, [fp, #-24]
cmp a1, #0
beq prgm.8
ldr a1, [fp, #-28]
EOR a1, a1, #1
str a1, [fp, #-24]
b prgm.7
prgm.8:
ldr a1, [fp, #4]
push {a1}
bl .F2
add sp, sp, #4
push {a1}
ldr a1, [fp, #4]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-24]
str a1, [fp, #-24]
prgm.5:
ldr a1, [fp, #4]
push {a1}
bl .F2
add sp, sp, #4
push {a1}
mov a1, #6
push {a1}
bl .F0
add sp, sp, #8
b .F1Exit
mov a1, #3
mov a2, a1
mov a1, #-1
mul a1, a2, a1
push {a1}
mov a1, #7
push {a1}
mov a1, #2
pop {a2}
mul a1, a2, a1
pop {a2}
add a1, a2, a1
b .F1Exit
.F1Exit:
@ End of function Dummy.dummy
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F2:
@ Function Dummy.getCompute
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
ldr a1, [a1, #0]
b .F2Exit
.F2Exit:
@ End of function Dummy.getCompute
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
