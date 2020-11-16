package com.veveup.adream_2048;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by veve on 14/10/2017.
 */

/**
 * 每一个数字方框的实体类
 */

public class Card extends FrameLayout {
        private TextView label;
    public Card(@NonNull Context context) {
        super(context);
        label =new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33FFFFFF);
        label.setGravity(Gravity.CENTER);

        LayoutParams layoutParams =new LayoutParams(-1,-1);
        layoutParams.setMargins(10,10,0,0);
        addView(label,layoutParams);

        setNum(0);



    }
    private int num=0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if(num<=0){
            label.setText("");
        }else {
            label.setText(num+"");
        }
    }
    public boolean equals(Card s){
        return getNum()==s.getNum();
    }

}
