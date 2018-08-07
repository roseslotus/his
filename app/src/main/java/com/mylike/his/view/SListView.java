package com.mylike.his.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zhengluping on 2018/5/31.
 */

public class SListView extends ListView {

    public SListView(Context context) {
        super(context);
    }

    public SListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
