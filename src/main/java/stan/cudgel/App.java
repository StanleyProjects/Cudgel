package stan.cudgel;

import javafx.application.Application;

import javafx.geometry.Rectangle2D;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import stan.cudgel.modules.main.MainScene;

public class App
    extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new MainScene(screen.getWidth(), screen.getHeight()));
        primaryStage.show();
    }
}