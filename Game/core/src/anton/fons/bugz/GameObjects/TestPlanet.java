package anton.fons.bugz.GameObjects;

public class TestPlanet extends GameObject
{
    private float rotSpeed = 10.0f;
    private TestCharacter character;

    public TestPlanet(TestCharacter character)
    {
        super("models/planets/planet0/planet0.g3dj");
        this.character = character;
    }

    @Override
    public void create()
    {
        setPosition(0, -5.0f, 0);
        setRotation(1.0f, 0.0f, 0.0f, 90.0f);
        rotate(0.0f, 0.0f, 1.0f, 90.0f);
        setScale(5.0f);
    }

    @Override
    public void update(float deltaTime)
    {
        if(character.walking)
            rotate(0.0f, 1.0f, 0.0f, rotSpeed * deltaTime);
    }

    public void onStepDone()
    {
        if(!assetsLoaded()) return;
    }
}
