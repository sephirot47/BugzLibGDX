package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.GameObject;

public class BoardPointer extends GameObject
{
    private Board board;

    public static final String ModelFilepath = "models/board/pointer/pointer.g3db";
    private final static float PointerHeight = 30f;
    private final static float WaveAmount = 6f, WaveMovSpeed = 5f;

    private float time = 0.0f;

    public BoardPointer(Board parentBoard)
    {
        super(ModelFilepath);
        board = parentBoard;
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(0.05f);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        time += deltaTime;

        Vector3 position = board.getWorldPositionFromTile(board.getPointerTilePosition() );
        setPosition(position.x, PointerHeight, position.z);

        //Apply the wave movement
        Vector3 lastPosition = getPosition();
        float sinTime = (float) Math.sin(time * WaveMovSpeed);
        Vector3 newPosition = lastPosition.add(0.0f, sinTime * WaveAmount, 0.0f);
        setPosition(newPosition);
    }
}
