package anton.fons.bugz.Scenes;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import anton.fons.bugz.BoardCanvas;
import anton.fons.bugz.Game;
import anton.fons.bugz.GameObjects.*;

public class WalkingScene extends Scene
{
    private TestCharacter testCharacter;//, testChild;
    private TestPlanet testPlanet;

    private BoardCanvas canvas;

    @Override
    protected void create()
    {
        super.create();

        testCharacter = new TestCharacter();
        addChild(testCharacter);

        testCharacter.setPosition(0.2f,0.2f,0.2f);

        //testChild = new TestCharacter();
        //testCharacter.addChild(testChild);

        //testChild.setPosition(0.6f,0.6f,0.6f);

        testPlanet = new TestPlanet(testCharacter);
        addChild(testPlanet);

        canvas = new BoardCanvas();
        setCanvas(canvas); //We set the canvas
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
        testCharacter.rotate(1.0f,0.0f,0.0f,5.0f);
    }
}
