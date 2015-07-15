package anton.fons.bugz;

import com.badlogic.gdx.ApplicationAdapter;

import java.util.ArrayList;

import anton.fons.bugz.Scenes.WalkingScene;

public class Game extends ApplicationAdapter
{
	public static IAndroidResolver AndroidResolver;

	public boolean created = false;

	private ArrayList<anton.fons.bugz.Scenes.Scene> scenes;
	private anton.fons.bugz.Scenes.Scene currentScene;
	private anton.fons.bugz.Scenes.WalkingScene walkingScene;

	public Game(IAndroidResolver androidResolver)
	{
		AndroidResolver = androidResolver;
	}

	public void changeScene(anton.fons.bugz.Scenes.Scene newScene)
	{
		if(currentScene != null) currentScene.dispose();

		newScene.create();
		currentScene = newScene;
	}

	@Override
	public void create ()
	{
		walkingScene = new WalkingScene();

		scenes = new ArrayList<anton.fons.bugz.Scenes.Scene>();
		scenes.add(walkingScene);

		changeScene(walkingScene);

		created = true;
	}

	@Override
	public void render ()
	{
		currentScene.update();
		currentScene.render();
	}

	@Override
	public void dispose ()
	{
		for(anton.fons.bugz.Scenes.Scene s : scenes) s.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		currentScene.resize(width, height);
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

	public anton.fons.bugz.Scenes.Scene getCurrentScene() { return currentScene; }
}
