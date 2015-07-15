package anton.fons.bugz.android;

import android.util.Log;

import anton.fons.bugz.IAndroidResolver;

public class AndroidResolver implements IAndroidResolver
{
    private int steps;

    public AndroidResolver()
    {
        steps = 0;
    }

    @Override
    public void log(String text)
    {
        Log.d("Bugz", text);
    }
}
