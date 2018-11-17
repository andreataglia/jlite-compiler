.data
prgm.S1:
.asciz "%s\n"

prgm.S0:
.asciz "You typed a true value"

prgm.S2:
.asciz "You typed a false value"

.text
.global main

main:
@ Function Main.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#8
mov a1, #0
str a1, [fp, #-24]
prgm.1:
mov a1, #6
push {a1}
ldr a1, [fp, #-24]
pop {a2}
cmp a1, a2
mov a1, #0
movlt a1, #1
cmp a1, #0
beq prgm.2
ldr a1, [fp, #-24]
push {a1}
mov a1, #1
pop {a2}
add a1, a2, a1
str a1, [fp, #-24]
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
beq prgm.4
ldr a1, =prgm.S0
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
b prgm.3
prgm.4:
ldr a1, =prgm.S2
mov a2, a1
ldr a1, =prgm.S1
bl printf(PLT)
prgm.3:
b prgm.1
prgm.2:
mainExit:
@ End of function Main.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
