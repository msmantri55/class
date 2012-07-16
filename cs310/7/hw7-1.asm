; Taylor Fausak (tdf268)
; 55515
; Assignment #7
; 2 November 2008

; hw7-1.asm

; Comments
; if(snum <= 0)
;	Result = op1;
; else
;	Result = op1 >> snum;

.ORIG	x3000; directive: put code at start of user memory

LEA		R6, USER_STACK	; set up user stack
RS_START
LD		R0, op1			; bit string to shift
LD		R1, snum		; number of positions to shift right

; BEGIN my code
LOOP
	AND		R4, R4, #0
	LD		R5, mask15
	AND		R3, R0, R5
	BRz		M15
	LD		R5, mask14
	ADD		R4, R4, R5
M15
	LD		R5, mask14
	AND		R3, R0, R5
	BRz		M14
	LD		R5, mask13
	ADD		R4, R4, R5
M14
	LD		R5, mask13
	AND		R3, R0, R5
	BRz		M13
	LD		R5, mask12
	ADD		R4, R4, R5
M13
	LD		R5, mask12
	AND		R3, R0, R5
	BRz		M12
	LD		R5, mask11
	ADD		R4, R4, R5
M12
	LD		R5, mask11
	AND		R3, R0, R5
	BRz		M11
	LD		R5, mask10
	ADD		R4, R4, R5
M11
	LD		R5, mask10
	AND		R3, R0, R5
	BRz		M10
	LD		R5, mask9
	ADD		R4, R4, R5
M10
	LD		R5, mask9
	AND		R3, R0, R5
	BRz		M9
	LD		R5, mask8
	ADD		R4, R4, R5
M9
	LD		R5, mask8
	AND		R3, R0, R5
	BRz		M8
	LD		R5, mask7
	ADD		R4, R4, R5
M8
	LD		R5, mask7
	AND		R3, R0, R5
	BRz		M7
	LD		R5, mask6
	ADD		R4, R4, R5
M7
	LD		R5, mask6
	AND		R3, R0, R5
	BRz		M6
	LD		R5, mask5
	ADD		R4, R4, R5
M6
	LD		R5, mask5
	AND		R3, R0, R5
	BRz		M5
	LD		R5, mask4
	ADD		R4, R4, R5
M5
	LD		R5, mask4
	AND		R3, R0, R5
	BRz		M4
	LD		R5, mask3
	ADD		R4, R4, R5
M4
	LD		R5, mask3
	AND		R3, R0, R5
	BRz		M3
	LD		R5, mask2
	ADD		R4, R4, R5
M3
	LD		R5, mask2
	AND		R3, R0, R5
	BRz		M2
	LD		R5, mask1
	ADD		R4, R4, R5
M2
	LD		R5, mask1
	AND		R3, R0, R5
	BRz		M1
	LD		R5, mask0
	ADD		R4, R4, R5
M1
	ADD		R0, R4, #0
	ADD		R1, R1, #-1
	BRp		LOOP
	
	ADD		R2, R0, #0
	BR		RS_END

mask15	.FILL	x8000
mask14	.FILL	x4000
mask13	.FILL	x2000
mask12	.FILL	x1000
mask11	.FILL	x0800
mask10	.FILL	x0400
mask9	.FILL	x0200
mask8	.FILL	x0100
mask7	.FILL	x0080
mask6	.FILL	x0040
mask5	.FILL	x0020
mask4	.FILL	x0010
mask3	.FILL	x0008
mask2	.FILL	x0004
mask1	.FILL	x0002
mask0	.FILL	x0001
; END my code

RS_END
ST		R2, Result 
HALT

; Data
op1		.FILL	xbf5c
snum	.FILL	#7
Result	.FILL	x0000
.BLKW	x20
USER_STACK

.END
