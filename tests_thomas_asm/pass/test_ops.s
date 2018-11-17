.data
prgm.S0:
.asciz "%i\n"

.text
.global main

main:
@ Function LongAdditionMain.main
push {v1,v2,v3,v4,v5,fp,lr}
add fp,sp,#24
sub sp,sp,#4
mov a1, #1
push {a1}
mov a1, #2
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #3
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #4
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #5
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #6
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #7
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #8
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #9
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #10
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #11
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #12
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #13
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #14
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #15
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #16
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #17
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #18
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #19
pop {a2}
add a1, a2, a1
push {a1}
mov a1, #20
pop {a2}
add a1, a2, a1
str a1, [fp, #-24]
ldr a1, [fp, #-24]
mov a2, a1
ldr a1, =prgm.S0
bl printf(PLT)
mainExit:
@ End of function LongAdditionMain.main
sub sp,fp,#24
pop {v1,v2,v3,v4,v5,fp,pc}
