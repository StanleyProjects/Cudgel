package stan.cudgel.modules.main;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import stan.cudgel.App;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.contracts.MainContract;
import stan.cudgel.contracts.SettingsContract;
import stan.cudgel.modules.cudgel.CudgelPane;
import stan.cudgel.modules.settings.SettingsPane;
import stan.cudgel.units.ui.MVPScene;

public class MainScene
    extends MVPScene<MainContract.Presenter>
{
    private Pane cudgelPane;
    private Pane settingsPane;
    private CudgelContract.Callback cudgelCallback;

    private final MainContract.View view = new MainContract.View()
    {
    };
    private final MainContract.Router router = new MainContract.Router()
    {
        public void showSettings(boolean show)
        {
            cudgelCallback.showSettingsButton(!show);
            settingsPane.setVisible(show);
        }
        public void showMusicPlayer(boolean show)
        {
            cudgelCallback.showMusicPlayerButton(!show);
        }
        public void exit()
        {
            App.getAppComponent().getPlatformUtil().exit();
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
            public void openSettings()
            {
                getPresenter().showSettings(true);
            }
            public void openMusicPlayer()
            {
                getPresenter().showMusicPlayer(true);
            }
            public void exit()
            {
                getPresenter().exit();
            }
        }, c -> cudgelCallback = c);
        settingsPane = new SettingsPane(App.getAppComponent().getPlatformUtil(), new SettingsContract.Behaviour()
        {
            public void close()
            {
                getPresenter().showSettings(false);
            }
        });
        addChildrens(cudgelPane, settingsPane);
    }
    protected void init()
    {
        moveNode(cudgelPane
            ,getWidth()/2 - cudgelPane.getWidth()/2
            ,getHeight()/2 - cudgelPane.getHeight()/2);
        moveNode(settingsPane
            ,getWidth()/2 - settingsPane.getWidth()/2
            ,getHeight()/2 - settingsPane.getHeight()/2);
        settingsPane.setVisible(false);
        setPresenter(new MainPresenter(view, router));
    }
}