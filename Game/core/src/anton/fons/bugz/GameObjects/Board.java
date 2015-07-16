package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

public class Board extends GameObject
{

    public Board()
    {
        super("models/board/board.g3dj");
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
    }
}
