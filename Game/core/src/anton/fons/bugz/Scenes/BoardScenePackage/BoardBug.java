package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.math.Vector2;

import anton.fons.bugz.SceneGraph.GameObject;

public class BoardBug extends GameObject
{
    public static final String ModelFilepath1 = "models/bugs/bugForm1.g3dj";
    public static final String ModelFilepath2 = "models/bugs/bugForm2.g3dj";
    public static final String ModelFilepath3 = "models/bugs/bugForm3.g3dj";
    public static final String ModelFilepath4 = "models/bugs/bugForm4.g3dj";

    private int id;

    //Stats
    private float maxLife, life;
    private float attack, defense;
    private float blockChance;

    private Vector2 tilePosition = new Vector2(0,0);

    public BoardBug(int bugId)
    {
        super(ModelFilepath3);
        this.id = id;

        InitBugStats();
    }

    public void InitBugStats()
    {
        maxLife = 100.0f;
        life = maxLife;
        attack = 5.0f;
        defense = 4.0f;
        blockChance = 0.3f;
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(10.0f);
        setRotation(1, 0, 0, 270);
        //rotate(1,0,0,90);
        //rotate(0,0,0,180);
    }

    @Override
    protected void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void setTilePosition(int x, int y)
    {
        tilePosition = new Vector2(x,y);
        setPosition(Board.getWorldPositionFromTile(new Vector2(x, y)));
        translate(0.0f, 0.0f, 0.0f);
    }
    public Vector2 getTilePosition() { return new Vector2(tilePosition.x, tilePosition.y); }

    public int getId() { return id; }

    public void setTilePosition(Vector2 tilePos) { setTilePosition((int)tilePos.x, (int)tilePos.y); }
}
