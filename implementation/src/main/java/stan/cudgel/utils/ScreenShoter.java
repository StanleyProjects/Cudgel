package stan.cudgel.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import stan.cudgel.di.PlatformUtil;

public class ScreenShoter
        implements PlatformUtil.ScreenShoter
{
    private Robot robot;

    public ScreenShoter()
    {
        try
        {
            robot = new Robot();
        }
        catch(AWTException e)
        {
        }
    }

    public byte[] take(int x, int y, int w, int h)
    {
        BufferedImage image = robot.createScreenCapture(new Rectangle(x, y, w, h));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "png", output);
            return output.toByteArray();
        }
        catch(IOException e)
        {
        }
        return null;
    }
}