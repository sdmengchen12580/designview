package org.faqrobot.textchat.two;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import org.faqrobot.textchat.R;



public class DesignViewGroup extends ViewGroup {

    private int childHorizontalSpace;
    private int childVerticalSpace;


    public DesignViewGroup(Context context) {
        super(context);
    }

    public DesignViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagsLayout);
        if (null != typedArray) {
            childHorizontalSpace = typedArray.getDimensionPixelSize(R.styleable.TagsLayout_tagHorizontalSpace, 0);
            childVerticalSpace = typedArray.getDimensionPixelSize(R.styleable.TagsLayout_tagVerticalSpace, 0);
            //使用完回收
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //父容器大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        int lineWidth = 0;        //记录每一行的宽度，width不断取最大宽度
        int lineHeight = 0;        //每一行的高度，累加至height
        int count = getChildCount();        //子控件个数
        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 遍历每个子元素
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            // 当前子控件实际占据的宽度
            int childWidth = child.getMeasuredWidth() + childHorizontalSpace;
            // 当前子控件实际占据的高度
            int childHeight = child.getMeasuredHeight() + childVerticalSpace;

            if (lp != null && lp instanceof MarginLayoutParams) {
                MarginLayoutParams params = (MarginLayoutParams) lp;
                childWidth += params.leftMargin + params.rightMargin;
                childHeight += params.topMargin + params.bottomMargin;
            }

            /**
             * 单行的宽度大于行宽
             */
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(lineWidth, childWidth); // 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
                child.setTag(new Location(left, top + height, childWidth + left - childHorizontalSpace, height + child.getMeasuredHeight() + top));
            } else {// 否则累加值lineWidth,lineHeight取最大高度
                child.setTag(new Location(left + lineWidth, top + height, lineWidth + childWidth - childHorizontalSpace + left, height + child.getMeasuredHeight() + top));
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }
        width = Math.max(width, lineWidth) + getPaddingLeft() + getPaddingRight();
        height += lineHeight;
        sizeHeight += getPaddingTop() + getPaddingBottom();
        height += getPaddingTop() + getPaddingBottom();
        //判断容器限制情况
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width,
                             (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            Location location = (Location) child.getTag();
            //子控件layout（左上右下的边距）
            child.layout(location.left, location.top, location.right, location.bottom);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public class Location {

        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int left;
        public int top;
        public int right;
        public int bottom;

    }


}
