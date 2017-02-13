package org.nevack.android.resistorbee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class RoundColorView extends View {
    @ColorInt private int color = 0xFF000000;
    private Paint paint;

    public RoundColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        invalidate();
    }

    public void setColorByRes(@ColorRes int res) {
        setColor(ContextCompat.getColor(getContext(), res));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int r = (Math.min(getWidth(), getHeight())) / 2;
        paint.setColor(color);
        canvas.drawCircle(x, y, r, paint);
    }
}
