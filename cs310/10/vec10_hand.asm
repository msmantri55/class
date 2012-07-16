; Taylor Fausak
; HW #10
; 6 December 2008
; vec10_hand.asm

	.ORIG	x3000
main
	LD	R6, stackstart
	ADD	R5, R6, -1	; FP points to first local variable
	ADD	R6, R6, -8	; allocate stack space for local var vector[8]

	LD	R0, length	; load length of vector to R0
	LEA	R1, vector	; load address of vector to R1

	ADD	R6, R6, -1	; push R0
	STR	R0, R6, 0

	ADD	R6, R6, -1	; push R1
	STR	R1, R6, 0

	JSR	vector_sum	; call the function

	LDR	R2, R6, 0	; pop return value
	ADD	R6, R6, 1

	ADD	R6, R6, 2	; pop arguments

	ST	R2, result	; store result in memory

HALT

vector_sum
; BEGIN my code
	LDR	R3, R1, #0	; load current vector element into R3
	ADD	R2, R2, R3	; add R3 to R2 (the result)

	ADD	R1, R1, #1	; increment the vector pointer
	ADD	R0, R0, #-1	; decrement length of vector
	BRp	vector_sum	; loop if length of vector is still positive
; END my code

RET

length
	.FILL	#8
vector
	.FILL	#1
	.FILL	#-2
	.FILL	#8
	.FILL	#4
	.FILL	#5
	.FILL	#10
	.FILL	#-7
	.FILL	#9
result
	.FILL	#0
stackstart
	.FILL	xd000

	.END
