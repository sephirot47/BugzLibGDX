package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.TimeUtils;

public class Board extends GameObject
{
    private GameObject pointer;
    public final static String PointerModelFilepath = "models/board/pointer/pointer.g3db";

    public Board()
    {
        super("models/board/board.g3dj");
        pointer = new GameObject(PointerModelFilepath);
        addChild(pointer);
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(0.02f);
    }

    @Override
    protected void update(float deltaTime)
    {
       super.update(deltaTime);
       rotate(1f, 1f, 0f, 1);
        pointer.setPosition(0.0f, 100.0f * (float) Math.sin(TimeUtils.millis()*0.002),0.0f);
    }
}
