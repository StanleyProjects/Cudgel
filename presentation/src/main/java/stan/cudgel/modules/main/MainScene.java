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
import stan.cudgel.utils.CSS;
import stan.cudgel.utils.ValueAnimator;

public class MainScene
    extends MVPScene<MainContract.Presenter>
{
    private interface Styles
    {
        int scale_animation_time = 300;

        String main = new CSS()
                .addFxBackgroundColor("null")
                .generate();
    }

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
            (show ? settingsShowAnimator : settingsHideAnimator).animate();
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

    private final ValueAnimator.Updater<Double> settingsScaleUpdater = d -> runOnUiThread(() -> setScale(settingsPane, d));
    private final ValueAnimator.Interpolator<Double> settingsHideScaleInterpolator = new ValueAnimator.AccelerateDoubleInterpolator(2);
    private final ValueAnimator.Interpolator<Double> settingsShowScaleInterpolator = new ValueAnimator.DecelerateDoubleInterpolator(2);
    private final ValueAnimator.AnimationListener settingsHideAnimationListener = new ValueAnimator.AnimationListener()
    {
        public void begin()
        {
            runOnUiThread(()->settingsPane.setDisable(true));
        }
        public void end()
        {
            runOnUiThread(()->settingsPane.setVisible(false));
        }
        public void cancel()
        {
        }
    };
    private final ValueAnimator.AnimationListener settingsShowAnimationListener = new ValueAnimator.AnimationListener()
    {
        public void begin()
        {
            runOnUiThread(()->settingsPane.setVisible(true));
        }
        public void end()
        {
            runOnUiThread(()->settingsPane.setDisable(false));
        }
        public void cancel()
        {
        }
    };
    private final ValueAnimator.Animator settingsShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, settingsScaleUpdater, settingsShowScaleInterpolator).setAnimationListener(settingsShowAnimationListener);
    private final ValueAnimator.Animator settingsHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, settingsScaleUpdater, settingsHideScaleInterpolator).setAnimationListener(settingsHideAnimationListener);

    public MainScene(double width, double height)
    {
        super(new Pane(), Styles.main, width, height, Color.TRANSPARENT);
        getStylesheets().add("style.css");
//        getStylesheets().add("data:text/css;charset=utf-8,.root {-fx-background-color : red}");
//        getStylesheets().add("data:text/css;charset=utf-8,.root%7B%0D%0A%20%20%20%20-fx-font-family%3A%20%22Muli%22%3B%0D%0A%20%20%20%20-fx-font-weight%3A%20lighter%3B%0D%0A%20%20%20%20-fx-font-size%3A%2035pt%3B%0D%0A%20%20%20%20-fx-padding%3A%200%3B%0D%0A%20%20%20%20-fx-spacing%3A%200%3B%0D%0A%7D");
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
        setScale(settingsPane, 0);
        setPresenter(new MainPresenter(view, router));
    }
}