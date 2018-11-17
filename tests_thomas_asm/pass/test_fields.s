.data
prgm.S0:
.asciz "%i\n"

.text
.global main

main:
@ Function FieldAccessMain.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#16
mov a1, #4
bl malloc(PLT)
str a1, [fp, #-36]
ldr a1, [fp, #-36]
push {a1}
mov a1, #5
push {a1}
bl .F0
add sp, sp, #8
str a1, [fp, #-28]
mov a1, #7
push {a1}
ldr a1, [fp, #-36]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-36]
ldr a1, [a1, #0]
push {a1}
ldr a1, [fp, #-28]
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #2
pop {a2}
mul a1, a2, a1
str a1, [fp, #-32]
ldr a1, [fp, #-32]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function FieldAccessMain.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
.F0:
@ Function FieldAccess.func
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #6
push {a1}
ldr a1, [fp, #4]
pop {a2}
add a1, a2, a1
b .F0Exit
.F0Exit:
@ End of function FieldAccess.func
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
