package anton.fons.bugz.Scenes;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;

import anton.fons.bugz.BoardCanvas;
import anton.fons.bugz.Canvas;
import anton.fons.bugz.LoadingCanvas;

public class LoadingScene extends Scene
{
    private LoadingCanvas canvas;

    @Override
    protected void create()
    {
        super.create();
        createCanvas();
    }

    private void createCanvas()
    {
        canvas = new LoadingCanvas();
        setCanvas(canvas); //We set the canvas
    }


    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    @Override
    protected void loadAssets()
    {
    }
}
