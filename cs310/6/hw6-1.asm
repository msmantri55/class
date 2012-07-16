; Taylor Fausak
; 55515
; Assignment #6
; 23 October, 2008

; hw6-1.asm

; Summary
;	Calculates the 21st Fibonacci number and stores it in R4

; Registers
;	R4 is the current Fibonacci number
;	R2 and R3 are the two previous Fibonacci numbers, with R2 < R3
;	R5 is the loop counter

.ORIG x3000

AND R2, R2, #0		; R2 = 0
ADD R3, R2, #1		; R3 = R2 + 1 = 1
ADD R5, R2, #11		; R5 = R2 + 11 = 11
ADD R5, R5, #10		; R5 = R5 + 10 = 21

START
ADD R4, R2, R3		; R4 = R2 + R3
ADD R5, R5, #-1		; R5 = R5 + (-1)
BRZ DONE		; jump to DONE if zero
ADD R2, R3, #0		; R2 = R3 + 0
ADD R3, R4, #0		; R3 = R4 + 0
BR START		; unconditional jump to START

DONE
ADD R2, R4, #0		; R2 = R4 + 0
HALT
.END
