#import <stdio.h>
#define LIMIT 40

int main () {
        int n; // max: 2 147 483 647

        for (n = 0; n < LIMIT; n++)
                printf("f(%d):\t%d\n", n, fib(n));

        return 0;
}

int fib (int n) {
        if (n == 0)
                return 0;
        else if (n == 1)
                return 1;
        else
                return fib(n - 1) + fib(n - 2); 
}
