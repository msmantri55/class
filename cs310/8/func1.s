	.file	"func1.c"
	.text
.globl func1
	.type	func1, @function
func1:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$20, %esp
	cmpl	$0, 8(%ebp)
	jne	.L2
	movl	$0, -20(%ebp)
	jmp	.L4
.L2:
	cmpl	$1, 8(%ebp)
	je	.L5
	cmpl	$2, 8(%ebp)
	jne	.L7
.L5:
	movl	$1, -20(%ebp)
	jmp	.L4
.L7:
	movl	$1, -12(%ebp)
	movl	$1, -8(%ebp)
	movl	$0, -4(%ebp)
	movl	$0, -16(%ebp)
	jmp	.L8
.L9:
	movl	-12(%ebp), %eax
	addl	-8(%ebp), %eax
	movl	%eax, -4(%ebp)
	movl	-8(%ebp), %eax
	movl	%eax, -12(%ebp)
	movl	-4(%ebp), %eax
	movl	%eax, -8(%ebp)
	addl	$1, -16(%ebp)
.L8:
	movl	8(%ebp), %eax
	subl	$2, %eax
	cmpl	-16(%ebp), %eax
	jg	.L9
	movl	-4(%ebp), %eax
	movl	%eax, -20(%ebp)
.L4:
	movl	-20(%ebp), %eax
	leave
	ret
	.size	func1, .-func1
	.ident	"GCC: (GNU) 4.2.2"
	.section	.note.GNU-stack,"",@progbits
