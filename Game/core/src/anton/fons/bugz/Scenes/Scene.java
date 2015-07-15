package anton.fons.bugz.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import anton.fons.bugz.GameObjects.GameObject;

public class Scene
{
    public ModelBatch modelBatch;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private static String textToPrint = "";

    public AssetManager assets;

    public Environment environment;

    public Camera cam;
    private Viewport viewport;

    private ArrayList<GameObject> gameObjects;

    public Scene() {}

    public void create()
    {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        assets = new AssetManager();

        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(7f, 7f, 7f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        viewport = new ExtendViewport(100.0f, 100.0f, cam);
        viewport.apply();

        gameObjects = new ArrayList<GameObject>();

        for(GameObject go : gameObjects)  go.create();
    }

    public void update()
    {
    }

    public void render()
    {
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Render all gameObjects
        modelBatch.begin(cam);
        for(GameObject go : gameObjects)  go.render(modelBatch, environment);
        modelBatch.end();
        //

        //Render screenLog text
        spriteBatch.begin();
        Matrix4 transform = new Matrix4();
        //transform.scale(5.0f, 5.0f, 5.0f);
        spriteBatch.setTransformMatrix(transform);
        font.draw(spriteBatch, textToPrint, 100, 100);
        spriteBatch.end();
        //
    }

    public void dispose()
    {
        modelBatch.dispose();
        assets.dispose();

        spriteBatch.dispose();
        font.dispose();
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

    public void pause()
    {
    }

    public void resume()
    {
    }

    public void screenLog(String msg)
    {
        textToPrint = new String(msg);
    }

    public void addGameObject(GameObject go) { gameObjects.add(go); }
    public void removeGameObject(GameObject go) { gameObjects.remove(go); }
    public void setEnvironment(Environment env) { environment = env; }
    public void setViewport(Viewport viewport) { this.viewport = viewport; }
    public void setCamera(Camera camera) { cam = camera; }

    public List<GameObject> getGameObjects() { return gameObjects; }
    public Environment getEnvironment() { return environment; }
    public Viewport getViewport() { return viewport; }
    public Camera getCamera() { return cam; }
}
