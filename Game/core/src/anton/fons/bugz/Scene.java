package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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

import java.util.ArrayList;

public class Scene
{
    public ModelBatch modelBatch;

    public boolean loading;

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private static String textToPrint = "";

    public AssetManager assets;

    public Environment environment;
    public PerspectiveCamera cam;
    public CameraInputController camController;

    protected ArrayList<GameObject> gameObjects;

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

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        loading = true;

        gameObjects = new ArrayList<GameObject>();

        for(GameObject go : gameObjects)  go.create();
    }

    public void update()
    {
        camController.update();
    }

    public void render()
    {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
}
