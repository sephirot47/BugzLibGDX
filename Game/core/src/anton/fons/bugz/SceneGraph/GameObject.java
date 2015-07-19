package anton.fons.bugz.SceneGraph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import anton.fons.bugz.Game;

public class GameObject extends SceneGraphNode implements AnimationController.AnimationListener
{
    private String modelFilepath = "";
    private Model model = null;
    protected ModelInstance modelInstance;
    private AnimationController animationController;

    public GameObject(String modelFilepath)
    {
        super();
        this.modelFilepath = modelFilepath;
    }

    //This will only work if called before create
    protected void setModelBeforeCreate(Model model)
    {
        this.model = model;
    }

    @Override
    protected void create()
    {
        super.create();

        if(this.model == null) //If model != null, it means you want to use a custom model (plane model created by code for example)
            model = Game.getResourceManager().get(modelFilepath, Model.class);

        modelInstance = new ModelInstance(model); //get the model instance
        animationController = new AnimationController(modelInstance); //set the animController
        animationController.allowSameAnimation = false;
    }

    @Override
    protected void prerender(ModelBatch modelBatch, Environment environment)
    {
        super.prerender(modelBatch, environment);
        try{ animationController.update(Gdx.graphics.getDeltaTime()); } //Update animations
        catch(Exception e) { /*This is to avoid an app crash due to a libGDX iterators bug :S*/ }

        updateTransform();
        if(affectedByLight) modelBatch.render(modelInstance, environment); //Draw the model instance
        else modelBatch.render(modelInstance); //Not affected by light (we dont specify environment)
    }
    /////////////////////////////////////////////////////////////////////////////

    //Animation functions ///////////////////////////////////////////////////////
        protected void pauseAnimation()
        {
            animationController.paused = true;
        }

        protected void resumeAnimation()
        {
            animationController.paused = false;
        }

        protected void play(String animationId) { play(animationId, 1, 0.5f); }
        protected void play(String animationId, int loopCount) { play(animationId, loopCount, 0.5f); }
        protected void play(String animationId, float transitionTime) { play(animationId, 1, transitionTime); }

        protected void playLoop(String animationId) { play(animationId, -1, 0.5f); }
        protected void playLoop(String animationId, float transitionTime) { play(animationId, -1, transitionTime); }

        protected void play(String animationId, int loopCount, float transitionTime)
        {
            animationController.paused = false;
            animationController.animate(animationId, loopCount, 1.0f, this, transitionTime);
        }

        protected void stopAnimation()
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
        private void updateTransform()
        {
            modelInstance.transform = getGlobalTransform();
        }
    /////////////////////////////////////////////////////////////////////////////

    public String getModelFilepath() { return modelFilepath; }
}