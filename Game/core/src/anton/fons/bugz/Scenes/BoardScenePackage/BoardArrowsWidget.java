package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class BoardArrowsWidget extends Group
{
    private BoardCanvas canvas;

    private static Texture arrowTexture = null;
    private ImageButton btnUp, btnDown, btnLeft, btnRight;

    public enum Arrow { Up, Down, Right, Left }

    public BoardArrowsWidget(BoardCanvas canvas)
    {
        this.canvas = canvas;
    }

    public void init()
    {
        if(arrowTexture == null) arrowTexture = new Texture(Gdx.files.internal(BoardCanvas.ArrowTexturePath));
        TextureRegion image = new TextureRegion(arrowTexture);

        //ARROW BUTTONS
        ImageButton.ImageButtonStyle arrowButtonsStyle = new ImageButton.ImageButtonStyle();
        arrowButtonsStyle.imageUp = new TextureRegionDrawable(image);
        arrowButtonsStyle.imageDown = new TextureRegionDrawable(image);
        ImageButton iconButton = new ImageButton(arrowButtonsStyle);

        //Put the buttons
        btnUp = new ImageButton(arrowButtonsStyle);
        btnDown = new ImageButton(arrowButtonsStyle);
        btnRight = new ImageButton(arrowButtonsStyle);
        btnLeft = new ImageButton(arrowButtonsStyle);
        btnUp.setTransform(true); btnRight.setTransform(true);
        btnDown.setTransform(true); btnLeft.setTransform(true);
        //

        btnUp.setOrigin(Align.center); btnRight.setOrigin(Align.center);
        btnDown.setOrigin(Align.center); btnLeft.setOrigin(Align.center);

        btnRight.setRotation(270); btnDown.setRotation(180); btnLeft.setRotation(90);

        setOrigin(Align.center);

        float ArrowCrossSize = 150;
        btnUp.setPosition(0, ArrowCrossSize);
        btnDown.setPosition(0, -ArrowCrossSize);
        btnRight.setPosition(ArrowCrossSize, 0);
        btnLeft.setPosition(-ArrowCrossSize, 0);

        addActor(btnUp);
        addActor(btnDown);
        addActor(btnRight);
        addActor(btnLeft);

        btnUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onArrowButtonPressed(Arrow.Up);
            }
        });
        btnDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onArrowButtonPressed(Arrow.Down);
            }
        });
        btnRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onArrowButtonPressed(Arrow.Right);
            }
        });
        btnLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onArrowButtonPressed(Arrow.Left);
            }
        });
    }

    public void show() { setVisible(true); }
    public void hide() { setVisible(false); }
}

