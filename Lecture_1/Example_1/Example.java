import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class Example
{
  private static class ArraySumTask extends RecursiveAction
  {
    int start;
    int end;
    double [] array;
    double sum;
    public static int SEQUENTIAL_THRESHOLD = 100;
    ArraySumTask(int start, int end, double[] array)
    {
      this.start = start;
      this.end = end;
      this.array = array;
    }

    public double getSum()
    {
      return sum;
    }

    @Override
    protected void compute()
    {
      if(end - start <= SEQUENTIAL_THRESHOLD)
      {
        for(int a = start; a < end; a++)
        {
          sum += 1 / array[a];
        }
      }
      else
      {
        ArraySumTask left = new ArraySumTask(start, (start + end)/2, array);
        ArraySumTask right = new ArraySumTask((start + end)/2, end, array);
        left.fork();
        right.compute();
        left.join();
        sum = left.getSum() + right.getSum();
      }
    }
  }

  public double getParallelSum(double array[])
  {
    long startTime = System.nanoTime();
    ArraySumTask ast = new ArraySumTask(0, array.length, array);
    //this way
    //play with the number of threads
    ForkJoinPool pool = new ForkJoinPool(4);
    pool.invoke(ast);
    //or this way
    //ForkJoinPool.commonPool().invoke(ast);
    double sum = ast.getSum();
    long endTime = System.nanoTime() - startTime;
    printResults("  Parallel", endTime, sum);
    return sum;
  }

  public double getSequentialSum(double[] array)
  {
    long startTime = System.nanoTime();
    double sum = 0;
    for(int a = 0; a < array.length; a++)
    {
      sum += 1 / array[a];
    }
    long endTime = System.nanoTime() - startTime;
    printResults("Sequential", endTime, sum);
    return sum;
  }

  public void printResults(String type, double time, double sum)
  {
    System.out.printf("%s computation in %8.3f ms. sum = %8.9f \n", type, time / 1e6, sum);
  }

}
