package anton.fons.bugz.SceneGraph;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class DecalGameObject extends SceneGraphNode
{
    private String decalTextureFilepath;
    private Decal decal;

    public DecalGameObject(String decalTextureFilepath,
                           int width, int height, boolean hasTransparenvcy)
    {
        super();
        this.decalTextureFilepath = decalTextureFilepath;

        TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal(decalTextureFilepath)));
        decal = Decal.newDecal(width, height, texture, hasTransparenvcy); //Create the decal
    }

    @Override
    protected void create()
    {
        super.create();

        //Once it's a child of a scene, add this decal to that parent scene
        addDecal(decal); //This function goes upwards to the scene in order to add the decal there
    }

    @Override
    protected void dispose()
    {
        super.dispose();
        removeDecal(decal); //This function goes upwards to the scene in order to remove the decal from its scene
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        updateTransform();
    }

    /////////////////////////////////////////////////////////////////////////////

    //Transform functions ///////////////////////////////////////////////////////
    protected void updateTransform()
    {
        Vector3 translation = getGlobalPosition();
        Vector3 scale = getGlobalScale();
        Quaternion rotation = getGlobalRotation();

        decal.setPosition(translation);
        decal.setRotation(rotation.getYaw(), rotation.getPitch(), rotation.getRoll());
        decal.setScale(scale.x, scale.y);
    }
    /////////////////////////////////////////////////////////////////////////////

    public Decal getDecal() { return decal; }
    public String getDecalTextureFilepath() { return decalTextureFilepath; }
}