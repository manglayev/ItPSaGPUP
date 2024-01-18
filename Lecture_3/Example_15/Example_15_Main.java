// Java program to illustrate Starvation concept
class Example_15_Main
{
  public static Object sharedObject = new Object();
	public static void main(String[] args) throws InterruptedException
	{
		System.out.println("Main thread execution starts");
    // Thread priorities are set in a way that thread[size - 1] gets least priority.
    int size = 4;
    Thread[] threads = new Thread[size];
    for (int a = 0; a < size; a++)
    {
        threads[a] = new Thread( new Example_15_1(a) );
    }
    for (int a = 0; a < size; a++)
    {
        threads[a].start();
    }
    for (int a = 0; a < size; a++)
    {
        threads[a].join();
    }
		System.out.println("Main thread execution completes");
	}
}
