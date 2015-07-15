package anton.fons.bugz;


public class WalkingScene extends Scene
{
    private TestCharacter testCharacter;

    public WalkingScene()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        testCharacter = new TestCharacter();
        gameObjects.add(testCharacter);
    }

    public void onStepDone()
    {
        testCharacter.onStepDone();
    }

}
