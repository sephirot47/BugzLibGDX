package anton.fons.bugz.Scenes.BoardScenePackage;

import com.badlogic.gdx.Gdx;

import anton.fons.bugz.SceneGraph.Canvas;
import anton.fons.bugz.SceneGraph.Scene;

public class BoardCanvas extends Canvas
{
    public final static String ArrowTexturePath = "canvas/board/arrow.png";
    private Board board;

    private BoardActionDialogWidget actionDialogWidget;
    private BoardArrowsWidget arrowsWidget;

    public BoardCanvas(Scene parentScene, Board board)
    {
        super(parentScene);
        this.board = board;
        actionDialogWidget = new BoardActionDialogWidget(this);
        arrowsWidget = new BoardArrowsWidget(this);
    }

    @Override
    protected void create()
    {
        super.create();
        //

        actionDialogWidget.init(getCanvasMediumFont());
        arrowsWidget.init();

        actionDialogWidget.setPosition(100, Gdx.graphics.getHeight());
        arrowsWidget.setPosition(200, 200);

        addCanvasElement(actionDialogWidget);
        addCanvasElement(arrowsWidget);
    }

    public void onActionDialogButtonPressed(BoardActionDialogWidget.ActionDialogButton actionButton)
    {
        if(actionButton == BoardActionDialogWidget.ActionDialogButton.Move)
        {

        }
        else if(actionButton == BoardActionDialogWidget.ActionDialogButton.Attack)
        {

        }
        else if(actionButton == BoardActionDialogWidget.ActionDialogButton.Defense)
        {

        }
        else if(actionButton == BoardActionDialogWidget.ActionDialogButton.Skill)
        {

        }
    }

    public void onArrowButtonPressed(BoardArrowsWidget.Arrow arrowButton)
    {
        if(arrowButton == BoardArrowsWidget.Arrow.Up)
            board.translateCurrentTilePosition(0, -1);
        else if(arrowButton == BoardArrowsWidget.Arrow.Down)
            board.translateCurrentTilePosition(0, 1);
        else if(arrowButton == BoardArrowsWidget.Arrow.Left)
            board.translateCurrentTilePosition(-1, 0);
        else if(arrowButton == BoardArrowsWidget.Arrow.Right)
            board.translateCurrentTilePosition(1, 0);
    }

    public BoardActionDialogWidget getActionDialogWidget() { return actionDialogWidget; }
}
