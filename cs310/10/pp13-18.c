#import <stdio.h>

int main () {
	int x, y, i;

	printf("a:\t");
	// part a
	x = 20;
	y = 10;
	while ((x > 10) && (y & 15)) {
		y = y + 1;
		x = x - 1;
		printf("* ");
	}

	printf("\nb:\t");
	// part b
	for (x = 10; x; x = x - 1)
		printf("* ");

	printf("\nc:\t");
	// part c
	for (x = 0; x < 10; x = x + 1) {
		if (x % 2)
			printf("* ");
	}

	printf("\nd:\t");
	// part d
	x = 0;
	while (x < 10) {
		for (i = 0; i < x; i = x + 1)
			printf("* ");
		x = x + 1;
	}

	return 0;
}
