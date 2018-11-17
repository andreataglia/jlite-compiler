.data
prgm.S0:
.asciz "%i\n"

prgm.I0:
.word 900

.text
.global main

main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #10
push {a1}
mov a1, #3
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
str a1, [fp, #-24]
ldr a1, [fp, #-24]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #81
push {a1}
mov a1, #9
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #63
push {a1}
mov a1, #9
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
str a1, [fp, #-24]
ldr a1, [fp, #-24]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mov a1, #64
push {a1}
mov a1, #8
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, =prgm.I0
ldr a1, [a1]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
push {a1}
mov a1, #10
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
