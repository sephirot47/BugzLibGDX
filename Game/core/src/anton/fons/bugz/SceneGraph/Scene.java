package anton.fons.bugz.SceneGraph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import java.util.ArrayList;

import anton.fons.bugz.Game;

public abstract class Scene extends SceneGraphNode
{
    public boolean assetsLoaded = false;

    private ModelBatch modelBatch;
    private DecalBatch decalBatch;
    private ArrayList<Decal> decals;

    private Canvas canvas = null;

    private Environment environment;
    private Camera cam;

    public Scene()
    {
        super();
    }

    @Override
    public void _create()
    {
        assetsLoaded = false;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 4f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        modelBatch = new ModelBatch();
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));
        decals = new ArrayList<Decal>();

        create();

        Game.getResourceManager().addScene(this);
        loadAssets(); //Load all the assets
    }

    public abstract void loadAssets();

    protected void onAssetsLoaded()
    {
        for(SceneGraphNode child : getChildren()) child._create();
    }

    @Override
    public void _update(float deltaTime)
    {
        if (!Game.getResourceManager().sceneLoaded(this)) return;
        else if (!assetsLoaded)
        {
            onAssetsLoaded(); //Called only once
            assetsLoaded = true;
        }

        if(assetsLoaded)
        {
            update(deltaTime);
            for (SceneGraphNode child : getChildren()) child._update(deltaTime);
        }
    }


    //Called from Game, in order to start all the rendering (needed because Game doesn't have the)
    //modelBatch or the environment
    public final void _sceneRender()
    {
        if(Game.getResourceManager().sceneLoaded(this))
        {
            _render(modelBatch, environment);
            for(Decal dec : decals)
            {
                decalBatch.add(dec);
            }
            decalBatch.flush();
        }
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
        decalBatch.dispose();

        removeAllChildren();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    @Override
    public void addDecal(Decal dec)
    {
        decals.add(dec);
    }

    @Override
    public void removeDecal(Decal dec)
    {
        decals.remove(dec);
    }

    public void setEnvironment(Environment env) { environment = env; }
    //public void setViewport(Viewport viewport) { this.viewport = viewport; }
    public void setCamera(Camera camera) { cam = camera; }
    public void setCanvas(Canvas canvas)
    {
        if(canvas != null) removeChild(canvas);
        this.canvas = canvas;
        addChild(canvas);
    }


    @Override
    public SceneGraphNode getParent() { return this; }

    public Environment getEnvironment() { return environment; }
    //public Viewport getViewport() { return viewport; }
    public Camera getCamera() { return cam; }
    public Canvas getCanvas() { return canvas; }
}
