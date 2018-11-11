#include <stdio.h>

int ciao(int b){
	b = b + b;
	return b;
}

int main()
{
 	int a;
 	a = 4 + 3;
 	a = ciao(a);
   printf("\nHello, World! %d \n\n", a);
   return 0;
}