package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class TestCharacter extends GameObject
{
    float time = 0.0f;

    public TestCharacter()
    {
        super("data/person.g3db");
    }

    @Override
    protected void onAssetsLoaded()
    {
        playLoop("6000_dummy_idle");
    }

    @Override
    public void update(float deltaTime)
    {
        time += deltaTime;

        if(time > 3.0f && time < 9.0f) { playLoop("6000_dummy_walk", 0.7f); }
        if(time > 10.0f) { playLoop("6000_dummy_idle", 0.7f); }
    }
}
