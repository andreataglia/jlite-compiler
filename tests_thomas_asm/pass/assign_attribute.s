.data
prgm.S0:
.asciz "%i\n"

.text
.global main

main:
@ Function Test.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #4
bl malloc(PLT)
str a1, [fp, #-24]
mov a1, #56
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
ldr a1, [a1, #0]
push {a1}
mov a1, #1
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mul a1, a2, a1
push {a1}
ldr a1, [fp, #-24]
mov v1, a1
pop {a1}
str a1, [v1, #0]
ldr a1, [fp, #-24]
ldr a1, [a1, #0]
push {a1}
mov a1, #8
pop {a2}
add a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function Test.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
