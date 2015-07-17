package anton.fons.bugz;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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
