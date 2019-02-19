package com.mylike.his.doctor.popup;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.entity.DropdownsBean;
import com.mylike.his.entity.SelectsBean;
import com.mylike.his.view.FlowLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;


import java.util.List;


/**
 * Created by thl on 2018/8/7.
 */

public class ChoiceZhuyuanShuaixuanPopupMenu  extends DialogFragment{

    private LinearLayout llContent;
    private TextView mBtnSubmit;
    private TextView mBtnReset;


    private List<DropdownsBean> mDatas;

    public ChoiceZhuyuanShuaixuanPopupMenu setDatas(List<DropdownsBean> datas){
        this.mDatas=datas;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.dialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View rootView= LayoutInflater.from(getActivity()).inflate(R.layout.popup_zhuyuan_shuaixuan_search,null,false);
        dialog.setContentView(rootView);
        initView(rootView);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        WindowManager wm = (WindowManager) getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.RIGHT; // 紧贴右边
        lp.width = (int) (size.x * 0.9); // 宽度持平
        window.setWindowAnimations(R.style.right_popup_animation);
        window.setAttributes(lp);
        setCancelable(true);

        return dialog;
    }

    private void initView(View rootView) {
        llContent = rootView.findViewById(R.id.ll_content);
        mBtnReset = (TextView) rootView.findViewById(R.id.btn_reset);
        mBtnSubmit = (TextView) rootView.findViewById(R.id.btn_submit);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTypeBody();
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
    }


    public void addTypeBody(){

        llContent.removeAllViews();
        for (final DropdownsBean bean : mDatas) {
            if (bean.getSelects() != null) {
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                final TextView categoryTextView = new TextView(getActivity());
                categoryTextView.setText(bean.getName());
                categoryTextView.setTextSize(14);
                llContent.addView(categoryTextView,params1);

               FlowLayout flowLayout =new FlowLayout(getActivity(),null);
               llContent.addView(flowLayout);

                for (SelectsBean selectsBean : bean.getSelects()) {
                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final TextView tvFocusName = new TextView(getActivity());
                    tvFocusName.setText(selectsBean.getValue());
                    tvFocusName.setTag(selectsBean);
                    tvFocusName.setTextSize(13);
                    tvFocusName.setGravity(Gravity.CENTER);
                    tvFocusName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tvFocusName.setBackgroundResource(R.drawable.shape_green_round20);
                            tvFocusName.setTextColor(Color.parseColor("#ffffff"));
                        }
                    });


                    tvFocusName.setBackgroundResource(R.drawable.shape_dark_gray_round20);
                    tvFocusName.setTextColor(Color.parseColor("#575757"));


                    tvFocusName.setPadding(DensityUtil.dp2px(14),DensityUtil.dp2px(7),DensityUtil.dp2px(14),DensityUtil.dp2px(7));

                    LinearLayout linearLayout = new LinearLayout(getActivity());
                    linearLayout.setPadding(DensityUtil.dp2px(6), DensityUtil.dp2px(6), DensityUtil.dp2px(6), DensityUtil.dp2px(6));
                    linearLayout.addView(tvFocusName,params2);

//            int width=searchChildListShowEntity.name.trim().length()>=0?LinearLayout.LayoutParams.WRAP_CONTENT:DensityUtil.dp2px(80);/

                    LinearLayout.LayoutParams llparams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    flowLayout.addView(linearLayout,llparams);
                }
            }

        }

    }

    public interface ISearchListener{
        void search();
    }
}
