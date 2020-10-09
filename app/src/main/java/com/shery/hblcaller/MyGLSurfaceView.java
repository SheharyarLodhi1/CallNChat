package com.shery.hblcaller;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {
    private Point dimensions;

    public MyGLSurfaceView(Context c, Point dimensions) {
        super(c);
        this.dimensions = dimensions;
        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
    }

    @Override
    protected void onMeasure(int unusedX, int unusedY) {
        setMeasuredDimension(dimensions.x, dimensions.y);
    }
}
