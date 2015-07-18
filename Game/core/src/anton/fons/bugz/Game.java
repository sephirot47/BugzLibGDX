package anton.fons.bugz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import anton.fons.bugz.SceneGraph.Scene;
import anton.fons.bugz.Scenes.BoardScenePackage.BoardScene;
import anton.fons.bugz.Scenes.LoadingScenePackage.LoadingScene;
import anton.fons.bugz.Scenes.WalkingScenePackage.WalkingScene;

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
	public void loadAndChangeScene(Scene newScene)
	{
		if(pendingScene != null) return;

		if (resourceManager.sceneLoaded(newScene)) {
            changeScene(newScene);
            newScene._create(); //Has to be called after because changeScene dispose current scene (and can be the same as newScene)
		} else {
			changeScene(loadingScene);
            loadingScene._create();

			pendingScene = newScene;
			pendingScene._create(); //Start loading its assets
		}
	}

	//REALLY Changes the scene to newScene
	public void changeScene(anton.fons.bugz.SceneGraph.Scene newScene)
	{
		if(currentScene != null) currentScene._dispose();
		currentScene = newScene;
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
