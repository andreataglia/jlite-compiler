.data
prgm.S0:
.asciz "%i\n"

.text
.global main

main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
mov a1, #4
str a1, [fp, #4]
mov a1, #2
push {a1}
ldr a1, [fp, #4]
pop {a2}
cmp a1, a2
mov a1, #0
moveq a1, #1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #2
str a1, [fp, #4]
mov a1, #2
push {a1}
ldr a1, [fp, #4]
pop {a2}
cmp a1, a2
mov a1, #0
moveq a1, #1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
