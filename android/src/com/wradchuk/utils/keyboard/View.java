package com.wradchuk.utils.keyboard;

public interface View {
    public void onSizeChange(float width, float height);
    public void addListener(SizeChangeListener sizeChangeListener);
    public float getWidth();
    public float getHeight();
}