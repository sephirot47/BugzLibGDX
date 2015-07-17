package anton.fons.bugz.Scenes;


import com.badlogic.gdx.graphics.g3d.Model;

import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.TestCharacter;
import anton.fons.bugz.GameObjects.TestPlanet;
import anton.fons.bugz.SceneGraph.Scene;

public class WalkingScene extends Scene
{
    private TestCharacter testCharacter;
    private TestPlanet testPlanet;

    @Override
    protected void create()
    {
        super.create();

        testCharacter = new TestCharacter();
        addChild(testCharacter);

        testCharacter.setPosition(0.2f, 0.2f, 0.2f);

        testPlanet = new TestPlanet(testCharacter);
        addChild(testPlanet);
    }

    @Override
    public void loadAssets()
    {
        //load everything we need
        Game.getResourceManager().load(testCharacter.getModelFilepath(), Model.class, this);
        Game.getResourceManager().load(testPlanet.getModelFilepath(), Model.class, this);
    }

    public void onStepDone()
    {
        testCharacter.onStepDone();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }
}
