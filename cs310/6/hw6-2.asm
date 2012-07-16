; Taylor Fausak
; 55515
; Assignment #6
; 23 October, 2008

; hw6-2.asm

; Summary
;	Uses a bit mask to mask out bits [12:7] or a data word

; Registers
;	R0 is the bit mask
;	R1 is the data word to be masked

.ORIG	x3000
LD	R0, Zero
LD	R1, Zorro

; BEGIN my code
; I need the mask (R0) to be 1110000001111111
ADD	R0, R0, #7
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, #15
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, R0
ADD	R0, R0, #7
; Apply the mask
AND	R2, R1, R0
; END my code

HALT

; Memory fills
Zero	.FILL	x0000
Zorro	.FILL	xF326
.END
