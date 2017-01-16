package stan.cudgel.modules.cudgel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CudgelPane
    extends Pane
{
    private CudgelButton cudgelButton;

    public CudgelPane()
    {
        super();
        setStyle("-fx-background-color: null");
        setPrefSize(144,144);
        initViews();
        Platform.runLater(()->
        {
            init();
        });
    }
    private void initViews()
    {
        cudgelButton = new CudgelButton();
        getChildren().addAll(cudgelButton);
    }
    private void init()
    {
        cudgelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
            }
        });
        cudgelButton.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                }
                else if(event.getButton() == MouseButton.SECONDARY)
                {
                }
            }
        });
        cudgelButton.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
            }
        });
        moveCudgelButton();
    }

    private void moveCudgelButton()
    {
        layout();
        cudgelButton.setLayoutX(getWidth()/2 - cudgelButton.getWidth()/2);
        cudgelButton.setLayoutY(getHeight()/2 - cudgelButton.getHeight()/2);
    }
}