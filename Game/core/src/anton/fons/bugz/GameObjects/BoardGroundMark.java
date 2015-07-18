package anton.fons.bugz.GameObjects;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import anton.fons.bugz.SceneGraph.GameObject;

public class BoardGroundMark extends GameObject
{
    public static final String ModelFilepath = "models/board/groundMark/groundMark.g3dj";
    private static final float MarkHeight = 3.5f;
    private float time = 0.0f;

    public BoardGroundMark()
    {
        super(ModelFilepath);
    }

    @Override
    protected void create()
    {
        super.create();
        setRotation(1f, 0f, 0f, 270);
        setScale(12.0f);

        //This wokkks!
        modelInstance.materials.get(0).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
    }

    public void updateMarkPosition(Vector2 tilePosition)
    {
        Vector3 position = Board.getPositionFromTile(tilePosition);
        setPosition(position.x, MarkHeight, position.z);
    }

    @Override
    protected void prerender(ModelBatch modelBatch, Environment env)
    {
        super.prerender(modelBatch, env);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        time += deltaTime;
    }
}
