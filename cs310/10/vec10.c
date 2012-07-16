#include <stdio.h>

#define LEN 8

int result;

int vector_sum (int size, int *vec) {
	int sum = 0;
	int counter = 0;
  
	while (counter < size) {
		sum += vec[counter];
		counter++;
	}

	return sum;
}

main () {
	int vector[LEN] = {1, -2, 8, 4, 5, 10, -7, 9};
	result = vector_sum(LEN, vector);
	printf("Sum of elements = %d\n", result);
}
