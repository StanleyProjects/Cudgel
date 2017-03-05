package stan.cudgel.managers;

import java.io.File;

import stan.cudgel.di.FoldersAccess;

public class FoldersManager
    implements FoldersAccess
{
    private String filesDirectory;

    public FoldersManager(String fd)
    {
        filesDirectory = fd;
        checkRoot();
    }
    private void checkRoot()
    {
        File files = new File(filesDirectory);
        files.mkdirs();
        if(files.exists())
        {
            System.out.println("files"
                    +"\n\t"+"directory - " + filesDirectory);
        }
        else
        {
            System.out.println("files"
                    +"\n\t"+"create files directory failed!!!");
            return;
        }
        File database = new File(getDatabasePath());
        database.mkdirs();
        if(database.exists())
        {
            System.out.println("database"
                    +"\n\t"+"directory - " + getDatabasePath());
        }
        else
        {
            System.out.println("database"
                    +"\n\t"+"create database directory failed!!!");
            return;
        }
        File media = new File(getMediaPath());
        media.mkdirs();
        if(media.exists())
        {
            System.out.println("media"
                    +"\n\t"+"directory - " + getMediaPath());
        }
        else
        {
            System.out.println("media"
                    +"\n\t"+"create media directory failed!!!");
            return;
        }
        File images = new File(getImagesPath());
        images.mkdirs();
        if(images.exists())
        {
            System.out.println("images"
                    +"\n\t"+"directory - " + getImagesPath());
        }
        else
        {
            System.out.println("images"
                    +"\n\t"+"create images directory failed!!!");
            return;
        }
        File screenshots = new File(getScreenshotsPath());
        screenshots.mkdirs();
        if(screenshots.exists())
        {
            System.out.println("screenshots"
                    +"\n\t"+"directory - " + getScreenshotsPath());
        }
        else
        {
            System.out.println("screenshots"
                    +"\n\t"+"create screenshots directory failed!!!");
            return;
        }
    }

    public String getFilesDirectory()
    {
        return filesDirectory;
    }
    public String getDatabasePath()
    {
        return filesDirectory + "/database";
    }
    public String getMediaPath()
    {
        return filesDirectory + "/media";
    }
    public String getImagesPath()
    {
        return getMediaPath() + "/images";
    }
    public String getScreenshotsPath()
    {
        return getImagesPath() + "/screenshots";
    }
}