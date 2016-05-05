package licola.demo.com.huabandemo.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 * 流式布局父控件 测量每个子控件大小 Margin 安排合理的位置
 */
public class FlowLayout extends ViewGroup {
    /**
     * new 出来时调用
     */
    public FlowLayout(Context context) {
        this(context, null);
    }

    /**
     * 用了非自定义属性方法
     */
    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 自定义属性时调用
     */
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<List<View>> mAllViews = new ArrayList<>(); //存放所有的子view
    private List<Integer> mLineheight = new ArrayList<>();  //存放每行的高度

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineheight.clear();

        int width = getWidth();     //获取viewGroup宽度

        int lineHeight = 0;
        int lineWidth = 0;

        List<View> lineViews = new ArrayList<>();

        /**
         * 获取每行的高度和view
         */
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果需要换行
            if (lineWidth + childWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                //记录当前行行高
                mLineheight.add(lineHeight);
                //记录当前行views
                mAllViews.add(lineViews);

                //重置行高行宽
                lineWidth = 0;
                lineHeight = childHeight + lp.bottomMargin + lp.topMargin;

                //重置view集合
                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.bottomMargin + lp.topMargin);
            lineViews.add(child);

        }
        //处理最后一行
        mLineheight.add(lineHeight);
        mAllViews.add(lineViews);

        /**
         * 设置子View的位置
         */
        int left = getPaddingLeft();
        int top = getPaddingTop();

        //行数
        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineheight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);

                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int rb = tc + child.getMeasuredHeight();
                //为子view进行布局
                child.layout(lc, tc, rc, rb);
                //布局完后改变left
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            //每一行布局完成之后进行改变
            left = getPaddingLeft();
            top += lineHeight;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content时需要计算高度
        int width = 0;
        int height = 0;

        //记录每一行的宽度和高度
        int lineHeight = 0;
        int lineWidth = 0;

        /**
         * 计算宽高, width, height
         */
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //子view的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            int childHeight = child.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置行宽
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else {    //未换行情况
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            //当到最后一个控件时，要高度要加上，宽度要是行宽
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        //当mode为wrap_content时，用计算的宽高;为match_parent时，用父类的宽高
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingRight() + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingBottom() + getPaddingTop());

    }


    /**
     * 当前viewGroup使用的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
