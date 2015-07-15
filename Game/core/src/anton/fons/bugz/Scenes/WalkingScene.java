package anton.fons.bugz.Scenes;


import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.*;

public class WalkingScene extends Scene
{
    private TestCharacter testCharacter;
    private TestPlanet testPlanet;

    @Override
    public void create()
    {
        testCharacter = new TestCharacter();
        addGameObject(testCharacter);

        testPlanet = new TestPlanet(testCharacter);
        addGameObject(testPlanet);
    }

    public void update()
    {
    }

    public void onStepDone()
    {
        testCharacter.onStepDone();
    }

}
