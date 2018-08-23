package hongzhen.com.movedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String tag = "MainActivity";
    //MyClickMoveView添加setOnTouchListener（）重写onTouch()方法，实现滑动、点击、长按
    private MyClickMoveView ClickMoveView;
    //MyView重写onTouchEvent()方法，实现滑动、点击
    private MyView myView;
    private boolean isDispatch = false;
    private int downX;
    private int downY;
    private int screenWidth;
    private int screenHeight;
    private boolean isClick = true;//点击事件，点击为true，拖动为false
    private boolean longClick = false;
    private View content;
    private boolean hasMeasured = false;
    private int startTime;
    private int endTime;
    private View parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClickMoveView = findViewById(R.id.my_click_move_view);
        myView = findViewById(R.id.my_view);
        //获取显示屏属性
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        content = getWindow().findViewById(Window.ID_ANDROID_CONTENT);//获取界面的ViewTree根节点
        //获取myView的父控件
        parent = (View) ClickMoveView.getParent();
        ViewTreeObserver vto = content.getViewTreeObserver();//获取ViewTree的监听器
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!hasMeasured) {
                    // screenHeight = content.getMeasuredHeight();//获取ViewTree的高度
                    screenHeight = parent.getMeasuredHeight();//获取父控件的高度
                    hasMeasured = true;//设置为true，使其不再被测量。
                }
                return true;//如果返回false，界面将为空。
            }

        });

        ClickMoveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick) {
                    Log.i(tag, "onClick");
                    Toast.makeText(MainActivity.this, "点击",
                            Toast.LENGTH_SHORT).show();
                    Log.i(tag, "点击");
                }
            }
        });

        ClickMoveView.setOnTouchListener(new View.OnTouchListener() {
            // 记录移动的最后的位置
            int lastX, lastY;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //获取事件类型
                int ea = event.getAction();
                switch (ea) {
                    // 按下事件
                    case MotionEvent.ACTION_DOWN:
                        startTime = (int) System.currentTimeMillis();
                        Log.i(tag, "Down-" + event.getRawX());
                        Log.i(tag, "Down-" + startTime);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        downX = lastX;
                        downY = lastY;
                        break;
                    // 拖动事件
                    case MotionEvent.ACTION_MOVE:
                        // 移动中动态设置位置
                        Log.i(tag, "MOVE-" + event.getRawX());
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = view.getLeft() + dx;
                        int top = view.getTop() + dy;
                        int right = view.getRight() + dx;
                        int bottom = view.getBottom() + dy;
                        //限定view被拖动的范围
                        if (left < 0) {
                            left = 0;
                            right = left + view.getWidth();
                        }
                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - view.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + view.getHeight();
                        }
                        if (bottom > screenHeight) {
                            bottom = screenHeight;
                            top = bottom - view.getHeight();
                        }
                        //限定view被拖动的范围，更新位置，实现拖动
                        view.layout(left, top, right, bottom);
                        // 重置当前的位置
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endTime = (int) System.currentTimeMillis();
                        Log.i(tag, "UP-" + event.getRawX());
                        Log.i(tag, "UP-" + (endTime - startTime));
                        //判断是单击事件或是拖动事件，偏移量大于15则断定为拖动事件
                        if (Math.abs((int) (event.getRawX() - downX)) > 15
                                || Math.abs((int) (event.getRawY() - downY)) > 15) {
                            isClick = false;
                            longClick = false;
                        } else {
                            //大于600毫秒为长按，否则是点击
                            if ((endTime - startTime) > 600) {
                                longClick = true;
                                isClick = false;
                            } else {
                                longClick = false;
                                isClick = true;
                            }
                        }
                        break;
                    default:
                }
                //返回true,点击事件无效，长按无效。父控件的dispatchTouchEvent，onInterceptTouchEvent都执行
//                return true;
                //返回false，点击事件有效，长按有效。父控件的dispatchTouchEvent，onInterceptTouchEvent都执行
                return false;
            }
        });
        /**
         * 长按监听
         */
        ClickMoveView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClick) {
                    Toast.makeText(MainActivity.this, "长按", Toast.LENGTH_LONG).show();
                    Log.i(tag, "长按");
                }
                return false;
            }
        });

        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("myView","onTouch");
                //返回true，自己的onTouchEvent方法不执行，点击事件无效
                return false;
//                return false;//自己的onTouchEvent方法执行，点击事件在onTouchEvent中配置，否则无效
            }
        });
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("myView","onClick");
                if (myView.isClick){
                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_LONG).show();
                }
            }
        });
        //长按无响应，暂不明确原因
        myView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("myView","onLongClick");
                if (myView.isClick){
                    Toast.makeText(MainActivity.this, "长按", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(tag, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

}
