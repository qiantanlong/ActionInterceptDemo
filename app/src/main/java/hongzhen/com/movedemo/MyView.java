package hongzhen.com.movedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    private String tag="Myview";
    private int lastX;
    private int lastY;
    private int x2;
    private int y2;
    private int startTime;
    private int endTime;
    private int downX;
    private int downY;
    public boolean isClick;
    private boolean longClick;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 重写onTouchEvent这个方法后，view背景的点击状态背景无效。设置onTouchListener后，可以实现同样效果，view背景的点击状态背景有效
     * @param event
     * @return
     */
     @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(tag, "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://处理手指按下事件
                Log.i(tag, "ACTION_DOWN");
                startTime = (int) System.currentTimeMillis();
                // 记录触摸点坐标
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                downX = lastX;
                downY = lastY;
                Log.i(tag, "ACTION_DOWN"+ lastX);
                break;
            case MotionEvent.ACTION_MOVE://处理手指移动事件
                // 计算偏移量
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                // 在当前left、top、right、bottom的基础上加上偏移量
                layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(tag, "ACTION_UP");
                endTime = (int) System.currentTimeMillis();
                x2 = (int) event.getX();
                y2 = (int) event.getY();
                if (Math.abs((int) (event.getRawX() - downX)) > 15
                        || Math.abs((int) (event.getRawY() - downY)) > 15) {
                    //滑动
                    Log.i(tag, "ACTION_UP---滑动");
                    isClick = false;
                    longClick = false;
                } else {
                    Log.i(tag, "ACTION_UP--点击");
                    //点击
                    if ((endTime - startTime) > 600) {
                        longClick = true;
                        isClick = false;
                    } else {
                        longClick = false;
                        isClick = true;
                    }
                    performClick();
                }
                break;
            default:
        }
        return true;
//        return true;//返回true表示本控件消费触摸事件,返回false表示本控件不消费触摸事件,可以滑动
//        return false;//返回false表示本控件消费触摸事件,返回false表示本控件不消费触摸事件，不可以滑动
    }
}
