package stan.cudgel.modules.cudgel;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BackgroundRepeat;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.units.CallbackConnector;
import stan.cudgel.units.ui.MVPPane;
import stan.cudgel.utils.CSS;
import stan.cudgel.R;
import stan.cudgel.utils.ValueAnimator;

public class CudgelPane
    extends MVPPane<CudgelContract.Presenter>
{
    private interface Styles
    {
        int pane_size = 144;
        int small_button_size = 32;
        int scale_animation_time = 300;

        String main = new CSS()
                .addFxBackgroundColor("null")
                .generate();
        CSS small_button = new CSS()
                .addFxEffectDropshadow(CSS.FxEffectBlurType.THREE_PASS_BOX, R.colors.BLACK, 3, 0, 0, 0)
                .addFxBackgroundRadius(small_button_size/2)
                .addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
                .addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT)
                .addFxBackgroundSize(18);
        interface MusicPlayer
        {
            CSS main = small_button.copy()
                    .addClearFocusBorder()
                    .addFxBackgroundImage(R.images.MUSIC);
            String normal = main.copy()
                    .addFxBackgroundColor(R.colors.PRIMARY)
                    .generate();
            String hover = main.copy()
                    .addFxBackgroundColor(R.colors.PRIMARY, 25)
                    .generate();
            String pressed = main.copy()
                    .addFxBackgroundColor(R.colors.PRIMARY, 50)
                    .generate();
        }
        interface Settings
        {
            CSS main = small_button.copy()
                    .addClearFocusBorder()
                    .addFxBackgroundImage(R.images.SETTINGS);
            String normal = main.copy()
                    .addFxBackgroundColor(R.colors.PRIMARY)
                    .generate();
            String hover = main.copy()
                    .addFxBackgroundColor(R.colors.PRIMARY, 25)
                    .generate();
            String pressed = main.copy()
                    .addFxBackgroundColor(R.colors.PRIMARY, 50)
                    .generate();
        }
    }

    private CudgelContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private CudgelButton cudgelButton;
    private Button musicPlayerButton;
    private Button settingsButton;

    private final ValueAnimator.Updater<Double> settingsScaleUpdater = d -> runOnUiThread(() -> setScale(settingsButton, d));
    private final ValueAnimator.Updater<Double> cudgelButtonScaleUpdater = d -> runOnUiThread(() -> setScale(cudgelButton, d));
    private final ValueAnimator.Updater<Double> musicPlayerButtonScaleUpdater = d -> runOnUiThread(() -> setScale(musicPlayerButton, d));
    private final ValueAnimator.Interpolator<Double> settingsHideScaleInterpolator = new ValueAnimator.AccelerateDoubleInterpolator(2);
    private final ValueAnimator.Interpolator<Double> settingsShowScaleInterpolator = new ValueAnimator.DecelerateDoubleInterpolator(2);
//    private final ValueAnimator.Interpolator<Double> settingsHideScaleInterpolator = new ValueAnimator.BounceDoubleInterpolator();
//    private final ValueAnimator.Interpolator<Double> settingsShowScaleInterpolator = settingsHideScaleInterpolator;
    private final ValueAnimator.Interpolator<Double> cudgelButtonShowScaleInterpolator = new ValueAnimator.DecelerateDoubleInterpolator(2);
    private final ValueAnimator.Interpolator<Double> musicPlayerButtonHideScaleInterpolator = new ValueAnimator.AccelerateDoubleInterpolator(2);
    private final ValueAnimator.Interpolator<Double> musicPlayerButtonShowScaleInterpolator = new ValueAnimator.DecelerateDoubleInterpolator(2);
    private final ValueAnimator.AnimationListener settingsHideAnimationListener = new ValueAnimator.AnimationListener()
    {
        public void begin()
        {
            runOnUiThread(()->settingsButton.setDisable(true));
        }
        public void end()
        {
            runOnUiThread(()->settingsButton.setVisible(false));
        }
        public void cancel()
        {
        }
    };
    private final ValueAnimator.AnimationListener settingsShowAnimationListener = new ValueAnimator.AnimationListener()
    {
        public void begin()
        {
            runOnUiThread(()->settingsButton.setVisible(true));
        }
        public void end()
        {
            runOnUiThread(()->settingsButton.setDisable(false));
        }
        public void cancel()
        {
        }
    };
    private final ValueAnimator.AnimationListener cudgelButtonShowAnimationListener = new ValueAnimator.AnimationListener()
    {
        @Override
        public void begin()
        {
            runOnUiThread(()->cudgelButton.setVisible(true));
        }
        @Override
        public void end()
        {
        }
        @Override
        public void cancel()
        {
        }
    };
    private final ValueAnimator.AnimationListener musicPlayerButtonHideAnimationListener = new ValueAnimator.AnimationListener()
    {
        @Override
        public void begin()
        {
        }
        @Override
        public void end()
        {
            runOnUiThread(()->musicPlayerButton.setVisible(false));
        }
        @Override
        public void cancel()
        {
        }
    };
    private final ValueAnimator.AnimationListener musicPlayerButtonShowAnimationListener = new ValueAnimator.AnimationListener()
    {
        @Override
        public void begin()
        {
            runOnUiThread(()->musicPlayerButton.setVisible(true));
        }
        @Override
        public void end()
        {
        }
        @Override
        public void cancel()
        {
        }
    };
    private final ValueAnimator.Animator settingsShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, settingsScaleUpdater, settingsShowScaleInterpolator).setAnimationListener(settingsShowAnimationListener);
    private final ValueAnimator.Animator settingsHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, settingsScaleUpdater, settingsHideScaleInterpolator).setAnimationListener(settingsHideAnimationListener);
    private final ValueAnimator.Animator cudgelButtonShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, cudgelButtonScaleUpdater, cudgelButtonShowScaleInterpolator).setAnimationListener(cudgelButtonShowAnimationListener);
    private final ValueAnimator.Animator musicPlayerButtonShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, musicPlayerButtonScaleUpdater, musicPlayerButtonShowScaleInterpolator).setAnimationListener(musicPlayerButtonShowAnimationListener);
    private final ValueAnimator.Animator musicPlayerButtonHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, musicPlayerButtonScaleUpdater, musicPlayerButtonHideScaleInterpolator).setAnimationListener(musicPlayerButtonHideAnimationListener);

    public CudgelPane(PlatformUtil pu, CudgelContract.Behaviour b, CallbackConnector<CudgelContract.Callback> connector)
    {
        super(Styles.main, Styles.pane_size, Styles.pane_size);
        platformUtil = pu;
        behaviour = b;
        connector.set(new CudgelContract.Callback()
        {
            public void showMusicPlayerButton(boolean show)
            {
                (show ? musicPlayerButtonShowAnimator : musicPlayerButtonHideAnimator).animate();
            }
            public void showSettingsButton(boolean show)
            {
                (show ? settingsShowAnimator : settingsHideAnimator).animate();
            }
        });
    }
    protected void initViews()
    {
        cudgelButton = new CudgelButton();
        musicPlayerButton = new Button();
        musicPlayerButton.setMinSize(Styles.small_button_size, Styles.small_button_size);
        setStyle(musicPlayerButton, Styles.MusicPlayer.normal, Styles.MusicPlayer.hover, Styles.MusicPlayer.pressed);
        settingsButton = new Button();
        settingsButton.setMinSize(Styles.small_button_size, Styles.small_button_size);
        setStyle(settingsButton, Styles.Settings.normal, Styles.Settings.hover, Styles.Settings.pressed);
        addChildrens(cudgelButton, musicPlayerButton, settingsButton);
    }
    protected void init()
    {
        moveNode(cudgelButton
            ,getWidth()/2 - cudgelButton.getWidth()/2
            ,getHeight()/2 - cudgelButton.getHeight()/2);
        cudgelButton.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = null;
            }
            else if(event.getButton() == MouseButton.SECONDARY && cudgelButton.isHover())
            {
                behaviour.exit();
            }
        });
        cudgelButton.setOnMousePressed(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = platformUtil.drag(CudgelPane.this, event.getScreenX(), event.getScreenY());
            }
        });
        cudgelButton.setOnMouseDragged(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger.drag(event.getScreenX(), event.getScreenY());
            }
        });
        moveNode(musicPlayerButton, getWidth()/2 - musicPlayerButton.getWidth()/2, 0);
        musicPlayerButton.setOnAction(event -> behaviour.openMusicPlayer());
        moveNode(settingsButton, 0, getHeight()/2 - musicPlayerButton.getHeight()/2);
        settingsButton.setOnAction(event -> behaviour.openSettings());
        settingsButton.setVisible(false);
        cudgelButton.setVisible(false);
        musicPlayerButton.setVisible(false);
        setScale(settingsButton, 0.01);
        setScale(cudgelButton, 0.01);
        setScale(musicPlayerButton, 0.01);
        cudgelButtonShowAnimator.animate();
        runOnNewThread(settingsShowAnimator::animate, 200);
        runOnNewThread(musicPlayerButtonShowAnimator::animate, 400);
    }
}