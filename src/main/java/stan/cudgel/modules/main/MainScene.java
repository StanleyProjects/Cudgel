package stan.cudgel.modules.main;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import stan.cudgel.di.DI;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.contracts.MainContract;
import stan.cudgel.contracts.media.MusicPlayerContract;
import stan.cudgel.modules.cudgel.CudgelPane;
import stan.cudgel.modules.media.musicplayer.MusicPlayerPane;
import stan.cudgel.utils.JavaFXUtil;

public class MainScene
    extends Scene
    implements MainContract.View
{
    private DI appComponent;

    private Pane cudgelPane;
    private CudgelContract.Callback cudgelCallback;

    private Pane musicPlayerPane;

    private MainContract.Presenter presenter;

    public MainScene(double width, double height)
    {
        super(new Pane(), width, height, Color.TRANSPARENT);
        getStylesheets().addAll(
            "css/cudgel.css",
            "css/media/musicplayer.css");
        appComponent = new AppComponent(new JavaFXUtil());
        presenter = new MainPresenter(this, appComponent.getPlatformUtil());
        initViews((Pane)getRoot());
        //init();
        appComponent.getPlatformUtil().runOnUiThread(()->
        {
            init();
        });
    }
    private void initViews(Pane root)
    {
        cudgelPane = new CudgelPane(appComponent.getPlatformUtil(), new CudgelContract.Behaviour()
        {
            @Override
            public void openMusicPlayer()
            {
                presenter.showMusicPlayer(true);
            }
            @Override
            public void exit()
            {
                presenter.exit();
            }
        }, (c)->
        {
            cudgelCallback = c;
        });
        musicPlayerPane = new MusicPlayerPane(appComponent.getPlatformUtil(), new MusicPlayerContract.Behaviour()
        {
            @Override
            public void close()
            {
                presenter.showMusicPlayer(false);
            }
        });
        root.setVisible(false);
        root.getChildren().addAll(cudgelPane, musicPlayerPane);
        configRoot(root);
    }
    private void configRoot(Pane root)
    {
        root.setStyle("-fx-background-color: null");
        root.layout();
    }
    public void init()
    {
        cudgelPane.setLayoutX(getWidth()/2 - cudgelPane.getWidth()/2);
        cudgelPane.setLayoutY(getHeight()/2 - cudgelPane.getHeight()/2);
        getRoot().setVisible(true);
        musicPlayerPane.setVisible(false);
        appComponent.getPlatformUtil().log(this, "w " + getWidth() + " h " + getHeight());
    }

    @Override
    public void showMusicPlayer(boolean show)
    {
        cudgelCallback.showMusicPlayerButton(!show);
        musicPlayerPane.setVisible(show);
    }

    private class AppComponent
            implements DI
    {
        private PlatformUtil platformUtil;

        AppComponent(PlatformUtil pu)
        {
            platformUtil = pu;
        }

        @Override
        public PlatformUtil getPlatformUtil()
        {
            return platformUtil;
        }
    }
}