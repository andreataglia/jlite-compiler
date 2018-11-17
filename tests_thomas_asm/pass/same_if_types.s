.data
prgm.S1:
.asciz "%s\n"

prgm.S0:
.asciz "Hey! This code is working!"

.text
.global main

.F0:
@ Function Test.hi
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #1
cmp a1, #0
beq prgm.2
ldr a1, [fp, #4]
push {a1}
bl .F1
add sp, sp, #4
b prgm.1
prgm.2:
ldr a1, [fp, #-24]
push {a1}
mov a1, #2
push {a1}
bl main
add sp, sp, #8
ldr a1, [fp, #4]
push {a1}
bl .F0
add sp, sp, #4
prgm.1:
.F0Exit:
@ End of function Test.hi
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Test.hey
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mov a1, #6
b .F1Exit
.F1Exit:
@ End of function Test.hey
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #-24]
push {a1}
bl .F0
add sp, sp, #4
mov a1, #0
b mainExit
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
