package anton.fons.bugz.android;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import anton.fons.bugz.Game;

public class AndroidLauncher extends AndroidApplication
{
	AndroidResolver androidResolver;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		androidResolver = new AndroidResolver();

		initialize(new Game(androidResolver), config);
	}
}
