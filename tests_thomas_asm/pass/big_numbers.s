.data
prgm.S0:
.asciz "%i\n"

prgm.I2:
.word 256

prgm.I3:
.word 6367393

prgm.I7:
.word 3745209

prgm.I0:
.word 8083

prgm.I6:
.word 275

prgm.I1:
.word 20292

prgm.I4:
.word 12918

prgm.I5:
.word 37983

.text
.global main

main:
@ Function BigNumbers790Test.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#8
ldr a1, =prgm.I0
ldr a1, [a1]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
str a1, [fp, #-28]
ldr a1, =prgm.I1
ldr a1, [a1]
str a1, [fp, #-24]
mov a1, #255
mov a2, a1
mov a1, #-1
mul a1, a2, a1
push {a1}
mov a1, #255
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, =prgm.I2
ldr a1, [a1]
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, =prgm.I2
ldr a1, [a1]
pop {a2}
sub a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
ldr a1, [fp, #-24]
push {a1}
ldr a1, [fp, #-28]
pop {a2}
mul a1, a2, a1
push {a1}
ldr a1, =prgm.I3
ldr a1, [a1]
push {a1}
ldr a1, =prgm.I4
ldr a1, [a1]
pop {a2}
mov a3, a1
push {a1}
push {a1}
mov a1, sp
bl div(PLT)
pop {a1}
pop {a2}
pop {a2}
add a1, a2, a1
push {a1}
ldr a1, =prgm.I5
ldr a1, [a1]
push {a1}
ldr a1, =prgm.I6
ldr a1, [a1]
mov a2, a1
mov a1, #-1
mul a1, a2, a1
pop {a2}
mul a1, a2, a1
pop {a2}
sub a1, a2, a1
push {a1}
ldr a1, =prgm.I7
ldr a1, [a1]
pop {a2}
add a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function BigNumbers790Test.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
