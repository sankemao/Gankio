package sankemao.baselib.mvp;

import java.util.LinkedHashMap;

/**
 * Description:TODO
 * Create Time: 2017/11/10.11:09
 * Author:jin
 * Email:210980059@qq.com
 */
public class PresenterManager {

    private Builder mBuilder;

    private PresenterManager(Builder builder) {
        this.mBuilder = builder;
    }

    public  Object getPresenter(String key) {
        return mBuilder.mPresenters.get(key);
    }

    /**
     * P层与V层关联
     */
    public void attachView(IView view) {
        if (mBuilder.mPresenters != null) {
            for (String tag : mBuilder.mPresenters.keySet()) {
                BasePresenter presenter = mBuilder.mPresenters.get(tag);
                presenter.attatchView(view);
            }
        }
    }

    /**
     * 断开P层与V层的连接.
     */
    public void destory() {
        if (mBuilder.mPresenters != null) {
            for (String tag : mBuilder.mPresenters.keySet()) {
                BasePresenter presenter = mBuilder.mPresenters.get(tag);
                if (presenter != null) {
                    presenter.detachView();
                }
            }
        }
    }

    public static Builder begin() {
        return new Builder();
    }

    public static class Builder {
        private LinkedHashMap<String, BasePresenter> mPresenters;

        /**
         * 添加Presenter.
         */
        public Builder addPresenter(BasePresenter presenter) {
            if (mPresenters == null) {
                mPresenters = new LinkedHashMap<>();
            }
            mPresenters.put(presenter.getClass().getName(), presenter);
            return this;
        }

        public PresenterManager attach(IView v) {
            PresenterManager presenterManager = new PresenterManager(this);
            presenterManager.attachView(v);
            return presenterManager;
        }
    }

}
