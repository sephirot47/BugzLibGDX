package anton.fons.bugz.android;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import anton.fons.bugz.Game;

public class AndroidLauncher extends AndroidApplication
{
	public static Activity activity;

	private StepManager stepManager;
	private AndroidResolver androidResolver;

	public static Game game;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		activity = this;

		stepManager = new StepManager();
		androidResolver = new AndroidResolver();

		game = new Game(androidResolver);
		initialize(game, config);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		stepManager.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		stepManager.onPause();
	}
}
