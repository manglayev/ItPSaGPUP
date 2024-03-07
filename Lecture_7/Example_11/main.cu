#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include "cuda.h"

#include <stdio.h>
#include <stdlib.h>

#define THREADS 5
#define BLOCKS 1

template <typename T> class Array
{
  private:
	  T* ptr;
	  int size;

  public:
    __host__ __device__ Array(T arr[], int s);
    __host__ __device__ void print();
};

template <class T> __host__ __device__ Array<T>::Array(T arr[], int s)
{
	ptr = new T[s];
	size = s;
	for (int i = 0; i < size; i++)
		ptr[i] = arr[i];
}

template <class T> __host__ __device__ void Array<T>::print()
{
    for (int i = 0; i < size; i++)
      printf(" %d", *(ptr + i));
    printf("\n");
}

__global__ void globalFunction(int *x)
{
  int idx = threadIdx.x + blockIdx.x * blockDim.x;
  if(idx < 1)
  {
    int arr[THREADS] = { 1, 2, 3, 4, 5 };
    Array<int> array(arr, THREADS);
    printf("PRINT FROM KERNEL:\n");
    array.print();
  }
}

int main()
{
  //C++ version
  int arr[THREADS] = { 1, 2, 3, 4, 5 };
  Array<int> array(arr, THREADS);
  printf("PRINT FROM HOST:\n");
  array.print();
  //CUDA version
  int *dev_a;
  cudaMallocManaged(&dev_a, sizeof(int));
  dev_a[0] = THREADS;
  globalFunction<<<BLOCKS, THREADS>>>(dev_a);
  cudaDeviceSynchronize();
  printf("AFTER KERNEL %d = %d\n", THREADS, dev_a[0]);
  cudaFree(dev_a);
  return 0;
}