package stan.cudgel.modules.main;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import stan.cudgel.App;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.contracts.MainContract;
import stan.cudgel.modules.cudgel.CudgelPane;
import stan.cudgel.units.ui.MVPScene;

public class MainScene
    extends MVPScene<MainContract.Presenter>
{
    private Pane cudgelPane;
    private CudgelContract.Callback cudgelCallback;

    private final MainContract.View view = new MainContract.View()
    {
        public void showMusicPlayer(boolean show)
        {
            cudgelCallback.showMusicPlayerButton(!show);
        }
    };

    public MainScene(double width, double height)
    {
        super(new Pane(), "-fx-background-color: null", width, height, Color.TRANSPARENT);
    }
    protected void initViews(Pane root)
    {
        cudgelPane = new CudgelPane(App.getAppComponent().getPlatformUtil(), new CudgelContract.Behaviour()
        {
            public void openMusicPlayer()
            {
                getPresenter().showMusicPlayer(true);
            }
            public void exit()
            {
                getPresenter().exit();
            }
        }, (c) -> cudgelCallback = c);
        addChildrens(cudgelPane);
    }
    protected void init()
    {
        moveNode(cudgelPane
            ,getWidth()/2 - cudgelPane.getWidth()/2
            ,getHeight()/2 - cudgelPane.getHeight()/2);
        setPresenter(new MainPresenter(view, App.getAppComponent().getPlatformUtil()));
    }
}