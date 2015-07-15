package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Vector;

public class GameObject implements AnimationController.AnimationListener
{
    protected AssetManager assets;
    private boolean assetsProcessed;
    private String modelFilepath;

    protected ModelInstance modelInstance;
    private Vector3 position, rotationAxis, scale;
    private float rotationDegrees;

    private AnimationController animationController;

    public ArrayList<GameObject> childs;

    public GameObject(String modelFilepath)
    {
        position = Vector3.Zero;
        rotationAxis = Vector3.Zero; rotationDegrees = 0f;
        scale = new Vector3(1f, 1f, 1f);

        assets = new AssetManager();

        this.modelFilepath = modelFilepath;

        childs = new ArrayList<GameObject>();

        _loadAssets();
    }

    public void create() {}

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
        try{ animationController.update(Gdx.graphics.getDeltaTime()); }
        catch(Exception e) { /*This is to avoid an app crash due to a libGDX iterators bug :S*/}

        modelInstance.transform = new Matrix4();
        modelInstance.transform.translate(position.x, position.y, position.z);
        modelInstance.transform.rotate(rotationAxis.x, rotationAxis.y, rotationAxis.z,
                rotationDegrees);
        modelInstance.transform.scale(scale.x, scale.y, scale.z);

        for(GameObject child : childs) //Render its childs
        {
            child.render(modelBatch, environment);
        }

        //Draw the model instance
        modelBatch.render(modelInstance, environment);
    }

    protected boolean assetsLoaded() { return assetsProcessed; }
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
    protected void playLoop(String animationId, float transitionTime) {
        play(animationId, -1, transitionTime); }
    protected void play(String animationId, int loopCount, float transitionTime)
    {
        animationController.paused = false;
        animationController.animate(animationId, loopCount, 1.0f, this, transitionTime);
    }

    protected void stop()
    {
        animationController.setAnimation(null);
    }

    //Animation Event listeners

    //Override this if you want to handle an animation end
    protected void onAnimationEnd(AnimationController.AnimationDesc animation) {}
    //Override this if you want to handle an animation loop
    protected void onAnimationLoop(AnimationController.AnimationDesc animation) {}

    @Override //Called when an animation ends
    public final void onEnd(AnimationController.AnimationDesc animation){
        onAnimationEnd(animation);
    }
    @Override  //Called when an animation loops
    public final void onLoop(AnimationController.AnimationDesc animation) {
        onAnimationLoop(animation);
    }
    ////////////////

    //Transform functions
    public void setScale(float scale) { this.scale = new Vector3(scale, scale, scale); }
    public void setScale(Vector3 scale) { this.scale = scale; }

    public void setRotation(float axisX, float axisY, float axisZ, float degrees)
    {
        this.rotationAxis = new Vector3(axisX, axisY, axisZ);
        this.rotationDegrees = degrees;
    }
    public void setRotation(Vector3 axis, float degrees)
    {
        this.rotationAxis = axis;
        this.rotationDegrees = degrees;
    }

    public void setPosition(float x, float y, float z) { this.position = new Vector3(x,y,z); }
    public void setPosition(Vector3 pos) { this.position = pos; }
    /////////////////////
}