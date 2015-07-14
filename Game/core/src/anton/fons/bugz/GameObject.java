package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;

public class GameObject
{
    protected AssetManager assets;
    private boolean assetsProcessed;
    private String modelFilepath;

    protected ModelInstance modelInstance;

    private AnimationController animationController;

    public ArrayList<GameObject> childs;

    public GameObject(String modelFilepath)
    {
        assets = new AssetManager();

        this.modelFilepath = modelFilepath;

        childs = new ArrayList<GameObject>();

        _loadAssets();
    }

    private void _loadAssets()
    {
        assets.load(modelFilepath, Model.class); //load everything we need

        assetsProcessed = false;

        loadAssets();
    }

    private void _onAssetsLoaded()
    {
        Model model = assets.get(modelFilepath, Model.class);
        modelInstance = new ModelInstance(model); //get the model instance

        animationController = new AnimationController(modelInstance); //set the animController
        animationController.allowSameAnimation = false;

        assetsProcessed = true;

        onAssetsLoaded();
    }

    public void render(ModelBatch modelBatch, Environment environment)
    {
        if( !assets.update() ) return; //If it's still loading...
        else if (!assetsProcessed) _onAssetsLoaded(); //Assets have just loaded, must be processed

        //Update
        update(Gdx.graphics.getDeltaTime());

        //Update animations
        animationController.update(Gdx.graphics.getDeltaTime());

        for(GameObject child : childs) //Render its childs
        {
            child.render(modelBatch, environment);
        }

        //Draw the model instance
        modelBatch.render(modelInstance, environment);
    }

    protected void loadAssets() {}
    protected void onAssetsLoaded() {}
    public void update(float deltaTime) {}

    //Animation functions
    protected void pause()
    {
        animationController.paused = true;
    }

    protected void resume()
    {
        animationController.paused = false;
    }

    protected void playLoop(String animationId) { play(animationId, -1, 0.5f); }
    protected void play(String animationId) { play(animationId, 1, 0.5f); }
    protected void play(String animationId, int loopCount) { play(animationId, loopCount, 0.5f); }
    protected void play(String animationId, float transitionTime) { play(animationId, 1, transitionTime); }
    protected void playLoop(String animationId, float transitionTime) { play(animationId, -1, transitionTime); }
    protected void play(String animationId, int loopCount, float transitionTime)
    {
        animationController.paused = false;
        animationController.animate(animationId, loopCount, 1.0f, null, transitionTime);
    }

    protected void stop()
    {
        animationController.setAnimation(null);
    }
    ////////////////
}