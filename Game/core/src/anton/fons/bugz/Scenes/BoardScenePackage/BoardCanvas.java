package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import anton.fons.bugz.SceneGraph.Canvas;

public class BoardCanvas extends Canvas
{
    public final static String ArrowTexturePath = "canvas/board/arrow.png";
    Texture arrowTexture;
    Group arrowButtonsGroup;
    private ImageButton btnUp, btnDown, btnLeft, btnRight;
    private Board board;

    public BoardCanvas(Board board)
    {
        this.board = board;
    }

    @Override
    protected void create()
    {
        super.create();

        arrowTexture = new Texture(Gdx.files.internal(ArrowTexturePath));
        TextureRegion image = new TextureRegion(arrowTexture);

        //ARROW BUTTONS
        ImageButtonStyle arrowButtonsStyle = new ImageButtonStyle();
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

        arrowButtonsGroup = new Group();
        arrowButtonsGroup.setPosition(200, 200);
        arrowButtonsGroup.setOrigin(Align.center);

        float ArrowCrossSize = 150;
        btnUp.setPosition(0, ArrowCrossSize);
        btnDown.setPosition(0, -ArrowCrossSize);
        btnRight.setPosition(ArrowCrossSize, 0);
        btnLeft.setPosition(-ArrowCrossSize, 0);

        arrowButtonsGroup.addActor(btnUp);
        arrowButtonsGroup.addActor(btnDown);
        arrowButtonsGroup.addActor(btnRight);
        arrowButtonsGroup.addActor(btnLeft);

        btnUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onArrowButtonPressed(btnUp);
            }
        });
        btnDown.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y){
                onArrowButtonPressed(btnDown);
            }
        });
        btnRight.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y){
                onArrowButtonPressed(btnRight);
            }
        });
        btnLeft.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y){
                onArrowButtonPressed(btnLeft);
            }
        });

        addCanvasElement(arrowButtonsGroup);
        //
    }

    private void onArrowButtonPressed(ImageButton arrowButton)
    {
        if(arrowButton == btnUp) board.translateCurrentTilePosition(0, -1);
        else if(arrowButton == btnDown) board.translateCurrentTilePosition(0, 1);
        else if(arrowButton == btnLeft) board.translateCurrentTilePosition(-1, 0);
        else if(arrowButton == btnRight) board.translateCurrentTilePosition(1, 0);
    }
}
