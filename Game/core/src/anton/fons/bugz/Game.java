package anton.fons.bugz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import anton.fons.bugz.SceneGraph.Scene;
import anton.fons.bugz.Scenes.BoardScene;
import anton.fons.bugz.Scenes.LoadingScene;
import anton.fons.bugz.Scenes.WalkingScene;

public class Game extends ApplicationAdapter
{
	public static IAndroidResolver AndroidResolver;

	public boolean created = false;

	public static Scene currentScene, pendingScene;

	private LoadingScene loadingScene;
	private WalkingScene walkingScene;
	private BoardScene boardScene;

	private static ResourceManager resourceManager;

	public Game(IAndroidResolver androidResolver)
	{
		AndroidResolver = androidResolver;
		resourceManager = new ResourceManager();
	}

	@Override
	public void create()
	{
		loadingScene = new LoadingScene();
		walkingScene = new WalkingScene();
		boardScene = new BoardScene();

		loadAndChangeScene(boardScene);
		created = true;
	}

	boolean changed1 = false, changed2 = false, changed3 = false, changed4 = false;
	long mec = System.currentTimeMillis();
	@Override
	public void render ()
	{
		Game.getResourceManager().update(); //Load things if u have to

		if(currentScene != null)
		{
			currentScene._update(Gdx.graphics.getDeltaTime());
			currentScene._sceneRender();
		}

		if(pendingScene != null)
		{
			//When the pendingScene is loaded, swap loadingScene with it
			if( getResourceManager().sceneLoaded(pendingScene) )
			{
				changeScene(pendingScene);
				pendingScene = null;
			}
		}

		if(System.currentTimeMillis() - mec >= 7000 && !changed1)
		{
			changed1 = true;
			loadAndChangeScene(walkingScene);
		}
		else if (System.currentTimeMillis() - mec >= 13000 && !changed2)
		{
			changed2 = true;
			loadAndChangeScene(boardScene);
		}
		else if (System.currentTimeMillis() - mec >= 16000 && !changed3)
		{
			changed3 = true;
			loadAndChangeScene(walkingScene);
		}
		else if (System.currentTimeMillis() - mec >= 23000 && !changed4)
		{
			changed4 = true;
			loadAndChangeScene(boardScene);
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

	public Scene getCurrentScene() { return currentScene; }

	//Puts the newScene in the queue. Meanwhile, the loadingScene is shown
	public void loadAndChangeScene(anton.fons.bugz.SceneGraph.Scene newScene)
	{
		if(pendingScene != null) return;

		changeScene(loadingScene);

		pendingScene = newScene;
		pendingScene._create();
	}

	//REALLY Changes the scene to newScene
	public void changeScene(anton.fons.bugz.SceneGraph.Scene newScene)
	{
		if(currentScene != null) currentScene._dispose();
		currentScene = newScene;
		currentScene._create();
	}

	public static ResourceManager getResourceManager()
	{
		return resourceManager;
	}


	public void onStepDone()
	{
		if(currentScene == walkingScene)
		{
			walkingScene.onStepDone();
		}
	}
}
