; Taylor Fausak
; 55515
; Assignment #6
; 23 October, 2008

; hw6-3.asm

; Summary
;	This multiplies two numbers (op1 and op2) together

; Registers
;	R0 is the first operand
;	R1 is the second operand
;	R2 is the product of R0 and R1

; Registers

.ORIG	x3000		; directive: put code at start of user memory

M_START
LD	R0, op1		; load op1
LD	R1, op2		; load op2

; BEGIN my code
AND	R2, R2, #0
MULTIPLY
ADD	R2, R2, R0
ADD	R1, R1, #-1
BRp	MULTIPLY
; END my code

M_END
ST	R2, Result 
HALT

; ********* DATA **********

op1	.FILL	x3f
op2	.FILL	x2c
Result  .FILL   x0000
.END			; directive: no more code
