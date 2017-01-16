package stan.cudgel.modules.cudgel;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;

public class CudgelPresenter
    implements CudgelContract.Presenter
{
    private CudgelContract.View view;

    private PlatformUtil platformUtil;

    public CudgelPresenter(CudgelContract.View v, PlatformUtil pu)
    {
        view = v;
        platformUtil = pu;
    }
}