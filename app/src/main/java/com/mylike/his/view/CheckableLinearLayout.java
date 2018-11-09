package com.mylike.his.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by zhengluping on 2018/11/8
 */
public class CheckableLinearLayout  extends LinearLayout implements Checkable {
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked = false;
    private OnClickListener mListener;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //点击的时候让控件变为选中状态，再次点击就取消选中状态
    public void init(){
        super.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle();
                if(mListener != null){
                    mListener.onClick(v);
                }
            }
        });
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        mListener = l;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean b) {
        if (b != mChecked) {
            mChecked = b;
            refreshDrawableState();
        }
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
