package anton.fons.bugz.Scenes.LoadingScenePackage;

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
        canvas = new LoadingCanvas(this);
        setCanvas(canvas); //We set the canvas
    }


    @Override
    protected void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    @Override
    public void loadAssets()
    {
    }
}
