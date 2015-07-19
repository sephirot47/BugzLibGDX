package anton.fons.bugz.Scenes.BoardScenePackage;


import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.DecalGameObject;

public class BoardGroundMark extends DecalGameObject
{
    public static final String TextureFilepath = "models/board/groundMark/groundMarkTexture.png";
    private static final float MarkHeight = 3.5f;
    private float time = 0.0f;

    public BoardGroundMark()
    {
        super(TextureFilepath, 1, 1, true);
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(25.0f);
        setRotation(0, 0, 1, 90);
    }


    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        time += deltaTime;

        Vector3 position = Board.getWorldTilePosition();
        setPosition(position.x, MarkHeight, position.z);

        updateTransform(); //Call this to update decals pos, scale, and rotation
    }
}
