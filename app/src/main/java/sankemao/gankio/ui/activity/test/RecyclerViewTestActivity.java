package sankemao.gankio.ui.activity.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.gankio.R;

public class RecyclerViewTestActivity extends BaseActivity {
    @BindView(R.id.rv_test)
    RecyclerView mRvTest;

    private List<String> mShowItems = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view_test;
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRvTest.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter testAdapter = new TestAdapter(this, mShowItems);
        View headView = this.getLayoutInflater().inflate(R.layout.item_test_rv_head, mRvTest, false);
        TextView tv = headView.findViewById(R.id.tv_content);
        tv.setText("我是头布局");

        View footView = this.getLayoutInflater().inflate(R.layout.item_test_rv_foot, mRvTest, false);
        TextView footTv = footView.findViewById(R.id.tv_content);
        footTv.setText("我是尾布局");

        testAdapter.addHeaderView(headView);
        testAdapter.addFooterView(footView);
        mRvTest.setAdapter(testAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 50; i++) {
            mShowItems.add(i + "");
        }
    }
}
