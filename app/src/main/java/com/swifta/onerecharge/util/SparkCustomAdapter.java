package com.swifta.onerecharge.util;

import com.robinhood.spark.SparkAdapter;

/**
 * Created by moyinoluwa on 10/26/16.
 */

public class SparkCustomAdapter extends SparkAdapter {
    private float[] yData;

    public SparkCustomAdapter(float[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
