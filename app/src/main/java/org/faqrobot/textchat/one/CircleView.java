package org.faqrobot.textchat.one;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView extends View {

    public static final String TAG = "CircleView";

    //画笔
    private Paint mPaint;
    //父容器的矩形
    private RectF oval;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //初始化画笔，矩形
    private void init() {
        mPaint = new Paint();
        oval = new RectF();
    }

    //测量大小的方法
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取容器填充子布局的模式  -2147483648
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取大小 1080
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //获取容器填充子布局的模式  -2147483648
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取大小 1866
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.e(TAG, "onMeasure: " + widthMode + "");
        Log.e(TAG, "onMeasure: " + widthSize + "");
        Log.e(TAG, "onMeasure: " + heightMode + "");
        Log.e(TAG, "onMeasure: " + heightSize + "");

        //3种模式
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
    }

    //如何摆放
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    //绘图的方法
    // TODO: 2018/3/30 0度是3点钟的方向-----顺时针的方向
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
        //FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //获取宽高
        int with = getWidth();
        int height = getHeight();
        Log.e(TAG, "onDraw---->" + with + "*" + height);
        //半径
        float radius = with / 4;
        //宽；高；半径；画笔  (圆形圆心已经确认-正方形的中心)
        canvas.drawCircle(with / 2, with / 2, radius, mPaint);
        mPaint.setColor(Color.BLUE);
        //用于父容器大小的界限-圆形所在的范围
        oval.set(with / 2 - radius, with / 2 - radius,
                 with / 2 + radius, with / 2 + radius);
        //根据进度画圆弧（圆弧的父容器；圆弧的起始角度；圆弧的角度；半径连线；画笔）
        canvas.drawArc(oval, 0, 30, true, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(oval, 90, 30, true, mPaint);
    }

}
