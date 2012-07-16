	.file	"main.c"
	.section	.rodata
.LC0:
	.string	"Enter a number (0-9): "
.LC1:
	.string	"%d"
.LC2:
	.string	"input out of bounds"
.LC3:
	.string	"result is: %d\n"
	.text
.globl main
	.type	main, @function
main:
	leal	4(%esp), %ecx
	andl	$-16, %esp
	pushl	-4(%ecx)
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ecx
	subl	$36, %esp
	movl	$.LC0, (%esp)
	call	printf
	leal	-12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC1, (%esp)
	call	scanf
	movl	-12(%ebp), %eax
	testl	%eax, %eax
	js	.L2
	movl	-12(%ebp), %eax
	cmpl	$9, %eax
	jle	.L4
.L2:
	movl	$.LC2, (%esp)
	call	puts
	jmp	.L7
.L4:
	movl	-12(%ebp), %eax
	movl	%eax, (%esp)
	call	func1
	movl	%eax, -8(%ebp)
	movl	-8(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC3, (%esp)
	call	printf
.L7:
	addl	$36, %esp
	popl	%ecx
	popl	%ebp
	leal	-4(%ecx), %esp
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 4.2.2"
	.section	.note.GNU-stack,"",@progbits
