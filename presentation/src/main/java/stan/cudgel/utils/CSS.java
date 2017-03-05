package stan.cudgel.utils;

import javafx.scene.paint.Color;
import javafx.scene.effect.BlurType;
import javafx.scene.layout.BackgroundRepeat;

public class CSS
{
    private String style;

    public CSS()
    {
        this("");
    }
    private CSS(String s)
    {
        style = s;
    }

    public CSS copy()
    {
        return new CSS(style);
    }
    public CSS addClearFocusBorder()
    {
        style += "-fx-focus-color: transparent;-fx-background-insets: 0, 1, 2;";
        return this;
    }
    public CSS addFxEffectDropshadow(BlurType blurType, String color, int radius, int spread)
    {
        return addFxEffectDropshadow(blurType, color, radius, spread, 0, 0);
    }
    public CSS addFxEffectDropshadow(BlurType blurType, String color, int radius, int spread, int offsetX, int offsetY)
    {
        style += "-fx-effect: dropshadow(";
        switch(blurType)
        {
            case GAUSSIAN:
                style += "gaussian";
            break;
            case ONE_PASS_BOX:
                style += "one-pass-box";
            break;
            case THREE_PASS_BOX:
                style += "three-pass-box";
            break;
            case TWO_PASS_BOX:
                style += "two-pass-box";
            break;
        }
        style += ","+color+","+radius+","+spread+","+offsetX+","+offsetY+");";
        return this;
    }
    public CSS addFxBackgroundRadius(int radius)
    {
        style += "-fx-background-radius:" + radius + "px;";
        return this;
    }
    public CSS addFxBackgroundPosition(FxBackgroundPosition position)
    {
        switch(position)
        {
            case LEFT:
                style += "-fx-background-position:left;";
            break;
            case CENTER:
                style += "-fx-background-position:center;";
            break;
            case RIGHT:
                style += "-fx-background-position:right;";
            break;
            case TOP:
                style += "-fx-background-position:top;";
            break;
            case BOTTOM:
                style += "-fx-background-position:bottom;";
            break;
        }
        return this;
    }
    public CSS addFxBackgroundRepeat(BackgroundRepeat repeat)
    {
        switch(repeat)
        {
            case NO_REPEAT:
                style += "-fx-background-repeat:no-repeat;";
            break;
            case REPEAT:
                style += "-fx-background-repeat:repeat;";
            break;
            case ROUND:
                style += "-fx-background-repeat:round;";
            break;
            case SPACE:
                style += "-fx-background-repeat:space;";
            break;
        }
        return this;
    }
    public CSS addFxBackgroundImage(String path)
    {
        style += "-fx-background-image: url(\""+path+"\");";
        return this;
    }
    public CSS addFxBackgroundSize(double size)
    {
        return addFxBackgroundSize((int)size);
    }
    public CSS addFxBackgroundSize(int size)
    {
        style += "-fx-background-size:" + size + "px;";
        return this;
    }
    public CSS addFxFontSize(int size)
    {
        style += "-fx-font-size:" + size + "px;";
        return this;
    }
    public CSS addFxTextFill(String color)
    {
        style += "-fx-text-fill:" + color + ";";
        return this;
    }
    public CSS addFxPromptTextFill(String color)
    {
        style += "-fx-prompt-text-fill:" + color + ";";
        return this;
    }
    public CSS addFxBackgroundColor(String color)
    {
        style += "-fx-background-color:" + color + ";";
        return this;
    }
    public CSS addFxBorderColor(String t, String r, String b, String l)
    {
        style += "-fx-border-color:" + t + " " + r + " " + b + " " + l + ";";
        return this;
    }
    public CSS addFxBorderColor(String color)
    {
        return addFxBorderColor(color, color, color, color);
    }
    public CSS addFxBorderWidth(int t, int r, int b, int l)
    {
        style += "-fx-border-width:" + t + " " + r + " " + b + " " + l + ";";
        return this;
    }
    public CSS addFxBorderWidth(int width)
    {
        return addFxBorderWidth(width, width, width, width);
    }
    public CSS addFxBackgroundColor(String color, int percent)
    {
        style += "-fx-background-color:derive("+color+","+percent+"%);";
        return this;
    }
    public CSS addFxBackgroundColor(String color, double alpha)
    {
        return addFxBackgroundColor(Color.web(color,alpha));
    }
    public CSS addFxBackgroundColor(Color color)
    {
        style += "-fx-background-color:#"+color.toString().substring(2)+";";
        return this;
    }

    public String generate()
    {
        return style;
    }

    public enum FxBackgroundPosition
    {
        LEFT,
        CENTER,
        RIGHT,
        TOP,
        BOTTOM
    }
}