package anton.fons.bugz.Scenes.LoadingScenePackage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;

import anton.fons.bugz.SceneGraph.Canvas;
import anton.fons.bugz.SceneGraph.Scene;

public class LoadingCanvas extends Canvas
{
    private Label loadingLabel;

    public LoadingCanvas(Scene parentScene) {
        super(parentScene);
    }

    @Override
    protected void create()
    {
        super.create();

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = getCanvasSmallFont();

        loadingLabel = new Label("Loading Scene", labelStyle);
        addCanvasElement(loadingLabel);
    }
}
