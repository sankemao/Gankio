package sankemao.baselib.mvp.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.baselib.mvp.inject.InjectPresenter;

/**
 * Description:presenter注入代理
 * Create Time:2018/1/15.22:57
 * Author:jin
 * Email:210980059@qq.com
 */
public class MvpProxyImpl implements IMvpProxy {
    private List<BasePresenter> mPresenters;
    private IView mView;

    public MvpProxyImpl(IView view) {
        this.mView = view;
        mPresenters = new ArrayList<>();
    }

    @Override
    public void bindPresenter() {
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter annotation = field.getAnnotation(InjectPresenter.class);
            Class<? extends BasePresenter> presenterClazz = null;
            if (annotation != null) {
                presenterClazz = (Class<? extends BasePresenter>) field.getType();
                //和instanceof有点像， 只不过instance用于对象的判断，而isAssignableFrom用于class判断。
                if(!BasePresenter.class.isAssignableFrom(presenterClazz)){
                    // 这个 Class 是不是继承自 BasePresenter 如果不是抛异常
                    throw new RuntimeException("No support inject presenter type "+presenterClazz.getName());
                }
                //创建presenter
                BasePresenter presenter = null;
                try {
                    presenter = presenterClazz.newInstance();
                    presenter.attachView(mView);
                    field.setAccessible(true);
                    field.set(mView, presenter);
                    mPresenters.add(presenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                checkView(presenter);
            }
        }
    }

    /**
     * 检测mView是否都实现了presenter所要调用的View层的接口，即presenter泛型中的View接口。
     * @param presenter
     */
    private void checkView(BasePresenter presenter) {
        /**
         * getGenericSuperclass()返回直接继承的父类（包含泛型参数）
         * 此处返回具体presenter父辈basePresenter<V extends IView>
         */
        Type genericSuperclass = presenter.getClass().getGenericSuperclass();
        //ParameterizedType参数化类型，即泛型，它是type的一种
        ParameterizedType pt = (ParameterizedType) genericSuperclass;
        //泛型数组
        Type[] actualTypeArguments = pt.getActualTypeArguments();
        Class viewClazz = (Class)actualTypeArguments[0];

        //拿到view层所有接口，这里指activity或fragment或viewGroup
        Class<?>[] viewClasses = mView.getClass().getInterfaces();
        boolean isImplementsView = false;
        //遍历view层接口
        for (Class viewClass : viewClasses) {
            if (viewClass.isAssignableFrom(viewClazz)) {
                //view层实现了Presenter泛型中的View接口
                isImplementsView = true;
            }
        }
        if (!isImplementsView) {
            throw new RuntimeException(mView.getClass().getSimpleName() + " must implements " + viewClazz.getName());
        }
    }

    @Override
    public void unbindPresenter() {
        for (BasePresenter presenter : mPresenters) {
            presenter.detachView();
        }
        mView = null;
    }
}
