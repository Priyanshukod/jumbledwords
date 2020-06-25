package com.stupendous.jumbledwords.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class VariableGravityButton extends Button {
public VariableGravityButton(Context context) {
    super(context);
}

public VariableGravityButton(Context context, AttributeSet attrs) {
    super(context, attrs);
}

public VariableGravityButton(Context context, AttributeSet attrs,
        int defStyle) {
    super(context, attrs, defStyle);
}

@Override
public void setPressed(boolean pressed) {
    if (pressed != isPressed()) {
        setGravity(pressed ? Gravity.CENTER_HORIZONTAL |
                Gravity.BOTTOM : Gravity.CENTER);
    }
    super.setPressed(pressed);
}}