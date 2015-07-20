package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class BoardTileManager
{
    private static int bugsId = 0;

    private Board board;

    private ArrayList<ArrayList<BoardTile>> tiles; //Tiles matrix

    private Vector2 currentTilePosition;

    public BoardTileManager(Board parentBoard)
    {
        board = parentBoard;

        //Create the BoardTile Matrix ///////////////////
        tiles = new ArrayList<ArrayList<BoardTile>>();
        for(int y = 0; y  < Board.BoardHeightInTiles; ++y)
        {
            ArrayList<BoardTile> tileRow = new ArrayList<BoardTile>();
            for(int x = 0; x < Board.BoardWidthInTiles; ++x)
                tileRow.add(new BoardTile(board, x, y));

            tiles.add(tileRow);
        }
        /////////////////////////////////////////////////

        currentTilePosition = new Vector2();
    }

    //TILE FUNCTIONS
    /////////////////////////////////////////////////////////////////////
    private BoardTile getTile(int x, int y)
    {
        if(board.outOfBoard(x,y)) return null;
        return tiles.get(y).get(x);
    }
    private BoardTile getTile(Vector2 tilePos) { return getTile((int) tilePos.x, (int) tilePos.y); }

    public Vector2 getCurrentTilePosition() { return currentTilePosition; }
    public boolean tileEmpty(Vector2 tilePosition)
    {
        BoardTile tile = getTile(tilePosition);
        return (tile == null) ? true : (tile.getBug() == null);
    }

    private BoardTile getBugTile(BoardBug bug)
    {
        return getTile( bug.getTilePosition() );
    }
    /////////////////////////////////////////////////////////////////////

    // MARK FUNCTIONS
    /////////////////////////////////////////////////////////////////////
    public void setMark(Vector2 tilePosition, BoardGroundMark.GroundMarkType markType)
    {
        BoardTile tile = getTile(tilePosition);
        if(tile != null) tile.setMark(markType);
    }

    public void clearMark(Vector2 tilePosition)
    {
        BoardTile tile = getTile(tilePosition);
        if(tile != null) tile.clearMark();
    }

    public void clearAllMarks()
    {
        for(ArrayList<BoardTile> tilesArray : tiles)
            for(BoardTile tile : tilesArray)
                tile.clearMark();
    }
    /////////////////////////////////////////////////////////////////////

    //BUG FUNCTIONS
    /////////////////////////////////////////////////////////////////////
    public void createBug(Vector2 tilePosition)
    {
        BoardTile tile = getTile(tilePosition);
        if(tile != null)
        {
            BoardBug bug = new BoardBug(bugsId);
            ++bugsId;

            bug.setTilePosition(tilePosition);
            tile.setBug(bug);
            board.addChild(bug);
        }
    }

    public void moveBug(int bugId, Vector2 newTilePosition)
    {
        BoardBug bug = getBugById(bugId);
        if(bug == null) return;

        BoardTile lastTile = getBugTile(bug);
        BoardTile newTile = getTile(newTilePosition);
        if(lastTile != null && newTile != null)
        {
            lastTile.setBug(null);
            newTile.setBug(bug);

            bug.setTilePosition(newTilePosition);
        }
    }

    public void removeBug(int bugId)
    {
        BoardBug bug = getBugById(bugId);
        if(bug == null) return;

        BoardTile tile = getBugTile(bug);
        if(tile != null)
        {
            tile.setBug(null);
            board.removeChild(bug);
        }
    }

    public BoardBug getBugById(int bugId)
    {
        for(int i = 0; i < tiles.size(); ++i)
        {
            for (int j = 0; j < tiles.get(i).size(); ++j)
            {
                BoardTile tile = getTile(i,j);
                BoardBug bug = tile.getBug();
                if( bug != null && bug.getId() == bugId) return bug;
            }
        }
        return null;
    }
    /////////////////////////////////////////////////////////////////////

    // ACTION FUNCTIONS
    /////////////////////////////////////////////////////////////////////
    public void takeAction(BoardGameController.BoardBugAction action)
    {

    }
    /////////////////////////////////////////////////////////////////////
}
