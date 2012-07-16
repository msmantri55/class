; Taylor Fausak
; HW #9
; 24 November 2008
; 
; fib-9.asm

.ORIG x3000

MAIN
     LD  R6, STACKSTAR		;  load start of stack

; Put initial call to fibonacci here


     HALT



STACKSTART .FILL xd000
INPUT      .FILL x0011
OUTPUT     .FILL x0000
.END
