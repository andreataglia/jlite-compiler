.data
prgm.S2:
.asciz "OOPs!"

prgm.S3:
.asciz "Done"

prgm.S0:
.asciz "Running"

prgm.S1:
.asciz "%s\n"

.text
.global main

.F0:
@ Function Test.run
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
prgm.1:
mov a1, #3
push {a1}
mov a1, #1
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
pop {a2}
cmp a1, a2
mov a1, #0
moveq a1, #1
cmp a1, #0
beq prgm.2
ldr a1, =prgm.S2
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
ldr a1, [fp, #4]
push {a1}
bl .F0
add sp, sp, #4
b prgm.1
prgm.2:
.F0Exit:
@ End of function Test.run
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
ldr a1, =prgm.S3
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mov a1, #0
b mainExit
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
