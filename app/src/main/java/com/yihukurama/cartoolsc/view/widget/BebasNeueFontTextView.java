package com.yihukurama.cartoolsc.view.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dengshuai on 16/4/2.
 */
public class BebasNeueFontTextView extends TextView {
    public BebasNeueFontTextView(Context context) {
        super(context);
        initFont();
    }

    public BebasNeueFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public BebasNeueFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();

    }


    private void initFont(){
        AssetManager assertMgr = getContext().getAssets();
        Typeface font = Typeface.createFromAsset(assertMgr, "fonts/BebasNeueBold.otf");
        setTypeface(font);
    }
}
