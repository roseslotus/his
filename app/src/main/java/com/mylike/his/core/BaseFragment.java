package com.mylike.his.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * Created by zhengluping on 2018/1/16.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 跳转页面
     *
     * @param clz
     */
    protected void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    /**
     * 携带String的页面跳转(TAG)
     *
     * @param clz
     */
    protected void startActivity(Class<?> clz, String tag, String value) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (!TextUtils.isEmpty(value)) {
            intent.putExtra(tag, value);
        }
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
