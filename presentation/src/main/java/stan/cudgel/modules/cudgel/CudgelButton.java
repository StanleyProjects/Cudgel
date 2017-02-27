package stan.cudgel.modules.cudgel;

import javafx.scene.control.Button;

public class CudgelButton
    extends Button
{
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
    }

    public void setDisable()
    {
    	setSize(MIN_SIZE);
        //setId("cudgel_button_disable");
    }
    public void setNone()
    {
    	enable = false;
    	setSize(MIN_SIZE);
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
}