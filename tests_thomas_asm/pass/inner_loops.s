.data
prgm.S0:
.asciz "%i\n"

prgm.I0:
.word 2000

.text
.global main

main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #62
str a1, [fp, #4]
ldr a1, [fp, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #0
push {a1}
ldr a1, [fp, #4]
pop {a2}
cmp a1, a2
mov a1, #0
movgt a1, #1
cmp a1, #0
beq prgm.2
ldr a1, [fp, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #4]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
str a1, [fp, #4]
mov a1, #0
push {a1}
ldr a1, [fp, #4]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
cmp a1, #0
beq prgm.4
ldr a1, [fp, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
prgm.5:
mov a1, #1
cmp a1, #0
beq prgm.6
ldr a1, [fp, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, =prgm.I0
ldr a1, [a1]
push {a1}
ldr a1, [fp, #4]
pop {a2}
cmp a1, a2
mov a1, #0
movgt a1, #1
cmp a1, #0
beq prgm.8
mov a1, #0
b mainExit
b prgm.7
prgm.8:
ldr a1, [fp, #4]
push {a1}
mov a1, #2
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mul a1, a2, a1
str a1, [fp, #4]
prgm.7:
b prgm.5
prgm.6:
b prgm.3
prgm.4:
mov a1, #0
b mainExit
prgm.3:
b prgm.1
prgm.2:
mov a1, #0
b mainExit
prgm.1:
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
