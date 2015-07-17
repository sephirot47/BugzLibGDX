package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.math.Vector2;

import anton.fons.bugz.SceneGraph.GameObject;

public class Board extends GameObject
{
    private BoardPointer pointer;

    public final static int BoardWidthInTiles = 8; //In tiles
    public final static int BoardHeightInTiles = 8; //In tiles
    public final static float TileSize = 200.0f / 8; //In I dunno what
    public final static float BoardWidth = BoardWidthInTiles * TileSize;
    public final static float BoardHeight = BoardHeightInTiles * TileSize;

    private final Vector2 pointerTilePosition;

    public Board()
    {
        super("models/board/board.g3dj");

        pointerTilePosition = new Vector2(3,3);

        pointer = new BoardPointer();
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
        pointer.updatePointerPosition(pointerTilePosition);
    }
}
