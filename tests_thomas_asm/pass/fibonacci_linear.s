.data
prgm.S2:
.asciz "%i\n"

prgm.S0:
.asciz "Fibonacci number 20 is:"

prgm.S1:
.asciz "%s\n"

prgm.S3:
.asciz "Number of iterations to find it:"

.text
.global main

.F0:
@ Function Fibonacci.compute
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#16
ldr a1, [fp, #8]
ldr a1, [a1, #0]
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, [fp, #8]
mov v1, a1
pop {a1}
str a1, [v1, #0]
mov a1, #0
str a1, [fp, #-32]
mov a1, #1
str a1, [fp, #-24]
mov a1, #1
str a1, [fp, #-36]
prgm.1:
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #-36]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
cmp a1, #0
beq prgm.2
ldr a1, [fp, #8]
ldr a1, [a1, #0]
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, [fp, #8]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-32]
push {a1}
ldr a1, [fp, #-24]
pop {a2}
add a1, a2, a1
str a1, [fp, #-28]
ldr a1, [fp, #-24]
str a1, [fp, #-32]
ldr a1, [fp, #-28]
str a1, [fp, #-24]
ldr a1, [fp, #-36]
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
str a1, [fp, #-36]
b prgm.1
prgm.2:
ldr a1, [fp, #-24]
b .F0Exit
.F0Exit:
@ End of function Fibonacci.compute
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Fibonacci.init
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #4
bl malloc(PLT)
str a1, [fp, #-24]
mov a1, #0
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-24]
b .F1Exit
.F1Exit:
@ End of function Fibonacci.init
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Fibonaccci42TestClass.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #-24]
push {a1}
bl .F1
add sp, sp, #4
str a1, [fp, #-24]
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
ldr a1, [fp, #-24]
push {a1}
mov a1, #20
push {a1}
bl .F0
add sp, sp, #8
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
ldr a1, =prgm.S3
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
ldr a1, [fp, #-24]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
mainExit:
@ End of function Fibonaccci42TestClass.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
