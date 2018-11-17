.data
prgm.S3:
.asciz "NULL\n"

prgm.S0:
.asciz "%i\n"

prgm.S1:
.asciz "Yes"

prgm.S2:
.asciz "%s\n"

.text
.global main

main:
@ Function MainClass89.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#16
mov a1, #256
bl malloc(PLT)
ldr a3, =stdin
ldr a3, [a3]
mov a2, #256
bl fgets(PLT)
bl free(PLT)
mov a1, #0
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
str a1, [fp, #-36]
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
str a1, [fp, #-24]
ldr a1, [fp, #-36]
push {a1}
ldr a1, [fp, #-24]
pop {a2}
add a1, a2, a1
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
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
cmp a1, #0
movgt a1, #1
movle a1, #0
str a1, [fp, #-28]
ldr a1, [fp, #-28]
cmp a1, #0
beq prgm.2
ldr a1, =prgm.S1
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
b prgm.1
prgm.2:
mov a1, #0
b mainExit
prgm.1:
mov a1, #256
bl malloc(PLT)
ldr a3, =stdin
ldr a3, [a3]
mov a2, #256
bl fgets(PLT)
bl free(PLT)
mov a1, #0
mov a1, #256
bl malloc(PLT)
ldr a3, =stdin
ldr a3, [a3]
mov a2, #256
bl fgets(PLT)
push {a1}
bl strlen
mov a3, a1
pop {a1}
mov a2, #0
add a3, a1, a3
str a2, [a3, #-1]
str a1, [fp, #-32]
mov a1, #0
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
ldr a1, [fp, #-32]
mov a2, a1
ldr a1, =prgm.S2
bl printf(PLT)
ldr a1, [fp, #-36]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function MainClass89.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
