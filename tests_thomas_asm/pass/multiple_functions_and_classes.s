.data
prgm.S0:
.asciz "%i\n"

prgm.S1:
.asciz "Done !"

prgm.S2:
.asciz "%s\n"

.text
.global main

.F0:
@ Function Me.increment
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, [fp, #8]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #8]
ldr a1, [a1, #0]
push {a1}
ldr a1, [fp, #4]
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, [fp, #8]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #8]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #42
b .F0Exit
.F0Exit:
@ End of function Me.increment
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F1:
@ Function Me.duplicate
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #12
bl malloc(PLT)
str a1, [fp, #-24]
ldr a1, [fp, #4]
ldr a1, [a1, #0]
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #4]
ldr a1, [a1, #4]
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #4]
ldr a1, [fp, #4]
ldr a1, [a1, #8]
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #8]
ldr a1, [fp, #-24]
b .F1Exit
.F1Exit:
@ End of function Me.duplicate
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F2:
@ Function Me.write
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, [fp, #4]
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, [fp, #8]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #8]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #1
push {a1}
ldr a1, [fp, #8]
mov v1, a1
pop {a1}
str a1, [v1, #4]
ldr a1, [fp, #8]
push {a1}
bl .F1
add sp, sp, #4
str a1, [fp, #-24]
ldr a1, [fp, #-24]
push {a1}
mov a1, #89
push {a1}
bl .F0
add sp, sp, #8
ldr a1, [fp, #8]
push {a1}
bl .F1
add sp, sp, #4
push {a1}
mov a1, #7
push {a1}
bl .F0
add sp, sp, #8
ldr a1, [fp, #8]
push {a1}
mov a1, #4
push {a1}
bl .F0
add sp, sp, #8
ldr a1, [fp, #8]
push {a1}
mov a1, #12
push {a1}
bl .F0
add sp, sp, #8
ldr a1, [fp, #8]
ldr a1, [a1, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #0
push {a1}
ldr a1, [fp, #8]
mov v1, a1
pop {a1}
str a1, [v1, #4]
ldr a1, [fp, #8]
ldr a1, [a1, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #-24]
ldr a1, [a1, #4]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #80
mov a2, a1
mov a1, #-1
mul a1, a2, a1
push {a1}
ldr a1, [fp, #8]
ldr a1, [a1, #0]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
push {a1}
mov a1, #0
push {a1}
ldr a1, [fp, #8]
ldr a1, [a1, #0]
pop {a2}
cmp a1, a2
mov a1, #0
movge a1, #1
pop {a2}
orr a1, a1, a2
cmp a1, #0
beq prgm.2
ldr a1, [fp, #4]
push {a1}
ldr a1, [fp, #8]
ldr a1, [a1, #0]
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #89
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
sub a1, a2, a1
b .F2Exit
b prgm.1
prgm.2:
mov a1, #80
b .F2Exit
prgm.1:
.F2Exit:
@ End of function Me.write
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#8
mov a1, #12
bl malloc(PLT)
str a1, [fp, #-24]
mov a1, #6
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #0]
mov a1, #8
push {a1}
mov a1, #9
pop {a2}
add a1, a2, a1
str a1, [fp, #-28]
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #-28]
push {a1}
bl .F0
add sp, sp, #8
ldr a1, [fp, #-24]
ldr a1, [a1, #0]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #-24]
push {a1}
mov a1, #78
push {a1}
bl .F2
add sp, sp, #8
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, =prgm.S1
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
