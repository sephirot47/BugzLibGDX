package anton.fons.bugz.SceneGraph;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class Canvas extends SceneGraphNode
{
    private Stage stage;
    protected BitmapFont smallFont, mediumFont, bigFont;
    private Scene parentScene;

    private static final String FontCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ:-.,!?1234567890" +
                                                 "abcdefghijklmnopqrstuvwxyz";
    private static final String FontFilepath = "fonts/Ubuntu.ttf";

    public Canvas(Scene parentScene)
    {
        super();
        this.parentScene = parentScene;
    }

    @Override
    protected void create()
    {
        super.create();

        stage = new Stage(new FitViewport(2000, 1200));
        Gdx.input.setInputProcessor(stage);

        //Generate the bitmapFonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FontFilepath));

        //BitmapFont of size 30
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = new Color(1f, 1f, 1f, 1f);
        parameter.characters = FontCharacters;
        smallFont = generator.generateFont(parameter);
        parameter.size = 60;
        mediumFont = generator.generateFont(parameter);
        parameter.size = 90;
        bigFont = generator.generateFont(parameter);

        generator.dispose(); // dispose to avoid memory leaks!
    }

    public BitmapFont getCanvasSmallFont() { return smallFont; }
    public BitmapFont getCanvasMediumFont() { return mediumFont; }
    public BitmapFont getCanvasBigFont() { return bigFont; }

    public final void addCanvasElement(Actor canvasElement)
    {
        stage.addActor( canvasElement );
    }

    @Override
    protected void postrender(ModelBatch modelBatch, Environment environment) {
        super.postrender(modelBatch, environment);

        //Draw the canvas AFTER 3D has been drawn
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
     //   parentScene.getViewport().apply();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }
}
