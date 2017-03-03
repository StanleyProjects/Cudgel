package stan.cudgel.modules.cudgel;

import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.shape.Circle;

import stan.cudgel.utils.CSS;
import stan.cudgel.R;

public class CudgelButton
    extends Button
{
	private interface Styles
	{
		CSS main = new CSS()
                .addClearFocusBorder()
    			.addFxEffectDropshadow(BlurType.THREE_PASS_BOX, R.colors.BLACK, 3, 0, 0, 0)
    			.addFxBackgroundRadius(MAX_SIZE/2)
    			.addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
    			.addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT);
		String disable = main.copy()
    			.addFxBackgroundImage(R.images.MIC_OFF)
    			.addFxBackgroundSize(24)
    			.addFxBackgroundColor("#9E9E9E")
    			.generate();
		CSS none = main.copy()
    			.addFxBackgroundImage("cudgel/mic_none_m.png")
    			.addFxBackgroundSize(36);
		String none_normal = none.copy()
    			.addFxBackgroundColor(R.colors.WHITE)
    			.generate();
		String none_hover = none.copy()
    			.addFxBackgroundColor("#E0E0E0")
    			.generate();
		String none_pressed = none.copy()
    			.addFxBackgroundColor("#BDBDBD")
    			.generate();
	}

	static private final int MIN_SIZE = 56;
	static private final int MAX_SIZE = 72;

	private boolean enable = false;

    public CudgelButton()
    {
        super();
        init();
    }
    private void init()
    {
    	setDisable();
    	//setNone();
    }

    public void setDisable()
    {
    	setSize(MIN_SIZE);
    	setStyle(Styles.disable);
        //setId("cudgel_button_disable");
    }
    public void setNone()
    {
    	enable = false;
    	setSize(MIN_SIZE);
    	setStyle(Styles.none_normal, Styles.none_hover, Styles.none_pressed);
        //setId("cudgel_button_none");
    }
    public void setEnable()
    {
    	enable = true;
    	setSize(MIN_SIZE);
        //setId("cudgel_button_enable");
    }

    public void changeSize(int percent)
    {
    	if(!enable)
    	{
    		return;
    	}
    	if(percent < 0)
    	{
    		percent = 0;
    	}
    	if(percent > 100)
    	{
    		percent = 100;
    	}
    	double d = MAX_SIZE-MIN_SIZE;
    	d /= 100;
    	d *= percent;
    	setSize(MIN_SIZE + (int)d);
    }

    private void setSize(int s)
    {
        setMinSize(s, s);
    }

    private void setStyle(String normal, String hover, String pressed)
    {
        setStyle(normal);
        addEventHandler(MouseEvent.MOUSE_PRESSED, event -> setStyle(pressed));
        addEventHandler(MouseEvent.MOUSE_RELEASED, event -> setStyle(isHover() ? hover : normal));
        addEventHandler(MouseEvent.MOUSE_ENTERED, event -> setStyle(hover));
        addEventHandler(MouseEvent.MOUSE_EXITED, event -> 
        {
            if(!isPressed())
            {
                setStyle(normal);
            }
        });
    }
}