package anton.fons.bugz.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import anton.fons.bugz.Canvas;
import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.GameObject;
import anton.fons.bugz.SceneGraphNode;

public class Scene extends SceneGraphNode
{
    private AssetManager assetsManager;

    private ModelBatch modelBatch;

    private Canvas canvas;

    private Environment environment;
    private Camera cam;

    public Scene()
    {
        super();

        //A scene is created by himself
        //A gameObject (a diferencia), is created when it's added to a scene, and disposed when removed
        _create();
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

        canvas = new Canvas(); //CREATE THE CANVAS AND ADD IT AS A CHILD :)
        addChild(canvas);
    }

    protected void onAssetsLoaded() {
        super.onAssetsLoaded();
    }

    //Called from Game, in order to start all the rendering (needed because Game doesn't have the)
    //modelBatch or the environment
    public final void _sceneRender()
    {
        _render(modelBatch, environment);
    }

    @Override
    protected void prerender(ModelBatch modelBatch, Environment environment) {
        super.prerender(modelBatch, environment);
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        // Here all the render functions of the childs will be called (SceneGraphNode's duty)
    }

    @Override
    protected void postrender(ModelBatch modelBatch, Environment environment) {
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
    }

    @Override
    public AssetManager getAssetsManager()
    {
        return assetsManager;
    }

    public void setEnvironment(Environment env) { environment = env; }
    //public void setViewport(Viewport viewport) { this.viewport = viewport; }
    public void setCamera(Camera camera) { cam = camera; }
    public void setCanvas(Canvas canvas)
    {
        removeChild(canvas);
        this.canvas = canvas;
        addChild(canvas);
    }

    @Override
    public boolean assetsLoaded() { return assetsManager.update(); }

    @Override
    public SceneGraphNode getParent() { return this; }

    public Environment getEnvironment() { return environment; }
    //public Viewport getViewport() { return viewport; }
    public Camera getCamera() { return cam; }
    public Canvas getCanvas() { return canvas; }
}
