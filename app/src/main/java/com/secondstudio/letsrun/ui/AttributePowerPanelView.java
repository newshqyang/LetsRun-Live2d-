package com.secondstudio.letsrun.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.secondstudio.letsrun.R;
import com.secondstudio.letsrun.model.TaskList;

public class AttributePowerPanelView extends View {
    //五边形玩家属性能力图

    //——————我们必须给的模拟数据————————
    //n边形
    private int n = 5;
    //显示文字
    private String[] text = new String[]{
            getResources().getString(R.string.attribute1)+" ",
            getResources().getString(R.string.attribute2)+" ",
            getResources().getString(R.string.attribute3)+" ",
            getResources().getString(R.string.attribute4)+" ",
            getResources().getString(R.string.attribute5)+" "};
    //区域等级，值不能超过n边形的个数

    private int[] area;
    private void getStanford(){
        //获取最大值
        int max = area[0];
        for (int i=1;i<5;i++){
            if (max < area[i]){
                max = area[i];
            }
        }
        if (max==0){    //防止出现0除以0的情况
            max = 1;
        }
        //算出相对于阈值5来说的值
        for (int i=0;i<5;i++){
            if (max <= num){      //如果最大值都不超过5的话，就不进行成比例约束
                break;
            }
            area[i] = area[i] * num / max;
        }
    }

    //——————View相关——————————
    //View自身的宽和高
    private int mWidth;
    private int mHeight;

    //——————画笔相关——————————
    //边框的画笔
    private Paint mBorderPaint;
    //文字的画笔
    private Paint mTextPaint;
    //区域的画笔
    private Paint mAreaPaint;

    //——————多边形相关——————————
    //n边形个数（有几层的意思）
    private int num = 10;
    //两个多边形之间的半径
    private int r = 30;
    //n边形顶点坐标
    private float x,y;
    //n边形角度
    private float angle = (float)((2 * Math.PI) / n);
    //文字与边框的边距等级，值越大边距越小
    private int textAlign = 1;

    //——————颜色相关——————————
    //边框颜色
    private int mBorderColorInner = getResources().getColor(R.color.ViewFillAndStrokeInner);
    private int mBorderColor = getResources().getColor(R.color.ViewFillAndStroke);
    //文字颜色
    private int mTextColor = getResources().getColor(R.color.Grey);
    //区域颜色
    private int mAreaColor = getResources().getColor(R.color.ViewGreen);
    private int mAreaColorBorder = getResources().getColor(R.color.ViewGreenBorder);


    public AttributePowerPanelView(Context context) {
        super(context);
    }

    public AttributePowerPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取Player_info
        area = new int[]{TaskList.getTasks().get(0).getDoneSize(), TaskList.getTasks().get(1).getDoneSize(),
                TaskList.getTasks().get(2).getDoneSize(), TaskList.getTasks().get(3).getDoneSize(),
                TaskList.getTasks().get(4).getDoneSize()};
        getStanford();
    }

    public AttributePowerPanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int width,int height,int oldWidth,int oldHeight){
        super.onSizeChanged(width,height,oldWidth,oldHeight);
        mWidth = width;     //获取宽
        mHeight = height;   //获取高
    }

    @Override
    protected void onDraw(Canvas canvas){
        //绘制图形
        super.onDraw(canvas);
        //初始化画笔
        initPaint();
        //画布移到中心点
        canvas.translate(mWidth/2,mHeight/2);
        //画n边形
        drawPolygon(canvas);
        //画n边形的中点到顶点的线
        drawLine(canvas);
        //画文字
        drawText(canvas);
        //画蓝色区域
        drawArea(canvas);
    }

    /**
     *   画笔初始化
     */
    private void initPaint() {
        //边框画笔
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(3);
        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(35);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        //区域画笔
        mAreaPaint = new Paint();
        mAreaPaint.setAntiAlias(true);
        mAreaPaint.setColor(mAreaColor);
        mAreaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 绘制多边形
     * @param canvas
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //n边形数目
        for (int j=num/2;j<=num;j+=(num/2)){
            float r = j * this.r;
            path.reset();
            //画n边形
            for (int i=1;i<=n;i++){
                x = (float)(Math.cos(i*angle)*r);
                y = (float)(Math.sin(i*angle)*r);
                if (i==1){
                    path.moveTo(x,y);
                }else {
                    path.lineTo(x,y);
                }
            }
            //关闭当前轮廓，如果当前点不等于第一点的轮廓，一条线段是自动添加的
            path.close();
            if (j==num/2){
                mBorderPaint.setColor(mBorderColorInner);
            }else {
                mBorderPaint.setColor(mBorderColor);
            }
            canvas.drawPath(path,mBorderPaint);
        }
    }

    /**
     * 画多边形线段
     * @param canvas
     */
    private void drawLine(Canvas canvas){
        Path path = new Path();
        float r = num * this.r;
        for (int i=1;i<=n;i++){
            path.reset();
            x = (float)(Math.cos(i*angle)*r);
            y = (float)(Math.sin(i*angle)*r);
            path.lineTo(x,y);
            canvas.drawPath(path,mBorderPaint);
        }
    }

    /**
     * 画文字
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        float r = num * this.r;
        for (int i=1;i<=n;i++){
            //测量文字的宽高
            Rect rect = new Rect();
            mTextPaint.getTextBounds(text[i-1],0,text[i-1].length(),rect);
            float textWidth = rect.width();
            float textHeight = rect.height();

            x = (float)(Math.cos(i*angle)*r);
            y = (float)(Math.sin(i*angle)*r);
            //位置微调
            if (x < 0){     //图形左侧区域的文字，2、3
                x = x - textWidth;
                y = y + textHeight;
            }
            if (y > 25){            //右侧，1、4、5
                x = x + textWidth/4;
            }
            //调整文字与边框的边距
            float LastX = x + x/num/textAlign;
            float LastY = y + y/num/textAlign;
            canvas.drawText(text[i-1],LastX,LastY,mTextPaint);
        }
    }

    /**
     * 画区域
     * @param canvas
     */
    private void drawArea(Canvas canvas) {
        Path path = new Path();
        for (int i=1;i<=n;i++){
            float r = area[i-1]*this.r;
            x = (float)(Math.cos(i*angle)*r);
            y = (float)(Math.sin(i*angle)*r);
            if (i==1){
                path.moveTo(x,y);
            }else {
                path.lineTo(x,y);
            }
        }
        //关闭当前轮廓，如果当前点不等于第一个点的轮廓，一条线段是自动添加的
        path.close();
        canvas.drawPath(path,mAreaPaint);

        //区域描边
        path.reset();
        for (int i=1;i<=n;i++){
            float r = area[i-1]*this.r;
            x = (float)(Math.cos(i*angle)*r);
            y = (float)(Math.sin(i*angle)*r);
            if (i==1){
                path.moveTo(x,y);
            }else {
                path.lineTo(x,y);
            }
        }
        path.close();
        mAreaPaint.setColor(mAreaColorBorder);
        mAreaPaint.setStyle(Paint.Style.STROKE);
        mAreaPaint.setStrokeWidth(5);
        canvas.drawPath(path,mAreaPaint);
    }
}
