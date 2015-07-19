package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import anton.fons.bugz.Game;
import anton.fons.bugz.SceneGraph.Scene;

public class BoardScene extends Scene
{
    private CameraInputController camController;

    private Board board;
    private BoardCanvas canvas;

    @Override
    protected void create()
    {
        super.create();

        board = new Board();
        canvas = new BoardCanvas(board);

        //Set camera
        Camera cam = getCamera();
        cam.position.x = 0f;
        cam.position.y = 4f;
        cam.position.z = 5.5f;

        cam.lookAt( 0f, 0f, 0f );
        cam.update();
    }

    @Override
    public void loadAssets()
    {
        //load everything we need
        Game.getResourceManager().load(board.getModelFilepath(), Model.class, this);
        Game.getResourceManager().load(BoardPointer.ModelFilepath, Model.class, this);

        Game.getResourceManager().load(BoardBug.ModelFilepath1, Model.class, this);
        Game.getResourceManager().load(BoardBug.ModelFilepath2, Model.class, this);
        Game.getResourceManager().load(BoardBug.ModelFilepath3, Model.class, this);
        Game.getResourceManager().load(BoardBug.ModelFilepath4, Model.class, this);

        //Game.getResourceManager().load(BoardGroundMark.ModelFilepath, Model.class, this);
    }

    @Override
    protected void prerender(ModelBatch modelBatch, Environment environment)
    {
        super.prerender(modelBatch, environment);
        Gdx.gl.glClearColor(0.2f, 0.4f, 1.0f, 1.0f); //Clear with a blue
    }

    @Override
    public void onAssetsLoaded()
    {
        super.onAssetsLoaded();
        setCanvas(canvas);
        addChild(board);
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
    }
}
