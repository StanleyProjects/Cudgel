package stan.cudgel;

import javafx.application.Application;

import javafx.geometry.Rectangle2D;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import stan.cudgel.di.AppComponent;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.modules.main.MainScene;
import stan.cudgel.utils.JavaFXUtil;

public class App
    extends Application
{
    static private AppComponent appComponent;
    static public AppComponent getAppComponent()
    {
        return appComponent;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage)
    {
        appComponent = new Component(new JavaFXUtil());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new MainScene(screen.getWidth(), screen.getHeight()));
        primaryStage.show();
    }

    private final class Component
        implements AppComponent
    {
        private PlatformUtil platformUtil;

        Component(PlatformUtil pu)
        {
            platformUtil = pu;
        }

        public PlatformUtil getPlatformUtil()
        {
            return platformUtil;
        }
    }
}