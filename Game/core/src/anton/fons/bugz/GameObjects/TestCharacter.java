package anton.fons.bugz.GameObjects;

import anton.fons.bugz.SceneGraph.GameObject;

public class TestCharacter extends GameObject
{

    /**It's the time that the user has to make another step before the character goes idle**/
    private static final float TimeWaitBetweenSteps = 2.0f;
    float timeSinceLastStep = 0.0f;
    public boolean walking = false;
    private boolean created = false;

    private static final String IdleAnimationId = "6000_dummy_idle";
    private static final String WalkAnimationId = "6000_dummy_walk";

    public TestCharacter()
    {
        super("models/characters/person.g3db");
    }


    @Override
    protected void create()
    {
        super.create();
        setScale(2.0f);
        created = true;
        playLoop(IdleAnimationId);
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
        if(!created) return;

        timeSinceLastStep = 0.0f;
        walking = true;
        playLoop(WalkAnimationId);
    }
}
