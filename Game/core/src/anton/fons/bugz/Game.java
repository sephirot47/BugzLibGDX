package anton.fons.bugz;

import com.badlogic.gdx.ApplicationAdapter;
import anton.fons.bugz.Scenes.*;

import java.util.ArrayList;

import anton.fons.bugz.Scenes.*;

public class Game extends ApplicationAdapter
{
	public static IAndroidResolver AndroidResolver;

	public boolean created = false;

	private ArrayList<Scene> scenes;
	private Scene currentScene;
	private WalkingScene walkingScene;

	public Game(IAndroidResolver androidResolver)
	{
		AndroidResolver = androidResolver;
	}

	public void changeScene(anton.fons.bugz.Scenes.Scene newScene)
	{
		if(currentScene != null) currentScene._dispose();

		newScene._create();
		currentScene = newScene;
	}

	@Override
	public void create()
	{
		scenes = new ArrayList<Scene>();

		walkingScene = new WalkingScene();
		scenes.add(walkingScene);

		changeScene(walkingScene);

		created = true;
	}

	@Override
	public void render ()
	{
		if(currentScene != null)
		{
			currentScene._update();
			currentScene._render();
		}
	}

	@Override
	public void dispose ()
	{
		for(anton.fons.bugz.Scenes.Scene s : scenes) s.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		currentScene._resize(width, height);
	}

	@Override
	public void pause()
	{
		currentScene.pause();
	}

	@Override
	public void resume()
	{
		currentScene.pause();
	}

	public void onStepDone()
	{
		if(currentScene == walkingScene)
		{
			walkingScene.onStepDone();
		}
	}

	public Scene getCurrentScene() { return currentScene; }
}
