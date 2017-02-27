package stan.cudgel.modules.cudgel;

import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.units.mvp.Presenter;

public class CudgelPresenter
    extends Presenter<CudgelContract.View>
    implements CudgelContract.Presenter
{
    private PlatformUtil platformUtil;

    public CudgelPresenter(CudgelContract.View v, PlatformUtil pu)
    {
        super(v);
        platformUtil = pu;
    }
}