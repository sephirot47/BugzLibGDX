package anton.fons.bugz.GameObjects;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import anton.fons.bugz.Game;

public class TestCharacter extends GameObject
{

    /**It's the time that the user has to make another step before the character goes idle**/
    private static final float TimeWaitBetweenSteps = 2.0f;
    float timeSinceLastStep = 0.0f;
    public boolean walking = false;

    private static final String IdleAnimationId = "6000_dummy_idle";
    private static final String WalkAnimationId = "6000_dummy_walk";

    public TestCharacter()
    {
        super("models/characters/person.g3dj");
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(2.0f);
    }

    @Override
    protected void onAssetsLoaded()
    {
        super.onAssetsLoaded();
        playLoop(IdleAnimationId);
    }


    @Override
    protected void prerender(ModelBatch modelBatch, Environment environment)
    {
        super.prerender(modelBatch, environment);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);

        timeSinceLastStep += deltaTime;
        if(timeSinceLastStep >= TimeWaitBetweenSteps)
        {
            walking = false;
            playLoop(IdleAnimationId); //Go to idle
        }
    }

    public void onStepDone()
    {
        if(!assetsLoaded()) return;

        timeSinceLastStep = 0.0f;
        walking = true;
        playLoop(WalkAnimationId);
    }
}
