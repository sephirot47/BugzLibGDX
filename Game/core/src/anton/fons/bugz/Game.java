package anton.fons.bugz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Game extends ApplicationAdapter
{
	public static IAndroidResolver AndroidResolver;

	private ArrayList<Scene> scenes;
	private Scene currentScene;
	private WalkingScene walkingScene;

	public Game(IAndroidResolver androidResolver)
	{
		AndroidResolver = androidResolver;
	}

	public void changeScene(Scene newScene)
	{
		if(currentScene != null) currentScene.dispose();

		newScene.create();
		currentScene = newScene;
	}

	@Override
	public void create ()
	{
		walkingScene = new WalkingScene();

		scenes = new ArrayList<Scene>();
		scenes.add(walkingScene);

		changeScene(walkingScene);
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
		for(Scene s : scenes) s.dispose();
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

	public Scene getCurrentScene() { return currentScene; }
}
