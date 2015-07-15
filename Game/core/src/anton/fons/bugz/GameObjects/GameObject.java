package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Vector;

import anton.fons.bugz.Game;

public class GameObject implements AnimationController.AnimationListener
{
    protected AssetManager assets;
    private boolean assetsProcessed;
    private String modelFilepath;

    protected ModelInstance modelInstance;
    private Vector3 position, scale;
    private Quaternion rotation;

    private AnimationController animationController;

    public ArrayList<GameObject> childs;

    public GameObject(String modelFilepath)
    {
        this.modelFilepath = modelFilepath;

        position = new Vector3(0f, 0f, 0f);
        Game.AndroidResolver.log( position.toString());
        rotation = new Quaternion();
        scale = new Vector3(1f, 1f, 1f);
    }

    public void _create()
    {
        assets = new AssetManager();
        childs = new ArrayList<GameObject>();
        _loadAssets();

        for(GameObject child : childs) child._create(); //Call create on childs
        create();
    }

    private void _loadAssets()
    {
        assets.load(modelFilepath, Model.class); //load everything we need

        assetsProcessed = false;

        for(GameObject child : childs) child._loadAssets(); //Load child assets
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

    public void _render(ModelBatch modelBatch, Environment environment)
    {
        if( !assets.update() ) return; //If it's still loading...
        else if (!assetsProcessed) _onAssetsLoaded(); //Assets have just loaded, must be processed

        try{ animationController.update(Gdx.graphics.getDeltaTime()); } //Update animations
        catch(Exception e) { /*This is to avoid an app crash due to a libGDX iterators bug :S*/}

        updateTransform();
        for(GameObject child : childs) //Render its childs
            child._render(modelBatch, environment);

        modelBatch.render(modelInstance, environment); //Draw the model instance
        render(modelBatch, environment); //Call the user render method
    }

    public void _update(float deltaTime)
    {
        if(!assetsLoaded()) return;

        for(GameObject child : childs) child._update(deltaTime); //Update childs
        update(deltaTime);
    }

    protected boolean assetsLoaded() { return assetsProcessed; }

    //Child related functions ///////////////////////////////////////////////////
        void addChild(GameObject child) { childs.add(child); }
        void removeChild(GameObject child) { childs.remove(child); }
    /////////////////////////////////////////////////////////////////////////////

    //Overridable functions /////////////////////////////////////////////////////
        protected void create() {}
        protected void loadAssets() {}
        protected void onAssetsLoaded() {}
        protected void update(float deltaTime) {}
        protected void render(ModelBatch modelBatch, Environment environment) {}
    /////////////////////////////////////////////////////////////////////////////

    //Animation functions ///////////////////////////////////////////////////////
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
    /////////////////////////////////////////////////////////////////////////////

    //Animation Event listeners /////////////////////////////////////////////////
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
    /////////////////////////////////////////////////////////////////////////////

    //Transform functions ///////////////////////////////////////////////////////
        //SCALE
        public void setScale(float scale) { this.scale = new Vector3(scale, scale, scale); }
        public void setScale(Vector3 scale) { this.scale = scale; }

        //ROTATE
        public void setRotation(float axisX, float axisY, float axisZ, float degrees)
        {
            rotation.setFromAxis(axisX, axisY, axisZ, degrees);
        }
        public void setRotation(Vector3 axis, float degrees)
        {
            rotation.setFromAxis(axis, degrees);
        }
        public void rotate(float axisX, float axisY, float axisZ, float degrees)
        {
            rotate(new Vector3(axisX, axisY, axisZ), degrees);
        }
        public void rotate(Vector3 axis, float degrees)
        {
            Quaternion rot = new Quaternion();
            rot.setFromAxis(axis, degrees);
            rotation.mul(rot);
        }

        //TRANSLATE
        public void setPosition(float x, float y, float z) { this.position = new Vector3(x,y,z); }
        public void setPosition(Vector3 pos) { this.position = new Vector3(pos.x, pos.y, pos.z); }

        //UPDATE MATRIX
        private void updateTransform()
        {
            modelInstance.transform.idt();
            modelInstance.transform.translate(position.x, position.y, position.z);
            modelInstance.transform.rotate(rotation);
            modelInstance.transform.scale(scale.x, scale.y, scale.z);
        }
    /////////////////////////////////////////////////////////////////////////////
}