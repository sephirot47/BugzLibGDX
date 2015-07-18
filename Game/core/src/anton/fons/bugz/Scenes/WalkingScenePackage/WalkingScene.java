package anton.fons.bugz.Scenes.WalkingScenePackage;


import com.badlogic.gdx.graphics.g3d.Model;

import anton.fons.bugz.Game;
import anton.fons.bugz.SceneGraph.Scene;

public class WalkingScene extends Scene
{
    private WalkingBug walkingBug;
    private Planet planet;

    @Override
    protected void create()
    {
        super.create();

        walkingBug = new WalkingBug();
        addChild(walkingBug);

        walkingBug.setPosition(0.2f, 0.2f, 0.2f);

        planet = new Planet(walkingBug);
        addChild(planet);
    }

    @Override
    public void loadAssets()
    {
        //load everything we need
        Game.getResourceManager().load(walkingBug.getModelFilepath(), Model.class, this);
        Game.getResourceManager().load(planet.getModelFilepath(), Model.class, this);
    }

    public void onStepDone()
    {
        walkingBug.onStepDone();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }
}
