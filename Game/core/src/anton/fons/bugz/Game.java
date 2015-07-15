package anton.fons.bugz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import anton.fons.bugz.Scenes.*;

import java.util.ArrayList;

import anton.fons.bugz.Scenes.*;

public class Game extends ApplicationAdapter
{
	public static IAndroidResolver AndroidResolver;

	public boolean created = false;

	private Scene currentScene;
	private WalkingScene walkingScene;

	public Game(IAndroidResolver androidResolver)
	{
		AndroidResolver = androidResolver;
	}

	@Override
	public void create()
	{
		walkingScene = new WalkingScene();
		changeScene(walkingScene);
		created = true;
	}

	@Override
	public void render ()
	{
		if(currentScene != null)
		{
			currentScene._update(Gdx.graphics.getDeltaTime());
			currentScene._sceneRender();
		}
	}

	@Override
	public void dispose ()
	{
		currentScene._dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		currentScene._resize(width, height);
	}

	@Override
	public void pause()
	{
		currentScene._pause();
	}

	@Override
	public void resume()
	{
		currentScene._pause();
	}

	public void onStepDone()
	{
		if(currentScene == walkingScene)
		{
			walkingScene.onStepDone();
		}
	}

	public Scene getCurrentScene() { return currentScene; }

	public void changeScene(Scene newScene)
	{
		if(currentScene != null) currentScene._dispose();

		newScene._create();
		currentScene = newScene;
	}
}
