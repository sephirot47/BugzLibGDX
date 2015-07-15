package anton.fons.bugz.Scenes;


public class WalkingScene extends Scene
{
    private anton.fons.bugz.GameObjects.TestCharacter testCharacter;

    public WalkingScene()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        testCharacter = new anton.fons.bugz.GameObjects.TestCharacter();
        gameObjects.add(testCharacter);
    }

    public void onStepDone()
    {
        testCharacter.onStepDone();
    }

}
