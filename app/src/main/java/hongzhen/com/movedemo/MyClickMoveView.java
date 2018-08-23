package hongzhen.com.movedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyClickMoveView extends View {
    private int lastX;
    private int lastY;
    private String tag = "MyClickMoveView";
    private int x2;
    private int y2;

    public MyClickMoveView(Context context) {
        super(context);
    }

    public MyClickMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyClickMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyClickMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }




}
