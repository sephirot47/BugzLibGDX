package anton.fons.bugz.android;

import android.app.Activity;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import anton.fons.bugz.Game;

public class AndroidLauncher extends AndroidApplication
{
	public static Activity activity;

	private ConnectionManager connectionManager;
	private StepManager stepManager;
	public static AndroidResolver androidResolver;

	public static Game game;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		activity = this;
		androidResolver = new AndroidResolver();

		connectionManager = new ConnectionManager();
		stepManager = new StepManager();

		game = new Game(androidResolver);
		initialize(game, config);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		stepManager.onResume();
		connectionManager.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		stepManager.onPause();
		connectionManager.onPause();
	}
}
