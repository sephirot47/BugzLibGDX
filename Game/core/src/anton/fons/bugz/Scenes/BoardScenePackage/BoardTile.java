package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;

public class BoardTile
{
    private BoardGroundMark mark;
    private Vector2 tilePosition;
    private Board board;

    public BoardTile(Board parentBoard, int x, int y)
    {
        board = parentBoard;
        tilePosition = new Vector2(x,y);
    }

    //Sets the mark of this tile
    public void setMark(BoardGroundMark.GroundMarkType markType)
    {
        if(mark != null) clearMark();

        mark = new BoardGroundMark(markType);
        mark.setTilePosition( new Vector2(tilePosition.x, tilePosition.y), board );
        board.addChild(mark); //Add it to the board to be rendered
    }

    //Clears the mark
    public void clearMark()
    {
        if(mark == null) return;
        mark._dispose();  //DISPOSE REMOVES THE CHILD FROM BOARD NP
        mark = null;
    }

    public BoardGroundMark getMark()
    {
        return mark;
    }

    public Vector2 getTilePosition()
    {
        return new Vector2(tilePosition.x, tilePosition.y);
    }
}
