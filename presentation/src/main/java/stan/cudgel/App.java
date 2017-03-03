package stan.cudgel;

import javafx.application.Application;

import javafx.geometry.Rectangle2D;

import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import stan.cudgel.di.AppComponent;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.modules.main.MainScene;
import stan.cudgel.utils.JavaFXUtil;
import stan.cudgel.utils.ScreenShoter;

public class App
    extends Application
{
    static private AppComponent appComponent;
    static public AppComponent getAppComponent()
    {
        return appComponent;
    }

    static public void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage)
    {
        appComponent = new Component(new JavaFXUtil(new ScreenShoter()));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
//        primaryStage.setFullScreen(true);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new MainScene(screen.getWidth(), screen.getHeight()));
//        primaryStage.setScene(new MainScene(256, 256));
//        primaryStage.setScene(new MainScene(0, 0));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/"+R.images.LAUNCHER)));
        primaryStage.setTitle(R.strings.APP_NAME);
//        primaryStage.initStyle(StageStyle.UTILITY);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        PlatformImpl.setTaskbarApplication(false);
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