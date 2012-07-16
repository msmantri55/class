#import <stdio.h>

int main () {
	int a = 6;
	int b = 9;

	printf("%d\n", a | b);
	printf("%d\n", a || b);
	printf("%d\n", a & b);
	printf("%d\n", a && b);
	printf("%d\n", !(a + b));
	printf("%d\n", a % b);
	printf("%d\n", b / a);
	printf("%d\n", a = b);
	printf("%d\n", a = b = 5);
	printf("%d\n", ++a + b--);
	printf("%d\n", a = (++b < 3) ? a : b);
	printf("%d\n", a <<= b);

	return 0;
}
