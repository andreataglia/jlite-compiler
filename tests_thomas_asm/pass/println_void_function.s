.data
prgm.S0:
.asciz "NULL\n"

prgm.S1:
.asciz "%i\n"

.text
.global main

main:
@ Function Hello.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #-24]
push {a1}
bl .F1
add sp, sp, #4
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function Hello.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F0:
@ Function Test.hiAgain
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #4
b .F0Exit
.F0Exit:
@ End of function Test.hiAgain
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Test.hello
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#8
ldr a1, [fp, #4]
push {a1}
bl .F0
add sp, sp, #4
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mov a1, #0
b .F1Exit
.F1Exit:
@ End of function Test.hello
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
