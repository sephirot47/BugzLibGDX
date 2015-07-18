package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.GameObject;

public class BoardPointer extends GameObject
{
    public static final String ModelFilepath = "models/board/pointer/pointer.g3db";
    private final static float PointerHeight = 30f;
    private final static float WaveAmount = 6f, WaveMovSpeed = 5f;

    private float time = 0.0f;

    public BoardPointer()
    {
        super(ModelFilepath);
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(0.05f);
    }

    public void updatePointerPosition(Vector2 tilePosition)
    {
        Vector3 position = Board.getPositionFromTile(tilePosition);
        setPosition(position.x, PointerHeight, position.z);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        time += deltaTime;

        //Apply the wave movement
        Vector3 lastPosition = getPosition();
        float sinTime = (float) Math.sin(time * WaveMovSpeed);
        Vector3 newPosition = lastPosition.add(0.0f, sinTime * WaveAmount, 0.0f);
        setPosition(newPosition);
    }
}
