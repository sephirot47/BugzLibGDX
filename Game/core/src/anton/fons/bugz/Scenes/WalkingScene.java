package anton.fons.bugz.Scenes;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import anton.fons.bugz.BoardCanvas;
import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.*;

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

        testCharacter.setPosition(0.2f,0.2f,0.2f);

        testPlanet = new TestPlanet(testCharacter);
        addChild(testPlanet);
    }

    @Override
    protected void loadAssets()
    {
        //load everything we need
        getAssetsManager().load(testCharacter.getModelFilepath(), Model.class);
        getAssetsManager().load(testPlanet.getModelFilepath(), Model.class);
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
