package anton.fons.bugz.GameObjects;

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
        super("models/characters/person.g3db");
    }

    @Override
    public void create()
    {
        setScale(2.0f);
    }

    @Override
    protected void onAssetsLoaded()
    {
        playLoop(IdleAnimationId);
    }

    @Override
    public void update(float deltaTime)
    {
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
        Game.AndroidResolver.log("STEPPPP");
        playLoop(WalkAnimationId);
    }
}
