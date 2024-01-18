public class Example_15_2
{
  public synchronized void work(int id, Object value)
  {
    //if(value == 9)
    {
    //    value = 0;
    }
    System.out.println("Thread "+id+" changed the value to "+value);
    //System.out.println("Thread "+Thread.currentThread().getName()+" execution");
  }
}
