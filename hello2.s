	.arch armv5t
	.fpu softvfp
	.eabi_attribute 20, 1
	.eabi_attribute 21, 1
	.eabi_attribute 23, 3
	.eabi_attribute 24, 1
	.eabi_attribute 25, 1
	.eabi_attribute 26, 2
	.eabi_attribute 30, 6
	.eabi_attribute 34, 0
	.eabi_attribute 18, 4
	.file	"hello.c"
	
	.text
	.align	2
	.global	ciao
	.syntax unified
	.arm
	.type	ciao, %function
ciao:
	@ args = 0, pretend = 0, frame = 8
	@ frame_needed = 1, uses_anonymous_args = 0
	@ link register save eliminated.
	push {fp, lr}
	add	fp, sp, #4
	str	r0, [fp, #-8]
	ldr	r2, [fp, #-8]
	ldr	r3, [fp, #-8]
	add	r3, r2, r3
	str	r3, [fp, #-8]
	ldr	r3, [fp, #-8]
	mov	r0, r3
	sub	sp, fp, #0
	@ sp needed
	pop	{fp, pc}
	.size	ciao, .-ciao
	
	.section	.rodata
	.align	2
.LC0:
	.ascii	"\012Hello, World! %d \012\012\000"
	
	.text
	.align	2
	.global	main
	.syntax unified
	.arm
	.type	main, %function
main:
	@ args = 0, pretend = 0, frame = 8
	@ frame_needed = 1, uses_anonymous_args = 0
	push {fp, lr}
	add	fp, sp, #4
	sub	sp, sp, #4
	mov	r3, #8
	str	r3, [fp, #-8]
	ldr	r0, [fp, #-8]
	bl	ciao
	str	r0, [fp, #-8]
	ldr	r1, [fp, #-8]
	ldr	r0, .L5
	bl	printf
	mov	r3, #0
	mov	r0, r3
	sub	sp, fp, #4
	@ sp needed
	pop	{fp, pc}
.L6:
	.align	2
.L5:
	.word	.LC0
	.size	main, .-main
	.section	.note.GNU-stack,"",%progbits
