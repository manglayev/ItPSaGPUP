import java.util.Random;
public class Example_5 implements Runnable
{
    int id;
    int time;
    Random generator = new Random();
    public Example_5(int name)
    {
        this.id = name;
        time = generator.nextInt(5000);
    }
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(time);
            System.out.println("id: "+id+"; sleep time: "+time);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
