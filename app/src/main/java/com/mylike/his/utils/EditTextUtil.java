package com.mylike.his.utils;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mylike.his.R;
import com.mylike.his.view.ClearEditText;

/**
 * Created by zhengluping on 2018/3/29.
 */

public class EditTextUtil {

    /**
     * 输入框有内容变化时，更改左边的图标
     *
     * @param editText  编辑框
     * @param tDrawable 有内容时的图标
     * @param fDrawable 无内容时的图标
     */
    public static void setDrawableleft(final EditText editText, final Drawable tDrawable, final Drawable fDrawable) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tDrawable.setBounds(0, 0, tDrawable.getIntrinsicWidth(), tDrawable.getIntrinsicHeight());
                fDrawable.setBounds(0, 0, fDrawable.getIntrinsicWidth(), fDrawable.getIntrinsicHeight());

                if (s.length() > 0) {
                    editText.setCompoundDrawables(tDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
                } else {
                    editText.setCompoundDrawables(fDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
