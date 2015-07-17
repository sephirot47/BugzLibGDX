package anton.fons.bugz.Scenes;

import anton.fons.bugz.Canvases.LoadingCanvas;
import anton.fons.bugz.SceneGraph.Scene;

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
