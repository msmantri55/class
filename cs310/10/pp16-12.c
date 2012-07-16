#include <stdio.h>
#define MAX_LEN 10
char *LowerCase(char *s);

int main () {
	char str[MAX_LEN];
	printf("Enter a string : ");
	scanf("%s", str);
	printf("Lowercase: %s \n", Lowercase(str));
}

char *LowerCase (char *s) {
	char newStr[MAX_LEN];
	int index;
	for (index = 0; index < MAX_LEN; index++) {
		if ('A' <= s[index] && s[index] <= 'Z')
			newStr[index] = s[index] + ('a' - 'A');
		else
			newStr[index] = s[index];
	}
	return newStr;
}
