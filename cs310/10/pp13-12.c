#import <stdio.h>

int main () {
	int x;

	printf("\n\na.\n");
	x = 4;
	if (7 > x > 2)
		printf("true");
	else
		printf("false");

	printf("\n\nb.\n");
	x = 4;
	while (x > 0)
		x++;

	printf("\n\nc.\n");
	x = 4;
	for (x = 4; x < 4; x--) {
		if (x < 2)
			break;
		else if (x == 2)
			continue;
		x = -1;
	}
	printf("%d\n", x);

	return 0;
}
