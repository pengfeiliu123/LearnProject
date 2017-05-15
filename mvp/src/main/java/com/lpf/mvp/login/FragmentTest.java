package com.lpf.mvp.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lpf.mvp.R;
import com.lpf.mvp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTest extends BaseFragment<LoginPresenter, LoginModel> implements LoginContract.View {


    @BindView(R.id.fragment_ok)
    Button fragmentOk;
    Unbinder unbinder;
    @BindView(R.id.progressBar_)
    ProgressBar progressBar;

    public FragmentTest() {
        // Required empty public constructor
    }

    @Override
    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void success() {
        Toast.makeText(mActivity, "hi, Fragment request success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed() {

    }

    @Override
    public void clear() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragment_ok)
    public void onViewClicked() {
        mPresenter.rxLogin("lpf", "123");
    }
}
