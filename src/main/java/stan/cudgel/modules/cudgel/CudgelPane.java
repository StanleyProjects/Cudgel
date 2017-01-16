package stan.cudgel.modules.cudgel;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;

public class CudgelPane
    extends Pane
    implements CudgelContract.View
{
    private CudgelContract.Presenter presenter;
    private CudgelContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private CudgelButton cudgelButton;

    private boolean hover;

    public CudgelPane(PlatformUtil pu, CudgelContract.Behaviour b)
    {
        super();
        setStyle("-fx-background-color: null");
        setPrefSize(144,144);
        platformUtil = pu;
        behaviour = b;
        initViews();
        platformUtil.runOnUiThread(()->
        {
            init();
        });
    }
    private void initViews()
    {
        cudgelButton = new CudgelButton();
        getChildren().addAll(cudgelButton);
    }
    private void init()
    {
        presenter = new CudgelPresenter(this, platformUtil);
        hover = false;
        hoverProperty().addListener((observable, oldValue, newValue)->
        {
            //platformUtil.log(this, "hoverProperty oldValue " +oldValue+" newValue "+ newValue);
            hover = newValue;
        });
        cudgelButton.setOnAction((event)->
        {
        });
        cudgelButton.setOnMouseReleased((event)->
        {
            //platformUtil.log(this, "setOnMouseReleased " +event.getButton()+" "+ event.getEventType());
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = null;
            }
            else if(event.getButton() == MouseButton.SECONDARY && hover)
            {
                behaviour.exit();
            }
        });
        cudgelButton.setOnMousePressed((event)->
        {
            //platformUtil.log(this, "setOnMousePressed " +event.getButton()+" "+ event.getEventType());
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = platformUtil.drag(CudgelPane.this, event.getScreenX(), event.getScreenY());
            }
        });
        cudgelButton.setOnMouseDragged((event)->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger.drag(event.getScreenX(), event.getScreenY());
            }
        });
        moveCudgelButton();
    }

    private void moveCudgelButton()
    {
        layout();
        cudgelButton.setLayoutX(getWidth()/2 - cudgelButton.getWidth()/2);
        cudgelButton.setLayoutY(getHeight()/2 - cudgelButton.getHeight()/2);
    }
}