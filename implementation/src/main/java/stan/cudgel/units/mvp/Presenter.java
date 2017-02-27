package stan.cudgel.units.mvp;

import java.util.Random;

public abstract class Presenter<VIEW>
{
    private final Random random = new Random();
    private final String tag;
    private final VIEW view;

    public Presenter(VIEW v)
    {
        view = v;
        tag = "["+getClass().getName().replace(getClass().getPackage().getName()+".", "")+"]";
    }

    final protected VIEW getView()
    {
        return view;
    }

    final protected void onNewThread(Runnable runnable)
    {
        new Thread(runnable).start();
    }
    final protected void together(Runnable... runnables)
    {
        for(Runnable runnable : runnables)
        {
            new Thread(runnable).start();
        }
    }

    final protected int nextInt()
    {
        return nextInt(Integer.MAX_VALUE-2)+1;
    }
    final protected int nextInt(int range)
    {
        return random.nextInt(range);
    }

    final protected void log(String message)
    {
        System.out.println(tag + ": " + message);
    }
}