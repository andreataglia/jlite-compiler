.data
prgm.S0:
.asciz "Please type an integer"

prgm.S3:
.asciz "%i\n"

prgm.S1:
.asciz "%s\n"

prgm.S2:
.asciz "The double of your number is:"

.text
.global main

main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
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
str a1, [fp, #-24]
ldr a1, =prgm.S2
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
ldr a1, [fp, #-24]
push {a1}
mov a1, #2
pop {a2}
mul a1, a2, a1
mov a2, a1
ldr a1, =prgm.S3
bl printf(PLT)
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
