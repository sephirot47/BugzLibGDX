package anton.fons.bugz.Scenes.BoardScenePackage;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.GameObject;

public class BoardGroundMark extends GameObject
{
    private static final String GroundMarkPointerFilepath = "models/board/groundMark/groundMarkTexture.png";
    private static final String GroundMarkGoodFilepath = "models/board/groundMark/groundMarkGoodTexture.png";
    private static final String GroundMarkBadFilepath = "models/board/groundMark/groundMarkBadTexture.png";
    public static enum GroundMarkType
    {
        Pointer, Good, Bad
    }

    public static final Texture GroundMarkPointerTexture =  new Texture(Gdx.files.internal(GroundMarkPointerFilepath));
    public static final Texture GroundMarkGoodTexture =  new Texture(Gdx.files.internal(GroundMarkGoodFilepath));
    public static final Texture GroundMarkBadTexture =  new Texture(Gdx.files.internal(GroundMarkBadFilepath));

    private Vector2 tilePosition;
    private static final float MarkHeight = 3.3f;
    private float time = 0.0f;

    public BoardGroundMark(GroundMarkType markType)
    {
        super("");

        Texture texture;
        if(markType == BoardGroundMark.GroundMarkType.Pointer)  texture = BoardGroundMark.GroundMarkPointerTexture;
        else if(markType == BoardGroundMark.GroundMarkType.Good)  texture = BoardGroundMark.GroundMarkGoodTexture;
        else texture = BoardGroundMark.GroundMarkBadTexture;

        //Create the model Plane
        Material material = new Material();
        Model planeModel = createPlaneModel(1, 1, material, 0f, 0f, 1f, 1f);
        material.set(new TextureAttribute(TextureAttribute.Diffuse, texture));
        material.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        //

        this.affectedByLight = false;
        setModelBeforeCreate(planeModel);

        tilePosition = new Vector2();
    }

    @Override
    protected void create()
    {
        super.create();
        setScale(25.5f);
        setRotation(1, 0, 0, 270);
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
    }

    //Builds a 3d Plane :)
    private Model createPlaneModel(final float width, final float height, final Material material,
                                   final float u1, final float v1, final float u2, final float v2)
    {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder bPartBuilder = modelBuilder.part("rect",
                GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates,
                material);

        //NOTE ON TEXTURE REGION, MAY FILL OTHER REGIONS, USE GET region.getU() and so on
        bPartBuilder.setUVRange(u1, v1, u2, v2);
        bPartBuilder.rect(
                -(width * 0.5f), -(height * 0.5f), 0,
                (width * 0.5f), -(height * 0.5f), 0,
                (width * 0.5f), (height * 0.5f), 0,
                -(width * 0.5f), (height * 0.5f), 0,
                0, 0, -1);

        return (modelBuilder.end());
    }
}
