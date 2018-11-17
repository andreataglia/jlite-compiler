.data
prgm.S3:
.asciz "This is not an error"

prgm.S1:
.asciz "%i\n"

prgm.S0:
.asciz "%s\n"

prgm.S2:
.asciz "OK"

.text
.global main

.F0:
@ Function Test.print
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
.F0Exit:
@ End of function Test.print
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Test.f
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #0
b .F1Exit
.F1Exit:
@ End of function Test.f
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F2:
@ Function Test.run
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mov a1, #52
str a1, [fp, #4]
mov a1, #52
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
.F2Exit:
@ End of function Test.run
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F3:
@ Function Test.resetErr
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, =prgm.S2
push {a1}
ldr a1, [fp, #4]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #4]
push {a1}
bl .F0
add sp, sp, #4
.F3Exit:
@ End of function Test.resetErr
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #4
bl malloc(PLT)
str a1, [fp, #-24]
ldr a1, =prgm.S3
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-24]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #-24]
push {a1}
mov a1, #42
push {a1}
bl .F2
add sp, sp, #8
ldr a1, [fp, #-24]
push {a1}
bl .F0
add sp, sp, #4
ldr a1, [fp, #-24]
push {a1}
bl .F3
add sp, sp, #4
ldr a1, [fp, #-24]
push {a1}
bl .F0
add sp, sp, #4
ldr a1, [fp, #-24]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
