#include "kernel_header.cuh"
#include <stdio.h>
#include <stdlib.h>

int main()
{
  int a = 1000;
  wrapperCaller(a);
  return 0;
}
