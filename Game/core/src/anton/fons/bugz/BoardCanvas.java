package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;

public class BoardCanvas extends Canvas
{
    private TextButton btnUp, btnDown, btnLeft, btnRight;

    @Override
    protected void create()
    {
        super.create();

        TextButtonStyle style = new TextButtonStyle();
        style.font = getCanvasFont();

        //Put the buttons
        btnUp = new TextButton("Up", style);       addCanvasElement(btnUp);
        btnDown = new TextButton("Down", style);   addCanvasElement(btnDown);
        btnRight = new TextButton("Right", style); addCanvasElement(btnRight);
        btnLeft = new TextButton("Left", style);   addCanvasElement(btnLeft);
        //
    }
}
