package anton.fons.bugz.Canvases;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import anton.fons.bugz.SceneGraph.Canvas;

public class LoadingCanvas extends Canvas
{
    private Label loadingLabel;

    @Override
    protected void create()
    {
        super.create();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = getCanvasFont();

        loadingLabel = new Label("Loading Scene", labelStyle);
        addCanvasElement(loadingLabel);
    }
}
