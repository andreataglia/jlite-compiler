.data
prgm.S1:
.asciz "Hello, this is function_call_exp..."

prgm.S0:
.asciz "%s\n"

.text
.global main

.F0:
@ Function Greet.sayhi
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #4]
cmp a1, #0
beq prgm.2
ldr a1, [fp, #16]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
b prgm.1
prgm.2:
ldr a1, [fp, #12]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
prgm.1:
.F0Exit:
@ End of function Greet.sayhi
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #6
mov a2, a1
mov a1, #-1
mul a1, a2, a1
str a1, [fp, #8]
ldr a1, [fp, #-24]
push {a1}
ldr a1, =prgm.S1
push {a1}
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #8]
push {a1}
mov a1, #32
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #89
push {a1}
ldr a1, [fp, #8]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
push {a1}
bl .F0
add sp, sp, #20
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
