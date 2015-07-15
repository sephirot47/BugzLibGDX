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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.GameObject;
import anton.fons.bugz.SceneGraphNode;

public class Scene extends SceneGraphNode
{
    private AssetManager assetsManager;

    private ModelBatch modelBatch;

    private Environment environment;
    private Camera cam;
    private Viewport viewport;

    public Scene()
    {
        super();
    }

    @Override
    protected void create()
    {
        super.create();
        assetsManager = new AssetManager();

        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 4f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        viewport = new ExtendViewport(100.0f, 100.0f, cam);
        //viewport.apply();
    }

    //Called from Game, in order to start all the rendering (needed because Game doesn't have the)
    //modelBatch or the environment
    public final void _sceneRender()
    {
        _render(modelBatch, environment);
    }

    @Override
    protected void prerender(ModelBatch modelBatch, Environment environment)
    {
        super.prerender(modelBatch, environment);
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        // Here all the render functions of the childs will be called (SceneGraphNode's duty)
    }

    @Override
    protected void postrender(ModelBatch modelBatch, Environment environment)
    {
        super.postrender(modelBatch, environment);

        // And once all the childs are rendered, end the modelBatch
        modelBatch.end();
    }

    public void dispose()
    {
        super.dispose();
        modelBatch.dispose();
        assetsManager.dispose();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public AssetManager getAssetsManager()
    {
        return assetsManager;
    }

    public void setEnvironment(Environment env) { environment = env; }
    public void setViewport(Viewport viewport) { this.viewport = viewport; }
    public void setCamera(Camera camera) { cam = camera; }

    @Override
    public SceneGraphNode getParent() { return this; }

    public Environment getEnvironment() { return environment; }
    public Viewport getViewport() { return viewport; }
    public Camera getCamera() { return cam; }
}
