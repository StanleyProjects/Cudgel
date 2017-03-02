package stan.cudgel.modules.settings;

import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.SettingsContract;
import stan.cudgel.units.ui.MVPPane;
import stan.cudgel.utils.CSS;
import stan.cudgel.R;

public class SettingsPane
    extends MVPPane<SettingsContract.Presenter>
{
    private interface Styles
    {
        int pane_size = 256;

        String main = new CSS()
                .addFxEffectDropshadow(CSS.FxEffectBlurType.THREE_PASS_BOX, R.colors.BLACK, 10, 0, 0, 0)
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
        }
        interface Close
        {
            CSS main = new CSS()
                    .addClearFocusBorder()
                    .addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
                    .addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT)
//                    .addFxBackgroundSize(Toolbar.height/2)
                    .addFxBackgroundRadius(0)
                    .addFxBackgroundImage(R.images.CLOSE);
            String normal = main.copy()
                    .addFxBackgroundSize(Toolbar.height/2)
                    .addFxBackgroundColor(R.colors.TRANSPARENT)
                    .generate();
            String hover = main.copy()
                    .addFxBackgroundSize(Toolbar.height/3 * 2)
                    .addFxBackgroundColor(R.colors.WHITE, 0.2)
                    .generate();
            String pressed = main.copy()
                    .addFxBackgroundSize(Toolbar.height/2)
                    .addFxBackgroundColor(R.colors.WHITE, 0.4)
                    .generate();
        }
    }

    private SettingsContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private Pane toolbar;
    private Button closeButton;
    private Label toolbarLabel;

    public SettingsPane(PlatformUtil pu, SettingsContract.Behaviour b)
    {
        super(Styles.main, Styles.pane_size, Styles.pane_size);
        platformUtil = pu;
        behaviour = b;
    }
    protected void initViews()
    {
        VBox vbox = new VBox();
        vbox.setMinSize(Styles.pane_size, Styles.pane_size);
        toolbar = new Pane();
        toolbar.setMinSize(Styles.pane_size, Styles.Toolbar.height);
        toolbar.setStyle(Styles.Toolbar.main);
        closeButton = new Button();
        closeButton.setMinSize(Styles.Toolbar.height, Styles.Toolbar.height);
        setStyle(closeButton, Styles.Close.normal, Styles.Close.hover, Styles.Close.pressed);
        toolbarLabel = new Label("Settings");
        toolbarLabel.setMinSize(Styles.pane_size - Styles.Toolbar.height*1, Styles.Toolbar.height);
        toolbarLabel.setStyle(Styles.Toolbar.label);
        toolbarLabel.setPadding(new Insets(0, 0, 0, Styles.Toolbar.label_text_size/2));
        toolbar.getChildren().addAll(toolbarLabel, closeButton);
        vbox.getChildren().add(toolbar);
        addChildrens(vbox);
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
                viewDragger = platformUtil.drag(SettingsPane.this, event.getScreenX(), event.getScreenY());
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
                behaviour.close();
            }
        });
    }
}