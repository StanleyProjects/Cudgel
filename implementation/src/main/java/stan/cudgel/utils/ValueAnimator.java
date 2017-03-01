package stan.cudgel.utils;

public abstract class ValueAnimator
{
    static final public Interpolator<Integer> linearIntegerInterpolator = (time, timePassed, begin, end) -> begin + (int)(timePassed*(end - begin)/time);
    static final public Interpolator<Double> linearDoubleInterpolator = (time, timePassed, begin, end) -> begin + timePassed*(end - begin)/time;
    static public Animator create(long time, int begin, int end, Updater<Integer> updater)
    {
        return create(time, begin, end, updater, linearIntegerInterpolator);
    }
    static public Animator create(long time, int begin, int end, Updater<Integer> updater, Interpolator<Integer> interpolator)
    {
        return new Animator<>(time, begin, end, updater, interpolator);
    }
    static public Animator create(int time, double begin, double end, Updater<Double> updater)
    {
        return create(time, begin, end, updater, linearDoubleInterpolator);
    }
    static public Animator create(int time, double begin, double end, Updater<Double> updater, Interpolator<Double> interpolator)
    {
        return new Animator<>(time, begin, end, updater, interpolator);
    }

    private ValueAnimator()
    {

    }

    public interface Updater<T>
    {
        void update(T t);
    }
    public interface Interpolator<T>
    {
        T interpolate(long time, long timePassed, T begin, T end);
    }
    public interface AnimationListener
    {
        void begin();
        void end();
        void cancel();
    }

    static public class AccelerateDoubleInterpolator
            implements Interpolator<Double>
    {
        private double factor;

        public AccelerateDoubleInterpolator(double f)
        {
            factor = f;
        }

        @Override
        public Double interpolate(long time, long timePassed, Double begin, Double end)
        {
            return begin + (end - begin)*Math.pow((timePassed*(end - begin)/time)/(end - begin), factor);
        }
    }
    static public class AccelerateDecelerateDoubleInterpolator
            implements Interpolator<Double>
    {
        private double factor;

        public AccelerateDecelerateDoubleInterpolator(double f)
        {
            factor = f;
        }

        @Override
        public Double interpolate(long time, long timePassed, Double begin, Double end)
        {
            return begin + (end - begin)*Math.pow(Math.sin(Math.PI*((timePassed*(end - begin)/time)/(end - begin))-Math.PI/2)/2+0.5, factor);
        }
    }
    static public class Animator<T>
    {
        volatile private boolean isCancel;
        private long time;
        private T beginValue;
        private T endValue;
        private Updater<T> updater;
        private Interpolator<T> interpolator;
        private AnimationListener animationListener;

        private long timeBegin;
        private long timeNow;
        private long timeEnd;

        private Animator(long t, T b, T e, Updater<T> u, Interpolator<T> inter)
        {
            isCancel = false;
            time = t;
            beginValue = b;
            endValue = e;
            updater = u;
            interpolator = inter;
        }

        public void animate()
        {
            timeBegin = System.currentTimeMillis();
            timeEnd = timeBegin + time;
            timeNow = timeBegin;
            new Thread(() ->
            {
                if(animationListener != null)
                {
                    animationListener.begin();
                }
                while(timeNow < timeEnd && !isCancel)
                {
                    T oldValue = interpolator.interpolate(time, timeNow-timeBegin, beginValue, endValue);
                    updater.update(oldValue);
                    timeNow = System.currentTimeMillis();
                    while(oldValue.equals(interpolator.interpolate(time, timeNow-timeBegin, beginValue, endValue)) && timeNow < timeEnd && !isCancel)
                    {
                        timeNow = System.currentTimeMillis();
                    }
                }
                if(isCancel)
                {
                    if(animationListener != null)
                    {
                        animationListener.cancel();
                    }
                    return;
                }
                updater.update(endValue);
                if(animationListener != null)
                {
                    animationListener.end();
                }
            }).start();
        }
        public void cancel()
        {
            isCancel = true;
        }

        public Animator<T> setAnimationListener(AnimationListener listener)
        {
            animationListener = listener;
            return this;
        }
    }
}