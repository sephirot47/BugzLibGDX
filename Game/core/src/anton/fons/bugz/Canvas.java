package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;

public abstract class Canvas extends SceneGraphNode
{
    private Stage stage;
    protected BitmapFont font30; //font of size 30

    private static final String FontCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ:-.,!?1234567890" +
                                                 "abcdefghijklmnopqrstuvwxyz";
    private static final String FontFilepath = "fonts/Ubuntu.ttf";

    @Override
    protected void create()
    {
        super.create();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //Generate the bitmapFonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FontFilepath));

        //BitmapFont of size 30
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = new Color(1f, 1f, 1f, 1f);
        parameter.characters = FontCharacters;
        font30 = generator.generateFont(parameter);

        generator.dispose(); // dispose to avoid memory leaks!
    }

    public BitmapFont getCanvasFont()
    {
        return font30;
    }

    public final void addCanvasElement(Actor canvasElement)
    {
        stage.addActor( canvasElement );
    }

    @Override
    protected void postrender(ModelBatch modelBatch, Environment environment)
    {
        super.postrender(modelBatch, environment);

        //Draw the canvas AFTER 3D has been drawn
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
