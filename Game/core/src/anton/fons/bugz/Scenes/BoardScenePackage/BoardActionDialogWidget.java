package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class BoardActionDialogWidget extends VerticalGroup
{
    private BoardCanvas canvas;
    private TextButton buttonMove, buttonAttack, buttonDefense, buttonSkill;

    public enum ActionDialogButton
    {
        Move, Attack, Defense, Skill
    }

    public BoardActionDialogWidget(BoardCanvas canvas)
    {
        this.canvas = canvas;
    }

    public void init(BitmapFont font)
    {
        TextButtonStyle tbs = new TextButtonStyle();
        tbs.font = font;

        buttonMove = new TextButton("Move", tbs);
        buttonAttack = new TextButton("Attack", tbs);
        buttonDefense = new TextButton("Defense", tbs);
        buttonSkill = new TextButton("Skill", tbs);

        this.addActorAt(0, buttonMove);
        this.addActorAt(1, buttonAttack);
        this.addActorAt(2, buttonDefense);
        this.addActorAt(3, buttonSkill);
        this.align(Align.topLeft);
        this.space(5.0f);

        buttonMove.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onActionDialogButtonPressed(ActionDialogButton.Move);
            }
        });

        buttonAttack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onActionDialogButtonPressed(ActionDialogButton.Attack);
            }
        });

        buttonDefense.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onActionDialogButtonPressed(ActionDialogButton.Defense);
            }
        });

        buttonSkill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                canvas.onActionDialogButtonPressed(ActionDialogButton.Skill);
            }
        });
    }

    public void show() { setVisible(true); }
    public void hide() { setVisible(false); }
}
