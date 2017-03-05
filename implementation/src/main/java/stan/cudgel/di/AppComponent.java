package stan.cudgel.di;

public interface AppComponent
{
    PlatformUtil getPlatformUtil();
    FoldersAccess getFoldersAccess();
    Settings getSettings();
    Connection getConnection();
}