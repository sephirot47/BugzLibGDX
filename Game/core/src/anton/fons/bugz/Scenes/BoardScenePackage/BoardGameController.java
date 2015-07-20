package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;

public class BoardGameController
{
    public enum BoardBugActionType
    {
        Move, Attack, Defense, Skill, Nothing
    }

    public class BoardBugAction
    {
        public int from, to;
        public String stringExtra1, stringExtra2;
        public Vector2 fromTilePos, toTilePos;
        public int intExtra1, intExtra2;
        public float floatExtra1, floatExtra2;
        public BoardBugActionType type;

        public BoardBugAction() {}
    }

    private Board board;

    public BoardGameController(Board parentBoard)
    {
        board = parentBoard;
    }

    public void endTurn()
    {

    }

    public void takeAction(BoardBugAction action)
    {
        board.getTileManager().takeAction(action);
    }
}
