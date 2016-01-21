package com.wingsofts.honeycombview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

/**
 * Created by wing on 2016/1/21.
 */
public class HoneycombView extends View {


    //每行的个数
    private int mColumnsCount = 3;

    //行数
    private int mLineCount = 5;
    private float mStrokeWidth = 5;

    //存放颜色的list
    private ArrayList<Integer> mColorList;
    //存放文字的list
    private ArrayList<String> mTextList ;

    /**
     * 正六边形边长为a   高为sqrt(3)*a 宽为(1+sqrt(3))*a
     */
    private int mLength = 50;
    private Paint mPaint;
    private Paint mTextPaint;
    private int mCombWidth = 200;
    private int mCombHeight = 200;
    private Path mPath;

    public HoneycombView(Context context) {
        this(context, null);
    }

    public HoneycombView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoneycombView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
//        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPath = new Path();
        mColorList = new ArrayList<>();

        mColorList.add(Color.parseColor("#33B5E5"));

        mColorList.add(Color.parseColor("#AA66CC"));

        mColorList.add(Color.parseColor("#99CC00"));

        mColorList.add(Color.parseColor("#FFBB33"));

        mColorList.add(Color.parseColor("#FF4444"));

        mTextList = new ArrayList<>();
        for(int i =0;i<mLineCount*mColumnsCount;i++){
            mTextList.add("wing "+i);
        }

        mTextPaint = new Paint();
        mTextPaint.setTextSize(20);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.AT_MOST){
            widthSize = (int) ((3f*mColumnsCount+0.5f) *mLength);
        }else{
//            throw new IllegalStateException("only support wrap_content");
        }

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = (int) ((mLineCount/2f +0.5f) * (Math.sqrt(3) * mLength));
        }else{

//            throw new IllegalStateException("only support wrap_content");
        }


        setMeasuredDimension(widthSize,heightSize);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //正六边形的高
        float height = (float) (Math.sqrt(3) * mLength);
        for (int j = 0; j < mLineCount; j++) {

            mPaint.setColor(mColorList.get(j));

            if (j % 2 == 0) {

//                mPaint.setColor(Color.parseColor("#FFBB33"));

                for (int i = 0; i < mColumnsCount; i++) {
                    int txtId = j*3 +i;
                    //横坐标偏移量
                    float offset = mLength * 3 * i;
                    //左上角的x
                    float x = mLength / 2 + offset;
                    float y = j * height / 2;


                    mPath.reset();
                    getPath(height, x, y);

                    canvas.drawPath(mPath, mPaint);
                    float txtLength = mTextPaint.measureText(mTextList.get(txtId));
                    canvas.drawText(mTextList.get(txtId),x+mLength/2-txtLength/2,y+height/2+5, mTextPaint);

                }
            } else {

//                mPaint.setColor(Color.parseColor("#AA66CC"));

                for (int i = 0; i < mColumnsCount; i++) {

                    int txtId = j*3 +i;
                    float offset = mLength * 3 * i;
                    float x = mLength * 2 + offset;
                    float y = (height / 2) * j;
                    mPath.reset();
                    getPath(height, x, y);
                    canvas.drawPath(mPath, mPaint);
                    float txtLength = mTextPaint.measureText(mTextList.get(txtId));
                    canvas.drawText(mTextList.get(txtId),x+mLength/2-txtLength/2,y+height/2+5, mTextPaint);

                }



            }


        }


    }

    //根据左上角一点 绘制整个正六边形
    private void getPath(float height, float x, float y) {
        mPath.moveTo(x, y);
        mPath.lineTo(x - mLength / 2, height / 2 + y);
        mPath.lineTo(x, height + y);
        mPath.lineTo(x + mLength, height + y);
        mPath.lineTo((float) (x + 1.5 * mLength), height / 2 + y);
        mPath.lineTo(x + mLength, y);
        mPath.lineTo(x, y);
        mPath.close();
    }

    /**
     * 设置列数
     * @param mColumnsCount
     */
    public void setColumnsCount(int mColumnsCount) {
        this.mColumnsCount = mColumnsCount;
        invalidate();
    }

    /**
     * 设置行数
     * @param mLineCount
     */
    public void setLineCount(int mLineCount) {
        this.mLineCount = mLineCount;

        invalidate();
    }

    /**
     * 设置文本数据
     */
    public void setTextList(ArrayList<String> textList) {
        mTextList.clear();
        mTextList.addAll(textList);

        invalidate();
    }

    /**
     * 设置颜色数据
     * @param colorList
     */
    public void setColorList(ArrayList<Integer> colorList) {
        mColorList.clear();
        mColorList.addAll(colorList);

        invalidate();
    }
}
