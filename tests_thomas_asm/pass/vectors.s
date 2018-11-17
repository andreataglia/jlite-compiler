.data
prgm.S3:
.asciz "%i\n"

prgm.S4:
.asciz "Hey!"

prgm.S0:
.asciz "Point instance"

prgm.S2:
.asciz "%s\n"

prgm.S1:
.asciz "Hello !"

prgm.I1:
.word 210000

prgm.I0:
.word 999

.text
.global main

.F0:
@ Function Point.toString
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, =prgm.S0
b .F0Exit
.F0Exit:
@ End of function Point.toString
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#20
mov a1, #12
bl malloc(PLT)
str a1, [fp, #-36]
mov a1, #8
bl malloc(PLT)
push {a1}
ldr a1, [fp, #-36]
mov v1, a1
pop {a1}
str a1, [v1, #0]
mov a1, #8
bl malloc(PLT)
str a1, [fp, #-32]
ldr a1, =prgm.I0
ldr a1, [a1]
push {a1}
ldr a1, [fp, #-36]
ldr a1, [a1, #0]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-36]
push {a1}
bl .F2
add sp, sp, #4
push {a1}
ldr a1, [fp, #-36]
mov v1, a1
pop {a1}
str a1, [v1, #8]
mov a1, #90
push {a1}
ldr a1, [fp, #-36]
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #0]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-32]
push {a1}
ldr a1, [fp, #-36]
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
push {a1}
bl .F2
add sp, sp, #4
mov v1, a1
pop {a1}
str a1, [v1, #4]
mov a1, #89
push {a1}
ldr a1, [fp, #-36]
ldr a1, [a1, #0]
mov v1, a1
pop {a1}
str a1, [v1, #4]
ldr a1, =prgm.S1
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
ldr a1, [fp, #-36]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
push {a1}
bl .F2
add sp, sp, #4
push {a1}
bl .F2
add sp, sp, #4
ldr a1, [a1, #8]
ldr a1, [a1, #8]
ldr a1, [a1, #8]
push {a1}
bl .F2
add sp, sp, #4
push {a1}
bl .F2
add sp, sp, #4
ldr a1, [a1, #0]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, =prgm.S4
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
mov a1, #43
push {a1}
mov a1, #78
push {a1}
mov a1, #9
pop {a2}
mul a1, a2, a1
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #7
pop {a2}
sub a1, a2, a1
push {a1}
ldr a1, [fp, #-32]
mov v1, a1
pop {a1}
str a1, [v1, #0]
mov a1, #256
bl malloc(PLT)
ldr a3, =stdin
ldr a3, [a3]
mov a2, #256
bl fgets(PLT)
push {a1}
bl atoi(PLT)
mov a3, a1
pop {a1}
push {a3}
bl free(PLT)
pop {a1}
str a1, [fp, #-40]
ldr a1, [fp, #-40]
push {a1}
ldr a1, [fp, #-32]
mov v1, a1
pop {a1}
str a1, [v1, #4]
ldr a1, [fp, #-36]
push {a1}
bl .F4
add sp, sp, #4
push {a1}
mov a1, #7
pop {a2}
mul a1, a2, a1
push {a1}
mov a1, #45
push {a1}
mov a1, #9
pop {a2}
mul a1, a2, a1
pop {a2}
add a1, a2, a1
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
mov a1, #0
b mainExit
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Vector.square
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #4]
pop {a2}
mul a1, a2, a1
b .F1Exit
.F1Exit:
@ End of function Vector.square
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F2:
@ Function Vector.getPoint
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
b .F2Exit
.F2Exit:
@ End of function Vector.getPoint
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F3:
@ Function Vector.sqrt
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #0
str a1, [fp, #-24]
prgm.1:
ldr a1, [fp, #4]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
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
push {a1}
mov a1, #1
pop {a2}
sub a1, a2, a1
b .F3Exit
.F3Exit:
@ End of function Vector.sqrt
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F4:
@ Function Vector.norm
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #4]
ldr a1, [a1, #0]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, [fp, #4]
ldr a1, [a1, #0]
ldr a1, [a1, #4]
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, [fp, #4]
ldr a1, [a1, #4]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, [fp, #4]
ldr a1, [a1, #4]
ldr a1, [a1, #4]
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #4]
ldr a1, [a1, #0]
ldr a1, [a1, #0]
push {a1}
ldr a1, [fp, #4]
ldr a1, [a1, #4]
ldr a1, [a1, #0]
pop {a2}
sub a1, a2, a1
push {a1}
bl .F1
add sp, sp, #8
push {a1}
ldr a1, [fp, #4]
ldr a1, [a1, #4]
ldr a1, [a1, #4]
push {a1}
ldr a1, [fp, #4]
ldr a1, [a1, #0]
ldr a1, [a1, #4]
pop {a2}
sub a1, a2, a1
push {a1}
ldr a1, [fp, #4]
ldr a1, [a1, #4]
ldr a1, [a1, #4]
push {a1}
ldr a1, [fp, #4]
ldr a1, [a1, #0]
ldr a1, [a1, #4]
pop {a2}
sub a1, a2, a1
pop {a2}
mul a1, a2, a1
pop {a2}
add a1, a2, a1
str a1, [fp, #-24]
ldr a1, [fp, #-24]
push {a1}
mov a1, #5
pop {a2}
mul a1, a2, a1
push {a1}
ldr a1, =prgm.I1
ldr a1, [a1]
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #-24]
push {a1}
bl .F3
add sp, sp, #8
str a1, [fp, #-24]
ldr a1, [fp, #-24]
push {a1}
mov a1, #67
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #67
pop {a2}
sub a1, a2, a1
b .F4Exit
.F4Exit:
@ End of function Vector.norm
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
