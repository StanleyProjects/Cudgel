package stan.cudgel.modules.main;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import stan.cudgel.modules.cudgel.CudgelPane;

public class MainScene
    extends Scene
{
    private CudgelPane cudgelPane;

    public MainScene(double width, double height)
    {
        super(new Pane(), width, height, Color.TRANSPARENT);
        getStylesheets().add("css/cudgel.css");
        initViews((Pane)getRoot());
        init();
    }
    private void initViews(Pane root)
    {
        cudgelPane = new CudgelPane();
        root.getChildren().add(cudgelPane);
        configRoot(root);
    }
    public void init()
    {
        cudgelPane.setLayoutX(getWidth()/2 - cudgelPane.getWidth()/2);
        cudgelPane.setLayoutY(getHeight()/2 - cudgelPane.getHeight()/2);
        System.out.println(getClass().getName() + " w " + getWidth() + " h " + getHeight());
    }

    private void configRoot(Pane root)
    {
        root.setStyle("-fx-background-color: null");
        root.layout();
    }
}