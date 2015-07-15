package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import anton.fons.bugz.Game;
import anton.fons.bugz.SceneGraphNode;
import anton.fons.bugz.Scenes.Scene;

public class GameObject extends SceneGraphNode implements AnimationController.AnimationListener
{
    private String modelFilepath;
    protected ModelInstance modelInstance;
    private AnimationController animationController;

    public GameObject(String modelFilepath)
    {
        super();
        this.modelFilepath = modelFilepath;
    }

    @Override
    protected void loadAssets()
    {
        super.loadAssets();

        getAssetsManager().load(modelFilepath, Model.class); //load everything we need
    }

    @Override
    protected void onAssetsLoaded()
    {
        super.onAssetsLoaded();
        Model model = getAssetsManager().get(modelFilepath, Model.class);
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
        modelBatch.render(modelInstance, environment); //Draw the model instance
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
            modelInstance.transform = getTransformMatrix();
        }
    /////////////////////////////////////////////////////////////////////////////
}