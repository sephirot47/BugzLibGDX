package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import anton.fons.bugz.SceneGraph.GameObject;

public class Board extends GameObject
{
    private BoardPointer pointer;
    private ArrayList< ArrayList<BoardTile> > tiles; //Tiles matrix
    private BoardTile currentTile;

    public final static int BoardWidthInTiles = 8; //In tiles
    public final static int BoardHeightInTiles = 8; //In tiles
    public final static float TileSize = 200.0f / 8; //In I dunno what
    public final static float BoardWidth = BoardWidthInTiles * TileSize;
    public final static float BoardHeight = BoardHeightInTiles * TileSize;

    public Board()
    {
        super("models/board/board.g3dj");

        //Create the BoardTile Matrix ///////////////////
        tiles = new ArrayList< ArrayList<BoardTile> >();
        for(int y = 0; y  < BoardHeightInTiles; ++y)
        {
            ArrayList<BoardTile> tileRow = new ArrayList<BoardTile>();
            for(int x = 0; x < BoardWidthInTiles; ++x)
                tileRow.add(new BoardTile(this, x, y));

            tiles.add(tileRow);
        }
        /////////////////////////////////////////////////

        setCurrentTilePosition(0, 0);
    }

    @Override
    protected void create()
    {
        super.create();
        pointer = new BoardPointer(this);
        setScale(0.02f);
        addChild(pointer);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
    }


    public Vector2 getPointerTilePosition()
    {
        return currentTile.getTilePosition();
    }

    public BoardTile getTile(int x, int y)
    {
        if(outOfBoard(x,y)) return null;
        return tiles.get(y).get(x);
    }
    public BoardTile getTile(Vector2 tilePos) { return getTile((int) tilePos.x, (int) tilePos.y); }

    public void clearAllMarks()
    {
        for(ArrayList<BoardTile> tilesArray : tiles)
            for(BoardTile tile : tilesArray)
                tile.clearMark();
    }

    public Vector3 getWorldPositionFromTile(Vector2 tilePosition)
    {
        float x = tilePosition.x * Board.TileSize - Board.BoardWidth / 2f + Board.TileSize / 2f;
        float z = tilePosition.y * Board.TileSize - Board.BoardHeight / 2f + Board.TileSize / 2f;
        return new Vector3(x, 0f, z);
    }

    public boolean outOfBoard(int x, int y)
    {
        return x < 0 || x >= Board.BoardWidthInTiles || y < 0 || y >= Board.BoardHeightInTiles;
    }

    public void setCurrentTilePosition(int x, int y)
    {
        if(outOfBoard(x,y)) return;
        if(currentTile != null) currentTile.clearMark(); //Clear la marca que hi havia del pointer
        currentTile = getTile(x, y);
        currentTile.setMark(BoardGroundMark.GroundMarkType.Pointer);

        clearAllMarks();
        BoardTile tile;
        tile = getTile(x+1, y); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x+2, y); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x, y+1); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x, y+2); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x, y-1); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x, y-2); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x-1, y); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
        tile = getTile(x-2, y); if(tile != null) tile.setMark(BoardGroundMark.GroundMarkType.Good);
    }

    public void translateCurrentTilePosition(int x, int y)
    {
        if(currentTile == null) return;
        currentTile.clearMark(); //Netejem la marca que hi havia del pointer

        Vector2 currentTilePos = currentTile.getTilePosition();
        currentTilePos.x += x;
        currentTilePos.y += y;
        currentTilePos.x = Math.max(currentTilePos.x, 0);
        currentTilePos.x = Math.min(currentTilePos.x, BoardWidthInTiles - 1);
        currentTilePos.y = Math.max(currentTilePos.y, 0);
        currentTilePos.y = Math.min(currentTilePos.y, BoardHeightInTiles - 1);

        setCurrentTilePosition((int) currentTilePos.x, (int) currentTilePos.y);

    }
}
