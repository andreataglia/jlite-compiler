.data
prgm.S0:
.asciz "%i\n"

.text
.global main

main:
@ Function MainClass.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #6
str a1, [fp, #4]
ldr a1, [fp, #4]
push {a1}
mov a1, #0
pop {a2}
cmp a1, a2
mov a1, #0
movgt a1, #1
cmp a1, #0
beq prgm.2
mov a1, #0
b mainExit
b prgm.1
prgm.2:
ldr a1, [fp, #4]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
str a1, [fp, #4]
prgm.1:
ldr a1, [fp, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function MainClass.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
