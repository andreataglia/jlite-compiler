.data

L1:
.asciz "\n>>>>first asm yes!!!\n\n"

.text
.global main

main:
  push {fp, lr, v1, v2, v3 ,v4, v5}
  add fp, sp, #24
  sub sp, fp, #4 /*space for local vars nvars*4 */

  ldr a1,=L1
  bl printf(PLT)

  sub sp, fp, #24
  pop {fp, pc, v1, v2, v3 ,v4, v5}