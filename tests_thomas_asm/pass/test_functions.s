.data
prgm.S0:
.asciz "Square of d larger than sum of squares"

prgm.S5:
.asciz "\n"

prgm.S3:
.asciz "%i\n"

prgm.S4:
.asciz " equal to:"

prgm.S1:
.asciz "%s\n"

prgm.S2:
.asciz "\nFactorial of:"

.text
.global main

.F0:
@ Function Compute.add
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #8]
push {a1}
ldr a1, [fp, #4]
pop {a2}
add a1, a2, a1
b .F0Exit
.F0Exit:
@ End of function Compute.add
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Compute.square
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
@ End of function Compute.square
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F2:
@ Function Compute.computeFac
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #1
push {a1}
ldr a1, [fp, #4]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
cmp a1, #0
beq prgm.2
mov a1, #1
str a1, [fp, #-24]
ldr a1, [fp, #-24]
b .F2Exit
b prgm.1
prgm.2:
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #8]
push {a1}
ldr a1, [fp, #4]
push {a1}
mov a1, #1
pop {a2}
sub a1, a2, a1
push {a1}
bl .F2
add sp, sp, #8
pop {a2}
mul a1, a2, a1
str a1, [fp, #-24]
ldr a1, [fp, #-24]
b .F2Exit
prgm.1:
.F2Exit:
@ End of function Compute.computeFac
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F3:
@ Function Compute.addSquares
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #12]
ldr a1, [a1, #4]
cmp a1, #0
beq prgm.4
ldr a1, [fp, #12]
ldr a1, [a1, #0]
b .F3Exit
b prgm.3
prgm.4:
mov a1, #1
push {a1}
ldr a1, [fp, #12]
mov v1, a1
pop {a1}
str a1, [v1, #4]
ldr a1, [fp, #12]
push {a1}
ldr a1, [fp, #12]
push {a1}
ldr a1, [fp, #8]
push {a1}
bl .F1
add sp, sp, #8
push {a1}
ldr a1, [fp, #12]
push {a1}
ldr a1, [fp, #4]
push {a1}
bl .F1
add sp, sp, #8
push {a1}
bl .F0
add sp, sp, #12
b .F3Exit
prgm.3:
.F3Exit:
@ End of function Compute.addSquares
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#28
mov a1, #1
str a1, [fp, #-24]
mov a1, #2
str a1, [fp, #-32]
mov a1, #3
str a1, [fp, #-40]
mov a1, #4
str a1, [fp, #-36]
mov a1, #8
bl malloc(PLT)
str a1, [fp, #-28]
ldr a1, [fp, #-28]
push {a1}
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #-32]
push {a1}
bl .F3
add sp, sp, #12
push {a1}
ldr a1, [fp, #-28]
push {a1}
ldr a1, [fp, #-40]
push {a1}
bl .F1
add sp, sp, #8
pop {a2}
add a1, a2, a1
str a1, [fp, #-44]
ldr a1, [fp, #-28]
push {a1}
ldr a1, [fp, #-36]
push {a1}
bl .F1
add sp, sp, #8
str a1, [fp, #-48]
ldr a1, [fp, #-44]
push {a1}
ldr a1, [fp, #-48]
pop {a2}
cmp a1, a2
mov a1, #0
movgt a1, #1
cmp a1, #0
beq prgm.6
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
b prgm.5
prgm.6:
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
prgm.5:
ldr a1, [fp, #-28]
push {a1}
mov a1, #4
push {a1}
bl .F2
add sp, sp, #8
str a1, [fp, #-44]
ldr a1, =prgm.S2
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mov a1, #4
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, =prgm.S4
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
ldr a1, [fp, #-44]
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, =prgm.S5
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
