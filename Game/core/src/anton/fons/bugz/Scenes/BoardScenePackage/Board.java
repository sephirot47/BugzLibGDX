package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.GameObject;

public class Board extends GameObject
{
    private BoardGameController gameController;
    private BoardTileManager tileManager;

    private BoardPointer pointer;
    private BoardActionDialogWidget dialogWidget;

    public final static int BoardWidthInTiles = 8; //In tiles
    public final static int BoardHeightInTiles = 8; //In tiles
    public final static float TileSize = 200.0f / 8; //In I dunno what
    public final static float BoardWidth = BoardWidthInTiles * TileSize;
    public final static float BoardHeight = BoardHeightInTiles * TileSize;

    public Board()
    {
        super("models/board/board.g3dj");

        gameController = new BoardGameController(this);
        tileManager = new BoardTileManager(this);
    }

    @Override
    protected void create()
    {
        super.create();
        pointer = new BoardPointer(this);
        setScale(0.02f);
        addChild(pointer);

        tileManager.createBug(new Vector2(3, 3));
        tileManager.createBug(new Vector2(7, 4));
        tileManager.createBug(new Vector2(3, 5));
        tileManager.createBug(new Vector2(1, 2));

        setCurrentTilePosition(0, 0);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    public Vector2 getCurrentTilePosition()
    {
        return tileManager.getCurrentTilePosition();
    }

    public static Vector3 getWorldPositionFromTile(Vector2 tilePosition)
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
        tileManager.clearAllMarks(); //Clear la marca que hi havia del pointer

        Vector2 tilePos = new Vector2(x,y);
        tileManager.setMark(tilePos, BoardGroundMark.GroundMarkType.Pointer);

        if(tileManager.tileEmpty(tilePos))
        {
            dialogWidget.hide();
        }
        else
        {
            dialogWidget.show();
        }
    }

    public void translateCurrentTilePosition(int x, int y)
    {
        Vector2 currentTilePos = getCurrentTilePosition();
        currentTilePos.x += x; currentTilePos.x = Math.max(currentTilePos.x, 0);
        currentTilePos.y += y; currentTilePos.y = Math.max(currentTilePos.y, 0);
        currentTilePos.x = Math.min(currentTilePos.x, BoardWidthInTiles - 1);
        currentTilePos.y = Math.min(currentTilePos.y, BoardHeightInTiles - 1);
        setCurrentTilePosition((int) currentTilePos.x, (int) currentTilePos.y);
    }

    public void setDialogWidget(BoardActionDialogWidget dialogWidget) { this.dialogWidget = dialogWidget; }
    public BoardActionDialogWidget getDialogWidget() { return dialogWidget; }

    public BoardTileManager getTileManager() { return tileManager; }
}
