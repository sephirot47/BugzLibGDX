package anton.fons.bugz.Scenes.WalkingScenePackage;

import anton.fons.bugz.SceneGraph.GameObject;

public class Planet extends GameObject
{
    private float rotSpeed = 10.0f;
    private WalkingBug character;

    public Planet(WalkingBug character)
    {
        super("models/planets/planet0/planet0.g3dj");
        this.character = character;
    }

    @Override
    public void create()
    {
        super.create();
        setPosition(0, -5.0f, 0);
        setRotation(1.0f, 0.0f, 0.0f, 90.0f);
        rotate(0.0f, 0.0f, 1.0f, 90.0f);
        setScale(5.0f);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        if(character.walking)
            rotate(0.0f, 1.0f, 0.0f, rotSpeed * deltaTime);
    }
}
