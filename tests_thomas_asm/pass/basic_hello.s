.data
prgm.S0:
.asciz "Hello"

prgm.S1:
.asciz "%s\n"

.text
.global main

main:
@ Function Test.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#0
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
mainExit:
@ End of function Test.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
