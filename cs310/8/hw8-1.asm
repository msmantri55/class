; Taylor Fausak (tdf268)
; 55515
; Assignment #8
; 13 November 2008

; hw8-1.asm

; Comments
;	I use a bitmask to repeatedly get the top bit from InValue and push
; it onto the stack. After the stack is filled, I pop off the values to
; build the new, reversed value.

	.ORIG	x3000

MAIN
	LEA		R6, USER_STACK
	LD		R0, InValue

; BEGIN my code
	LD		R3, limit		; number of bits to reverse
	ADD		R6, R6, R3		; put the stack pointer at
	ADD		R6, R6, #1		; the top of the stack
PUSH_LOOP
	AND		R1, R1, #0		; zero out r1
	LD		R2, mask
	AND		R2, R0, R2		; get top bit of r0
	BRzp		SKIP
	ADD		R1, R1, #1
SKIP
	JSR		PUSH			; push r1 onto the stack
	ADD		R0, R0, R0		; double r0
	ADD		R3, R3, #-1		; decrement counter
	BRzp		PUSH_LOOP

	AND		R1, R1, #0		; zero out r1
	LD		R3, limit		; number of bits to reverse
POP_LOOP
	ADD		R1, R1, R1		; double new value
	JSR		POP			; pop the stack
	ADD		R1, R1, R0		; add popped value
	ADD		R3, R3, #-1		; decrement counter
	BRzp	POP_LOOP

	BR		END_CODE		; halt execution

PUSH
	ADD		R6, R6, #-1
	STR		R1, R6, #0
	RET
POP
	LDR		R0, R6, #0
	ADD		R6, R6, #1
	RET
mask		.FILL	x8000	; 1000 0000 0000 0000
limit		.FILL	x000f	; 16 bits
; END my code

END_CODE
	STI		R1, ResultAddr
	HALT

; data
InValue		.FILL xdb0f
ResultAddr	.FILL x3300
USER_STACK	.BLKW x20
.END
