package anton.fons.bugz.android;

import android.content.pm.ActivityInfo;
import android.util.Log;

import anton.fons.bugz.IAndroidResolver;

public class AndroidResolver implements IAndroidResolver
{
    public AndroidResolver()
    {

    }

    @Override
    public void log(String text)
    {
        Log.d("Bugz", text);
    }

    @Override
    public void changeOrientationToPortrait()
    {
        AndroidLauncher.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void changeOrientationToLandscape()
    {
        AndroidLauncher.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
