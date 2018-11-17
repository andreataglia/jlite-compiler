.data
prgm.S0:
.asciz "true"

prgm.S2:
.asciz "false"

prgm.S1:
.asciz "%s\n"

.text
.global main

.F0:
@ Function BoolOps.func
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #6
push {a1}
ldr a1, [fp, #4]
pop {a2}
add a1, a2, a1
b .F0Exit
.F0Exit:
@ End of function BoolOps.func
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function BoolOpsMain.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#20
mov a1, #4
str a1, [fp, #-28]
mov a1, #5
str a1, [fp, #-36]
mov a1, #1
str a1, [fp, #-40]
mov a1, #0
str a1, [fp, #-24]
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #-40]
pop {a2}
orr a1, a1, a2
push {a1}
ldr a1, [fp, #-24]
pop {a2}
orr a1, a1, a2
push {a1}
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #-40]
pop {a2}
and a1, a1, a2
pop {a2}
orr a1, a1, a2
str a1, [fp, #-32]
ldr a1, [fp, #-32]
push {a1}
ldr a1, [fp, #-36]
push {a1}
ldr a1, [fp, #-28]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
pop {a2}
and a1, a1, a2
str a1, [fp, #-40]
ldr a1, [fp, #-32]
push {a1}
ldr a1, [fp, #-36]
push {a1}
ldr a1, [fp, #-28]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
pop {a2}
and a1, a1, a2
cmp a1, #0
beq prgm.2
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
b prgm.1
prgm.2:
ldr a1, =prgm.S2
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
prgm.1:
ldr a1, [fp, #-36]
push {a1}
ldr a1, [fp, #-28]
pop {a2}
cmp a1, a2
mov a1, #0
movgt a1, #1
push {a1}
ldr a1, [fp, #-32]
pop {a2}
orr a1, a1, a2
cmp a1, #0
beq prgm.4
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
b prgm.3
prgm.4:
ldr a1, =prgm.S2
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
prgm.3:
mainExit:
@ End of function BoolOpsMain.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
