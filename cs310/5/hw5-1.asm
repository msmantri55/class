; Taylor Fausak
; CS310
; HW #5
; 17 October 2008

; hw5-1.asm

;	Since this program has store 342 in R2 in ten or fewer instructions,
; repeatedly adding the maximum immediate value (#+15) to R2 wouldn't work
; (it would require 23 instructions). So a geometric approach was necessary.
; I set R0 to 14 (one less than the max), and doubled it 5 times, storing
; the second-to-last value as R1, which makes R0 + R1 as close to 342 as
; possible;
;	I would be interested to see if this can be done in fewer than 7
; instructions.

.ORIG	x5000		; directive: put code at start of user memory
LD	R0, Zero	; Zero out the initial register

; BEGIN my code
ADD	R0, R0, #14	; R0 <- R0 + 14		0 + 14 = 14
ADD	R0, R0, R0	; R0 <- R0 + R0		14 + 14 = 28
ADD	R0, R0, R0	; R0 <- R0 + R0		28 + 28 = 56
ADD	R0, R0, R0	; R0 <- R0 + R0		56 + 56 = 112
ADD	R1, R0, R0	; R1 <- R0 + R0		112 + 112 = 224
ADD	R2, R0, R1	; R2 <- R0 + R1		112 + 224 = 336
ADD	R2, R2, #6	; R2 <- R2 + 6		336 + 6 = 342
; END my code

HALT

; Memory fills
Zero	.FILL	x0000	; Zero to initialize registers
	.END		; directive: no more code
