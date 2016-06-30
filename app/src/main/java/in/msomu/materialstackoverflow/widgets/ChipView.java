package in.msomu.materialstackoverflow.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.msomu.materialstackoverflow.R;

/**
 * Created by msomu on 24/05/16.
 * Single ChipView (CustomView)
 */

public class ChipView extends LinearLayout {
    private Context context;

    public ChipView(Context context) {
        super(context);
        this.context = context;
    }

    public ChipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews(context, attrs);
    }

    public void setText(String string) {
        LayoutInflater.from(context).inflate(R.layout.widget_tag, this);
        TextView textView = (TextView) this.findViewById(R.id.tag_text);
        textView.setText(string);
    }


    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChipView, 0, 0);
        String titleText = "";
        try {
            titleText = a.getString(R.styleable.ChipView_text);
        } finally {
            a.recycle();
        }
        setText(titleText);
    }
}
