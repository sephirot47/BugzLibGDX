package anton.fons.bugz.Scenes;


import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.*;

public class WalkingScene extends Scene
{
    private TestCharacter testCharacter;
    private TestPlanet testPlanet;

    public WalkingScene()
    {
        super();
    }

    @Override
    protected void create()
    {
        super.create();

        testCharacter = new TestCharacter();
        addChild(testCharacter);

        testPlanet = new TestPlanet(testCharacter);
        addChild(testPlanet);
    }

    public void onStepDone()
    {
        testCharacter.onStepDone();
    }
}
