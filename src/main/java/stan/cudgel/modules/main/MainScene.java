package stan.cudgel.modules.main;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import stan.cudgel.di.DI;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.contracts.MainContract;
import stan.cudgel.modules.cudgel.CudgelPane;
import stan.cudgel.utils.JavaFXUtil;

public class MainScene
    extends Scene
    implements MainContract.View
{
    private DI appComponent;

    private CudgelPane cudgelPane;

    private MainContract.Presenter presenter;

    public MainScene(double width, double height)
    {
        super(new Pane(), width, height, Color.TRANSPARENT);
        getStylesheets().add("css/cudgel.css");
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
        });
        root.setVisible(false);
        root.getChildren().add(cudgelPane);
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
        appComponent.getPlatformUtil().log(this, "w " + getWidth() + " h " + getHeight());
    }

    @Override
    public void showMusicPlayer(boolean show)
    {
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