#include <stdio.h>

int func1(int);

main () {
  int val, result;
  printf("Enter a number (0-9): ");
  scanf("%d", &val);

  if(val<0 || val > 9) {
    printf("input out of bounds\n");
  }

  else {
    result = func1(val);
    printf("result is: %d\n", result);
  }
}
