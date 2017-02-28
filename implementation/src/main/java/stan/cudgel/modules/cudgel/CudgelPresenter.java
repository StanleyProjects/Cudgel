package stan.cudgel.modules.cudgel;

import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.units.mvp.Presenter;

public class CudgelPresenter
    extends Presenter<CudgelContract.View>
    implements CudgelContract.Presenter
{
    public CudgelPresenter(CudgelContract.View v)
    {
        super(v);
    }
}