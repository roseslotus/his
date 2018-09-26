package com.mylike.his.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.IntentionAddEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/9/25
 * 控件工具
 */
public class ViewUtil<T> {
    //-----------------------------------------------------------三级意向-------------------------------------------------------------------------
    //数据
    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();//一级意向
    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();//二级意向
    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();//三级意向

    //初始化意向
    public OptionsPickerView initIntention(Context context, OptionsPickerView IntentionPV, List<IntentionEntity> intentionEntities) {
        this.intentionEntities1.addAll(intentionEntities);
        for (int i = 0; i < intentionEntities1.size(); i++) {
            List<IntentionEntity> intentionEntityList2 = new ArrayList<>();//二级意向
            List<List<IntentionEntity>> intentionEntityList3 = new ArrayList<>();//三级意向
            //在第一项添加空意向，如果选择“请选择”则代表此级意向为空
            intentionEntityList2.add(new IntentionEntity("请选择"));
            //如果无意向，添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃
            if (intentionEntities1.get(i).getChildren().size() == 0) {
                intentionEntityList3.add(intentionEntityList2);
            }
            for (int j = 0; j < intentionEntities1.get(i).getChildren().size(); j++) {
                //添加二级意向
                intentionEntityList2.add(intentionEntities1.get(i).getChildren().get(j));

                //如果二级意向循环第一次，这为三级意向添加一个空对象，对应二级意向的“请选择”
                if (j == 0) {
                    List<IntentionEntity> IList = new ArrayList<>();
                    IList.add(new IntentionEntity("请选择"));
                    intentionEntityList3.add(IList);
                }

                //添加三级意向
                List<IntentionEntity> IList3 = new ArrayList<>();
                IList3.add(new IntentionEntity("请选择"));
                if (intentionEntities1.get(i).getChildren().get(j).getChildren() != null || intentionEntities1.get(i).getChildren().get(j).getChildren().size() != 0) {
                    IList3.addAll(intentionEntities1.get(i).getChildren().get(j).getChildren());
                }

                intentionEntityList3.add(IList3);
            }
            intentionEntities2.add(intentionEntityList2);
            intentionEntities3.add(intentionEntityList3);
        }

        //初始化意向选择器
        IntentionPV = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//选择项
                IntentionAddEntity intentionAddEntity = new IntentionAddEntity();
                String intentionValue = "";
                if (!TextUtils.isEmpty(intentionEntities1.get(options1).getPbtid())) {
                    intentionAddEntity.setItemFirst(intentionEntities1.get(options1).getPbtid());
                    intentionValue = intentionValue + intentionEntities1.get(options1).getPbtname();
                }
                if (!TextUtils.isEmpty(intentionEntities2.get(options1).get(options2).getPbtid())) {
                    intentionAddEntity.setItemSecond(intentionEntities2.get(options1).get(options2).getPbtid());
                    intentionValue = intentionValue + "/" + intentionEntities2.get(options1).get(options2).getPbtname();
                }
                if (!TextUtils.isEmpty(intentionEntities3.get(options1).get(options2).get(options3).getPbtid())) {
                    intentionAddEntity.setItemThird(intentionEntities3.get(options1).get(options2).get(options3).getPbtid());
                    intentionValue = intentionValue + "/" + intentionEntities3.get(options1).get(options2).get(options3).getPbtname();
                }
                intentionAddEntity.setIntentionStr(intentionValue);
                getIntentValue(intentionAddEntity);
            }
        })
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(context.getResources().getColor(R.color.green_50))//确定字体颜色
                .setCancelColor(context.getResources().getColor(R.color.gray_49))//取消字体样式
                .setDividerColor(context.getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .isRestoreItem(true)//还原第一项
                .build();
        IntentionPV.setPicker(intentionEntities1, intentionEntities2, intentionEntities3);//设置数据

        return IntentionPV;
    }

    //意向点击确认后的回调
    private OnIntentionListener onIntentionListener; //监听选中值的回调

    public interface OnIntentionListener {
        void onOptionsSelect(IntentionAddEntity intentionAddEntity);//返回意向对象
    }

    public ViewUtil setIntentionListener(OnIntentionListener intentionListener) {
        this.onIntentionListener = intentionListener;
        return this;
    }

    public void getIntentValue(IntentionAddEntity intentionAddEntity) {
        if (onIntentionListener != null) {
            onIntentionListener.onOptionsSelect(intentionAddEntity);
        }
    }
}
