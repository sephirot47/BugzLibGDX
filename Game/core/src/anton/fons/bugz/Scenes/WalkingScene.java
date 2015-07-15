package anton.fons.bugz.Scenes;


import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import anton.fons.bugz.GameObjects.*;

public class WalkingScene extends Scene
{
    private TestCharacter testCharacter;
    private ArrayList<GameObject> sphereMaps;

    public WalkingScene()
    {
        super();
    }

    @Override
    public void create()
    {
        super.create();

        testCharacter = new TestCharacter();
        addGameObject(testCharacter);

        sphereMaps = new ArrayList<GameObject>(); //Fill up the maps array
        GameObject map0 = new GameObject("models/planets/planet1/planet1.g3dj");
        sphereMaps.add(map0);
        for(GameObject sphereMap : sphereMaps) addGameObject(sphereMap);
    }

    float rot = 1.0f;
    public void update()
    {
        super.update();
        rot += 1.0f;
        /*
        for(GameObject sphereMap : sphereMaps)
        {
            sphereMap.setPosition(0.0f, 0.0f, 0.0f);
            sphereMap.setRotation(1.0f, 0.0f, 0.0f, rot);
            sphereMap.setScale(1.0f);
        }
        */
    }

    public void onStepDone()
    {
        testCharacter.onStepDone();
    }

}
