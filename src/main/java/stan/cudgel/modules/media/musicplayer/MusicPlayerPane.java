package stan.cudgel.modules.media.musicplayer;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import stan.cudgel.contracts.media.MusicPlayerContract;
import stan.cudgel.di.PlatformUtil;

public class MusicPlayerPane
    extends Pane
	implements MusicPlayerContract.View
{
    private MusicPlayerContract.Presenter presenter;
    private MusicPlayerContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private Button playButton;

    private boolean hover;

    public MusicPlayerPane(PlatformUtil pu, MusicPlayerContract.Behaviour b)
    {
        super();
        setStyle("-fx-background-color: null");
        setPrefSize(144,144);
        platformUtil = pu;
        behaviour = b;
        initViews();
        platformUtil.runOnUiThread(()->
        {
            init();
        });
    }
    private void initViews()
    {
        playButton = new Button();
        playButton.setMinSize(56, 56);
        getChildren().addAll(playButton);
    }
    private void init()
    {
        presenter = new MusicPlayerPresenter(this);
        hover = false;
        hoverProperty().addListener((observable, oldValue, newValue)->
        {
            platformUtil.log(this, "hoverProperty oldValue " +oldValue+" newValue "+ newValue);
            hover = newValue;
        });
        playButton.setOnAction((event)->
        {
            presenter.playPauseSwitch();
        });
        playButton.setOnMouseReleased((event)->
        {
            //platformUtil.log(this, "setOnMouseReleased " +event.getButton()+" "+ event.getEventType());
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = null;
            }
            else if(event.getButton() == MouseButton.SECONDARY && hover)
            {
                behaviour.close();
            }
        });
        playButton.setOnMousePressed((event)->
        {
            //platformUtil.log(this, "setOnMousePressed " +event.getButton()+" "+ event.getEventType());
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = platformUtil.drag(MusicPlayerPane.this, event.getScreenX(), event.getScreenY());
            }
        });
        playButton.setOnMouseDragged((event)->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger.drag(event.getScreenX(), event.getScreenY());
            }
        });
        movePlayButton();
        showPlay();
    }

    private void movePlayButton()
    {
        layout();
        playButton.setLayoutX(getWidth()/2 - playButton.getWidth()/2);
        playButton.setLayoutY(getHeight()/2 - playButton.getHeight()/2);
    }

    @Override
    public void showStop()
    {
    }
    @Override
    public void showPlay()
    {
        playButton.setId("play_button");
    }
    @Override
    public void showPause()
    {
        playButton.setId("pause_button");
    }
}