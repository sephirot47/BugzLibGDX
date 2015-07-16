package anton.fons.bugz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;

public class Canvas extends SceneGraphNode
{
    private Stage stage;
    private BitmapFont font30; //font of size 30

    private TextButton button1;

    private static final String FontCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ:-.,!?1234567890" +
                                                 "abcdefghijklmnopqrstuvwxyz";
    private static final String FontFilepath = "fonts/Ubuntu.ttf";

    @Override
    protected void loadAssets()
    {
        super.loadAssets();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //Generate the bitmapFonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FontFilepath));

            //BitmapFont of size 30
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = 30;
            parameter.characters = FontCharacters;
            font30 = generator.generateFont(parameter);

        generator.dispose(); // dispose to avoid memory leaks!


        //Buttons
            TextButtonStyle style = new TextButtonStyle();
            style.font = font30;
            button1 = new TextButton("Hiiiii :D", style);
            stage.addActor(button1);
        //
    }

    @Override
    protected void onAssetsLoaded()
    {
        super.onAssetsLoaded();
    }

    @Override
    protected void postrender(ModelBatch modelBatch, Environment environment)
    {
        //Draw the canvas AFTER 3D has been drawn
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
