package stan.cudgel;

import javafx.application.Application;

import javafx.geometry.Rectangle2D;

import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import stan.cudgel.boxes.Cases;
import stan.cudgel.connection.OkHttp;
import stan.cudgel.di.AppComponent;
import stan.cudgel.di.Connection;
import stan.cudgel.di.FoldersAccess;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.di.Settings;
import stan.cudgel.managers.FoldersManager;
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
        FoldersAccess foldersAccess = new FoldersManager(System.getProperty("user.home") + "/" + R.strings.APP_LOCAL_PATH);
        appComponent = new Component(new JavaFXUtil(new ScreenShoter()),
                foldersAccess,
                new Cases(foldersAccess),
                new OkHttp());
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
        private FoldersAccess foldersAccess;
        private Settings settings;
        private Connection connection;

        Component(PlatformUtil pu, FoldersAccess fa, Settings ss, Connection cncn)
        {
            platformUtil = pu;
            foldersAccess = fa;
            settings = ss;
            connection = cncn;
        }

        public PlatformUtil getPlatformUtil()
        {
            return platformUtil;
        }
        public FoldersAccess getFoldersAccess()
        {
            return foldersAccess;
        }
        public Settings getSettings()
        {
            return settings;
        }
        public Connection getConnection()
        {
            return connection;
        }
    }
}