package stan.cudgel.modules.cudgel;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.units.CallbackConnector;
import stan.cudgel.units.ui.MVPPane;

public class CudgelPane
    extends MVPPane<CudgelContract.Presenter>
{
    private CudgelContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private CudgelButton cudgelButton;

    public CudgelPane(PlatformUtil pu, CudgelContract.Behaviour b, CallbackConnector<CudgelContract.Callback> connector)
    {
        super("-fx-background-color: null", 144, 144);
        platformUtil = pu;
        behaviour = b;
        connector.set(new CudgelContract.Callback()
        {
            public void showMusicPlayerButton(boolean show)
            {
            }
        });
    }
    protected void initViews()
    {
        cudgelButton = new CudgelButton();
        addChildrens(cudgelButton);
    }
    protected void init()
    {
        moveNode(cudgelButton
            ,getWidth()/2 - cudgelButton.getWidth()/2
            ,getHeight()/2 - cudgelButton.getHeight()/2);
        cudgelButton.setOnMouseReleased((event)->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = null;
            }
            else if(event.getButton() == MouseButton.SECONDARY)
            {
                behaviour.exit();
            }
        });
        cudgelButton.setOnMousePressed((event)->
        {
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
    }
}