package com.example.shiji_xc.statusbaremo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by shiji-xc on 2017/8/9.
 */

public class StatusBarUtil {

    public static void setColor(Activity context, int color){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            context.getWindow().setStatusBarColor(color);
        }else {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
            int childCount = decorView.getChildCount();
            if(childCount>0&&decorView.getChildAt(childCount-1) instanceof StatusBarView){
                decorView.getChildAt(childCount-1).setBackgroundColor(color);
            }else {
                StatusBarView statusBarView = creatStatusBar(context, color);
                decorView.addView(statusBarView);
            }
        }
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(identifier);
    }

    /**
     * 创建一个和状态栏一样搞定的view将其插入到最上层的view上（4.4兼容方案）
     * @param context
     * @param color
     * @return
     */
    private static StatusBarView creatStatusBar(Context context,int color){
        StatusBarView statusBarView=new StatusBarView(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(context));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * 计算状态栏颜色(本人也不知道具体算法是为什么，所以不要问我)
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
