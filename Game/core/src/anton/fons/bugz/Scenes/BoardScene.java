package anton.fons.bugz.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import anton.fons.bugz.BoardCanvas;
import anton.fons.bugz.GameObjects.Board;
import anton.fons.bugz.GameObjects.TestCharacter;
import anton.fons.bugz.GameObjects.TestPlanet;

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

        camController = new CameraInputController(getCamera());
        Gdx.input.setInputProcessor(camController);

        canvas = new BoardCanvas();
        setCanvas(canvas); //We set the canvas
    }

    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
        camController.update();
    }
}
