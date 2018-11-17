.data
prgm.S1:
.asciz "Hello"

prgm.S0:
.asciz "%i\n"

prgm.S2:
.asciz "%s\n"

.text
.global main

main:
@ Function TestCLass89B.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #-24]
push {a1}
bl .F0
add sp, sp, #4
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #0
b mainExit
mainExit:
@ End of function TestCLass89B.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F0:
@ Function SecondDUVJ130dsdk.id8292AAAdsd
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, =prgm.S1
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
ldr a1, [fp, #4]
push {a1}
mov a1, #4
push {a1}
bl .F1
add sp, sp, #8
.F0Exit:
@ End of function SecondDUVJ130dsdk.id8292AAAdsd
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function SecondDUVJ130dsdk.duplicate
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
push {a1}
mov a1, #9
pop {a2}
mul a1, a2, a1
b .F1Exit
.F1Exit:
@ End of function SecondDUVJ130dsdk.duplicate
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
