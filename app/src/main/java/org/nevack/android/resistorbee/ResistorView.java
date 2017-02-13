package org.nevack.android.resistorbee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class ResistorView extends View {

    public static final int RADIUS = 20;
    private int[] colors;

    private Paint bodyPaint;
    private Paint strokePaint;
    private int strokeCount;
    public static final int WIRE_THICKNESS = 20;
    public static final int RESISTOR_WIDTH = 300;
    public static final int RESISTOR_HEIGHT = 150;
    public static final int RESISTOR_END_WIDTH = 75;
    public static final int STROKE_WIDTH = 20;
    private int x;
    private int y;

    public ResistorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ResistorView);
        strokeCount = typedArray.getInt(R.styleable.ResistorView_stroke_count, 6);
        typedArray.recycle();
        colors = new int[strokeCount];
        fillColors();
        init();
    }

    private void init() {
        bodyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bodyPaint.setStyle(Paint.Style.FILL);
        bodyPaint.setColor(0xFFCCA965);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.FILL);
        strokePaint.setColor(0xFF0000AA);
    }

    public void setStrokeCount(int count) {
        strokeCount = count;
        colors = new int[strokeCount];
        fillColors();
        invalidate();
    }

    private void fillColors() {
        for (int i = 0; i < colors.length; i++) {
            colors[i] = 0xFF000000;
        }
    }

    public void setStrokeColor(int position, int color) {
        colors[position] = color;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {

        x = (getWidth() - RESISTOR_WIDTH) / 2;
        y = (getHeight() - RESISTOR_HEIGHT) / 2;

        strokePaint.setColor(0xFF000000);
        canvas.drawRect(
                0,
                (getHeight() - WIRE_THICKNESS) / 2,
                getWidth(),
                (getHeight() + WIRE_THICKNESS) / 2,
                strokePaint
        );

        canvas.drawRect(
                x,
                y,
                x + RESISTOR_WIDTH,
                y + RESISTOR_HEIGHT,
                bodyPaint
        );

        RectF rect1 = new RectF(x - RESISTOR_END_WIDTH, y - RADIUS, x, y + RESISTOR_HEIGHT + RADIUS);
        RectF rect2 = new RectF(x + RESISTOR_WIDTH, y - RADIUS, x + RESISTOR_WIDTH + RESISTOR_END_WIDTH, y + RESISTOR_HEIGHT + RADIUS);
        canvas.drawRoundRect(rect1, RADIUS, RADIUS, bodyPaint);
        canvas.drawRoundRect(rect2, RADIUS, RADIUS, bodyPaint);

        for(int i = 1; i <= strokeCount; i++) {
            strokePaint.setColor(colors[i - 1]);
            canvas.drawRect(
                    x + RESISTOR_WIDTH / strokeCount * i - STROKE_WIDTH / 2 - RESISTOR_WIDTH / strokeCount / 2,
                    y,
                    x + RESISTOR_WIDTH / strokeCount * i + STROKE_WIDTH / 2 - RESISTOR_WIDTH / strokeCount / 2,
                    y + RESISTOR_HEIGHT,
                    strokePaint
            );
//            canvas.drawLine(
//                    x + RESISTOR_WIDTH / strokeCount * i - RESISTOR_WIDTH / strokeCount / 2,
//                    y + RESISTOR_HEIGHT,
//                    getWidth() / strokeCount * i - getWidth() / strokeCount / 2,
//                    getHeight() + 40,
//                    strokePaint
//            );
        }
    }
}
