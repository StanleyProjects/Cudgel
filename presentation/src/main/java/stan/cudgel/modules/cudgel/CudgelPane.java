package stan.cudgel.modules.cudgel;

import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
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
                .addFxEffectDropshadow(BlurType.THREE_PASS_BOX, R.colors.BLACK, 3, 0)
//                .addFxEffectDropshadow(CSS.FxEffectBlurType.GAUSSIAN, R.colors.BLACK, 3, 0, 0, 0)
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
        interface ScreenShot
        {
            CSS main = small_button.copy()
                                   .addClearFocusBorder()
                                   .addFxBackgroundImage(R.images.CAMERA);
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
    private Button screenShotButton;

    private final ValueAnimator.Updater<Double> settingsScaleUpdater = d -> runOnUiThread(() -> setScale(settingsButton, d));
    private final ValueAnimator.Updater<Double> cudgelButtonScaleUpdater = d -> runOnUiThread(() -> setScale(cudgelButton, d));
    private final ValueAnimator.Updater<Double> musicPlayerButtonScaleUpdater = d -> runOnUiThread(() -> setScale(musicPlayerButton, d));
    private final ValueAnimator.Updater<Double> screenShotButtonScaleUpdater = d -> runOnUiThread(() -> setScale(screenShotButton, d));
    private final ValueAnimator.Interpolator<Double> hideScaleInterpolator = new ValueAnimator.AccelerateDoubleInterpolator(2);
    private final ValueAnimator.Interpolator<Double> showScaleInterpolator = new ValueAnimator.DecelerateDoubleInterpolator(2);
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
    private final ValueAnimator.AnimationListener cudgelButtonHideAnimationListener = new ValueAnimator.AnimationListener()
    {
        @Override
        public void begin()
        {
        }
        @Override
        public void end()
        {
            runOnUiThread(()->cudgelButton.setVisible(false));
        }
        @Override
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
    private final ValueAnimator.AnimationListener screenShotButtonHideAnimationListener = new ValueAnimator.AnimationListener()
    {
        @Override
        public void begin()
        {
        }
        @Override
        public void end()
        {
            runOnUiThread(()->screenShotButton.setVisible(false));
        }
        @Override
        public void cancel()
        {
        }
    };
    private final ValueAnimator.AnimationListener screenShotButtonShowAnimationListener = new ValueAnimator.AnimationListener()
    {
        @Override
        public void begin()
        {
            runOnUiThread(()->screenShotButton.setVisible(true));
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
    private final ValueAnimator.Animator settingsShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, settingsScaleUpdater, showScaleInterpolator).setAnimationListener(settingsShowAnimationListener);
    private final ValueAnimator.Animator settingsHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, settingsScaleUpdater, hideScaleInterpolator).setAnimationListener(settingsHideAnimationListener);
    private final ValueAnimator.Animator cudgelButtonShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, cudgelButtonScaleUpdater, showScaleInterpolator).setAnimationListener(cudgelButtonShowAnimationListener);
    private final ValueAnimator.Animator cudgelButtonHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, cudgelButtonScaleUpdater, hideScaleInterpolator).setAnimationListener(cudgelButtonHideAnimationListener);
    private final ValueAnimator.Animator musicPlayerButtonShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, musicPlayerButtonScaleUpdater, showScaleInterpolator).setAnimationListener(musicPlayerButtonShowAnimationListener);
    private final ValueAnimator.Animator musicPlayerButtonHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, musicPlayerButtonScaleUpdater, hideScaleInterpolator).setAnimationListener(musicPlayerButtonHideAnimationListener);
    private final ValueAnimator.Animator screenShotButtonShowAnimator = ValueAnimator.create(Styles.scale_animation_time, 0.01, 1, screenShotButtonScaleUpdater, showScaleInterpolator).setAnimationListener(screenShotButtonShowAnimationListener);
    private final ValueAnimator.Animator screenShotButtonHideAnimator = ValueAnimator.create(Styles.scale_animation_time, 1, 0.01, screenShotButtonScaleUpdater, hideScaleInterpolator).setAnimationListener(screenShotButtonHideAnimationListener);

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
            public void showScreenShotButton(boolean show)
            {
                (show ? screenShotButtonShowAnimator : screenShotButtonHideAnimator).animate();
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
        screenShotButton = new Button();
        screenShotButton.setMinSize(Styles.small_button_size, Styles.small_button_size);
        setStyle(screenShotButton, Styles.ScreenShot.normal, Styles.ScreenShot.hover, Styles.ScreenShot.pressed);
        addChildrens(cudgelButton, musicPlayerButton, settingsButton, screenShotButton);
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
                runOnNewThread(cudgelButtonHideAnimator::animate, 300);
                runOnNewThread(settingsHideAnimator::animate, 200);
                runOnNewThread(musicPlayerButtonHideAnimator::animate, 100);
                runOnNewThread(screenShotButtonHideAnimator::animate, 0);
                runOnNewThread(behaviour::exit, 400 + Styles.scale_animation_time);
            }
        });
        cudgelButton.setOnMousePressed(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = platformUtil.newDragger(CudgelPane.this, event.getScreenX(), event.getScreenY());
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
        moveNode(screenShotButton, getWidth() - screenShotButton.getWidth(), getHeight()/2 - screenShotButton.getHeight()/2);
        screenShotButton.setOnAction(event -> behaviour.takeScreenShot());
        settingsButton.setVisible(false);
        cudgelButton.setVisible(false);
        musicPlayerButton.setVisible(false);
        screenShotButton.setVisible(false);
        setScale(settingsButton, 0.01);
        setScale(cudgelButton, 0.01);
        setScale(musicPlayerButton, 0.01);
        setScale(screenShotButton, 0.01);
        cudgelButtonShowAnimator.animate();
        runOnNewThread(settingsShowAnimator::animate, 100);
        runOnNewThread(musicPlayerButtonShowAnimator::animate, 200);
        runOnNewThread(screenShotButtonShowAnimator::animate, 300);
    }
}