package stan.cudgel.modules.settings;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;

import stan.cudgel.App;
import stan.cudgel.cores.SettingsCore;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.SettingsContract;
import stan.cudgel.modules.settings.models.Media;
import stan.cudgel.units.ui.MVPPane;
import stan.cudgel.utils.CSS;
import stan.cudgel.R;

public class SettingsPane
    extends MVPPane<SettingsContract.Presenter>
{
    private interface Styles
    {
        int pane_width = 512;
        int pane_height = 256;

        String main = new CSS()
                .addFxEffectDropshadow(BlurType.THREE_PASS_BOX, R.colors.BLACK, 10, 0, 0, 0)
                .addFxBackgroundColor(R.colors.WHITE)
                .generate();
        interface Toolbar
        {
            int height = 36;
            int label_text_size = 16;
            String main = new CSS()
                    .addFxBackgroundColor(R.colors.PRIMARY)
                    .generate();
            String label = new CSS()
                    .addFxFontSize(label_text_size)
                    .addFxTextFill(R.colors.WHITE)
                    .generate();
            interface Close
            {
                CSS main = new CSS()
                        .addClearFocusBorder()
                        .addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
                        .addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT)
                        .addFxBackgroundRadius(0)
                        .addFxBackgroundImage(R.images.CLOSE_WHITE);
                String normal = main.copy()
                                    .addFxBackgroundSize(Toolbar.height * 0.5)
                                    .addFxBackgroundColor(R.colors.TRANSPARENT)
                                    .generate();
                String hover = main.copy()
                                   .addFxBackgroundSize(Toolbar.height * 0.6)
                                   .addFxBackgroundColor(R.colors.WHITE, 0.1)
                                   .generate();
                String pressed = main.copy()
                                     .addFxBackgroundSize(Toolbar.height * 0.5)
                                     .addFxBackgroundColor(R.colors.WHITE, 0.2)
                                     .generate();
            }
        }
        interface Data
        {
            int field_text_size = 12;
            int label_text_size = 12;
            int sublabel_text_size = 10;

            String scroll = new CSS()
                    .addClearFocusBorder()
                    .addFxBackgroundColor(R.colors.TRANSPARENT)
                    .generate();
            String label = new CSS()
                    .addFxFontSize(label_text_size)
                    .addFxTextFill(R.colors.PRIMARY)
                    .generate();
            String sublabel = new CSS()
                    .addFxFontSize(sublabel_text_size)
                    .addFxTextFill(R.colors.GRAY_DARK)
                    .generate();
            String text_field = new CSS()
                    .addFxBackgroundColor("null")
                    .addFxBorderColor(R.colors.PRIMARY)
                    .addFxBorderWidth(0,0,2,0)
                    .addFxFontSize(field_text_size)
                    .addFxTextFill(R.colors.BLACK)
                    .addFxPromptTextFill(R.colors.GRAY)
                    .generate();
        }
        interface Actionbar
        {
            int height = 36;
            int button_text_size = 12;

            String main = new CSS()
                    .addFxBackgroundColor(R.colors.WHITE)
                    .generate();
            interface Save
            {
                CSS main = new CSS()
                        .addFxFontSize(button_text_size)
                        .addFxBackgroundRadius(0)
                        .addClearFocusBorder();
                String normal = main.copy()
                                    .addFxTextFill(R.colors.PRIMARY)
                                    .addFxBackgroundColor(R.colors.TRANSPARENT)
                                    .generate();
                String hover = main.copy()
                                   .addFxTextFill(R.colors.PRIMARY)
                                   .addFxBackgroundColor(R.colors.PRIMARY, 0.1)
                                   .generate();
                String pressed = main.copy()
                                     .addFxTextFill(R.colors.PRIMARY)
                                     .addFxBackgroundColor(R.colors.PRIMARY, 0.2)
                                     .generate();
            }
        }
    }

    private final SettingsContract.View view = new SettingsContract.View()
    {
        public void update(SettingsCore.Media media)
        {
            runOnUiThread(() ->
            {
                screenshotsPathTextField.setText(media.getScreenshotsPath());
            });
        }
    };

    private SettingsContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private Pane toolbar;
    private Button closeButton;
    private Button saveButton;
    private TextField screenshotsPathTextField;

    public SettingsPane(PlatformUtil pu, SettingsContract.Behaviour b)
    {
        super(Styles.main, Styles.pane_width, Styles.pane_height);
        platformUtil = pu;
        behaviour = b;
    }

    protected void initViews()
    {
        VBox vbox = new VBox();
        vbox.prefWidthProperty().bind(widthProperty());
        vbox.prefHeightProperty().bind(heightProperty());
//        vbox.setMinSize(Styles.pane_size, Styles.pane_size);
        toolbar = new Pane();
        toolbar.setMinSize(Styles.pane_width, Styles.Toolbar.height);
        toolbar.setStyle(Styles.Toolbar.main);
        closeButton = new Button();
        closeButton.setMinSize(Styles.Toolbar.height, Styles.Toolbar.height);
        setStyle(closeButton, Styles.Toolbar.Close.normal, Styles.Toolbar.Close.hover, Styles.Toolbar.Close.pressed);
        Label toolbarLabel = new Label(R.strings.SETTINGS_TITLE);
        toolbarLabel.setMinSize(Styles.pane_width - Styles.Toolbar.height*1, Styles.Toolbar.height);
        toolbarLabel.setStyle(Styles.Toolbar.label);
        toolbarLabel.setPadding(new Insets(0, 0, 0, Styles.Toolbar.label_text_size/2));
        toolbar.getChildren().addAll(toolbarLabel, closeButton);
        ScrollPane sp = new ScrollPane();
        sp.setStyle(Styles.Data.scroll);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.prefWidthProperty().bind(vbox.widthProperty());
        sp.prefHeightProperty().bind(vbox.heightProperty());
        Pane dataPane = initData();
        dataPane.prefWidthProperty().bind(sp.widthProperty());
        sp.setContent(dataPane);
        HBox actionBar = new HBox();
        actionBar.setAlignment(Pos.CENTER_RIGHT);
        actionBar.setMinSize(Styles.pane_width, Styles.Actionbar.height);
        actionBar.setStyle(Styles.Actionbar.main);
        saveButton = new Button();
        saveButton.setMinHeight(Styles.Actionbar.height);
//        saveButton.setMinWidth(Styles.Actionbar.height);
        setStyle(saveButton, Styles.Actionbar.Save.normal, Styles.Actionbar.Save.hover, Styles.Actionbar.Save.pressed);
        saveButton.setText(R.strings.SETTINGS_SAVE.toUpperCase());
        actionBar.getChildren().addAll(saveButton);
        vbox.getChildren().addAll(toolbar, sp, actionBar);
        addChildrens(vbox);
    }
    private Pane initData()
    {
        VBox dataBox = new VBox();
        Pane mediaPane = initMedia();
        mediaPane.prefWidthProperty().bind(dataBox.widthProperty());
        dataBox.getChildren().addAll(mediaPane);
        return dataBox;
    }
    private Pane initMedia()
    {
        VBox mediaBox = new VBox();
        Label mediaLabel = new Label(R.strings.MEDIA_LABLE);
        mediaLabel.setStyle(Styles.Data.label);
        VBox.setMargin(mediaLabel,new Insets(Styles.Data.label_text_size/2, 0, 0, Styles.Data.label_text_size/2));
        Label screenshotsPathSublabel = new Label(R.strings.SCREENSHOTSPATH_SUBLABLE);
        screenshotsPathSublabel.setStyle(Styles.Data.sublabel);
        VBox.setMargin(screenshotsPathSublabel,new Insets(0, 0, 0, Styles.Data.label_text_size));
        screenshotsPathTextField = new TextField();
        screenshotsPathTextField.setStyle(Styles.Data.text_field);
        VBox.setMargin(screenshotsPathTextField,new Insets(0, 0, 0, Styles.Data.label_text_size));
        screenshotsPathTextField.setPromptText(R.strings.SCREENSHOTSPATH_HINT);
        screenshotsPathTextField.setPadding(new Insets(2,0,2,0));
        //
        mediaBox.getChildren().addAll(mediaLabel, screenshotsPathSublabel, screenshotsPathTextField);
        return mediaBox;
    }
    protected void init()
    {
        moveNode(closeButton, toolbar.getWidth() - closeButton.getWidth(), 0);
        toolbar.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = null;
            }
        });
        toolbar.setOnMousePressed(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = platformUtil.newDragger(SettingsPane.this, event.getScreenX(), event.getScreenY());
            }
        });
        toolbar.setOnMouseDragged(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger.drag(event.getScreenX(), event.getScreenY());
            }
        });
        closeButton.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY && closeButton.isHover())
            {
                getPresenter().update();
                behaviour.close();
            }
        });
        saveButton.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY && saveButton.isHover())
            {
                getPresenter().save(new Media(screenshotsPathTextField.getText()));
                behaviour.close();
            }
        });
        setPresenter(new SettingsPresenter(view, new SettingsModel(App.getAppComponent().getSettings())));

        getPresenter().update();
    }
}