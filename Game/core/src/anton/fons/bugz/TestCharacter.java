package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class TestCharacter extends GameObject
{
    public TestCharacter()
    {
        super("data/person.g3db");
    }

    @Override
    protected void onAssetsLoaded()
    {
        super.onAssetsLoaded();
        animationController.setAnimation("6000_dummy_walk", -1);
    }

    @Override
    public void render(Environment environment, Camera gameCamera)
    {
        super.render(environment, gameCamera);
    }
}
