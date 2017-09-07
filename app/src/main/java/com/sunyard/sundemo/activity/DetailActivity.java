package com.sunyard.sundemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sunyard.sundemo.R;
import com.sunyard.sundemo.common.base.ActivityUtils;
import com.sunyard.sundemo.common.base.BaseActivity;
import com.sunyard.sundemo.model.SunRepository;
import com.sunyard.sundemo.presenter.DetailPresenter;
import com.sunyard.sundemo.view.fragment.DetailFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailActivity extends BaseActivity {
    public static final String PARAM_JSON = "param_json";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail);
        ButterKnife.inject(this);

        //init title
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("商品详情");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //get intent
        String json = getIntent().getStringExtra(PARAM_JSON);

        //load fragment
        DetailFragment detailFragment = DetailFragment.newInstance(json);
        ActivityUtils.addFragmentToFrame(getSupportFragmentManager(), detailFragment, R.id.detailFrame, DetailFragment.TAG);

        //create presenter
        new DetailPresenter(detailFragment, new SunRepository());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
