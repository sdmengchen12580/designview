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
        //总的宽度和高度
        int width = 0;
        int height = 0;

        //记录每一行的宽度-每一行的高度
        int lineWidth = 0;
        int lineHeight = 0;
        //子控件个数
        int count = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();


        // 遍历每个子元素
        for (int i = 0; i < count; i++) {


            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;


            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 当前子控件实际占据的宽度-高度（只加上间距距离）
            int childWidth = child.getMeasuredWidth() + childHorizontalSpace;
            int childHeight = child.getMeasuredHeight() + childVerticalSpace;


            // 得到child的lp（再加上margin间距距离）
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            if (lp != null && lp instanceof MarginLayoutParams) {
                MarginLayoutParams params = (MarginLayoutParams) lp;
                childWidth += params.leftMargin + params.rightMargin;
                childHeight += params.topMargin + params.bottomMargin;
            }


            //单行的宽度大于行宽------------    - getPaddingLeft() - getPaddingRight()
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(lineWidth, childWidth);
                // TODO: 2018/4/11 精华所在---重新开启新行，开始记录 
                lineWidth = childWidth;
                // 叠加当前高度
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
                child.setTag(new Location(left, top + height, childWidth + left - childHorizontalSpace, height + child.getMeasuredHeight() + top));
            }

            // 否则累加值lineWidth,lineHeight取最大高度
            else {
                child.setTag(new Location(
                        left + lineWidth,
                        top + height,
                        lineWidth + childWidth + left - childHorizontalSpace,
                        height + child.getMeasuredHeight() + top));
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }



        width = Math.max(width, lineWidth) + getPaddingLeft() + getPaddingRight();
        //height始终是少加一层高度
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
