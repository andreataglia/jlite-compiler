.data
prgm.S1:
.asciz "%s\n"

prgm.S0:
.asciz "test\" + \"dddd"

.text
.global main

.F0:
@ Function Test.toString
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, =prgm.S0
str a1, [fp, #-24]
ldr a1, [fp, #-24]
b .F0Exit
.F0Exit:
@ End of function Test.toString
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #4]
push {a1}
bl .F0
add sp, sp, #8
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
