package anton.fons.bugz.Scenes.BoardScenePackage;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.DecalGameObject;

public class BoardGroundMark extends DecalGameObject
{
    public static final String GroundMarkPointerFilepath = "models/board/groundMark/groundMarkTexture.png";
    public static final String GroundMarkGoodFilepath = "models/board/groundMark/groundMarkGoodTexture.png";
    public static final String GroundMarkBadFilepath = "models/board/groundMark/groundMarkBadTexture.png";
    public static enum GroundMarkType
    {
        Pointer, Good, Bad
    }

    private Vector2 tilePosition;
    private static final float MarkHeight = 3.5f;
    private float time = 0.0f;

    public BoardGroundMark(String textureFilepath)
    {
        super(textureFilepath, 1, 1, true);
        tilePosition = new Vector2();
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(25.5f);
        setRotation(0, 0, 1, 90);
    }

    public void setTilePosition(Vector2 tilePosition, Board board)
    {
        this.tilePosition = new Vector2(tilePosition.x, tilePosition.y);

        Vector3 position = board.getWorldPositionFromTile( tilePosition );
        setPosition(position.x, MarkHeight, position.z);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        time += deltaTime;

        updateTransform();
    }
}
