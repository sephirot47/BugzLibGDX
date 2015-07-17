package anton.fons.bugz.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import anton.fons.bugz.Canvases.BoardCanvas;
import anton.fons.bugz.Game;
import anton.fons.bugz.SceneGraph.Scene;
import anton.fons.bugz.GameObjects.Board;

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
        addChild(board);
        board.setPosition(1.0f, 0.0f, 0.0f);

        camController = new CameraInputController(getCamera());
        Gdx.input.setInputProcessor(camController);

        canvas = new BoardCanvas();
        setCanvas(canvas); //We set the canvas
    }

    @Override
    public void loadAssets()
    {
        //load everything we need
        Game.getResourceManager().load(board.getModelFilepath(), Model.class, this);
        Game.getResourceManager().load(Board.PointerModelFilepath, Model.class, this);
    }


    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        camController.update();
    }
}
