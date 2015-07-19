package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;

import anton.fons.bugz.SceneGraph.GameObject;

public class BoardBug extends GameObject
{
    public static final String ModelFilepath1 = "models/bugs/bugForm1.g3dj";
    public static final String ModelFilepath2 = "models/bugs/bugForm2.g3dj";
    public static final String ModelFilepath3 = "models/bugs/bugForm3.g3dj";
    public static final String ModelFilepath4 = "models/bugs/bugForm4.g3dj";

    private Board board;
    private Vector2 tilePosition = new Vector2(0,0);

    public BoardBug(Board parentBoard)
    {
        super(ModelFilepath4);
        board = parentBoard;
    }

    public void setTilePosition(int x, int y)
    {
        tilePosition = new Vector2(x,y);
        setPosition( board.getWorldPositionFromTile( new Vector2(x,y) ) );
    }
    public Vector2 getTilePosition() { return new Vector2(tilePosition.x, tilePosition.y); }

    @Override
    protected void create()
    {
        super.create();
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        setScale(10.0f);
    }
}
