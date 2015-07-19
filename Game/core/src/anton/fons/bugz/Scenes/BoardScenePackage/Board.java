package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.GameObject;

public class Board extends GameObject
{
    private BoardPointer pointer;
    private BoardGroundMark groundMark;

    public final static int BoardWidthInTiles = 8; //In tiles
    public final static int BoardHeightInTiles = 8; //In tiles
    public final static float TileSize = 200.0f / 8; //In I dunno what
    public final static float BoardWidth = BoardWidthInTiles * TileSize;
    public final static float BoardHeight = BoardHeightInTiles * TileSize;

    private static Vector2 pointerTilePosition;

    public Board()
    {
        super("models/board/board.g3dj");

        pointerTilePosition = new Vector2(7,7);

        pointer = new BoardPointer();
        groundMark = new BoardGroundMark();

        addChild(pointer);
        addChild(groundMark);
    }

    public static Vector2 getTilePosition()
    {
        return pointerTilePosition;
    }

    public static Vector3 getWorldTilePosition()
    {
        return getPositionFromTile(pointerTilePosition);
    }

    public static Vector3 getPositionFromTile( Vector2 tilePosition )
    {
        float x = tilePosition.x * Board.TileSize - Board.BoardWidth / 2f + Board.TileSize / 2f;
        float z = tilePosition.y * Board.TileSize - Board.BoardHeight / 2f + Board.TileSize / 2f;
        return new Vector3(x, 0f, z);
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
        //rotate(1,1,1,1);
    }

    public void translatePointerTilePosition(int x, int y)
    {
        pointerTilePosition.x += x;
        pointerTilePosition.y += y;
        pointerTilePosition.x = Math.max(pointerTilePosition.x, 0);
        pointerTilePosition.x = Math.min(pointerTilePosition.x, BoardWidthInTiles - 1);
        pointerTilePosition.y = Math.max(pointerTilePosition.y, 0);
        pointerTilePosition.y = Math.min(pointerTilePosition.y, BoardHeightInTiles - 1);
    }
}
