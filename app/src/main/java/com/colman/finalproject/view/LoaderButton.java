package com.colman.finalproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.colman.finalproject.R;

public class LoaderButton extends ConstraintLayout {

    private Button mButton;
    private ProgressBar mLoader;
    private String mButtonText;

    public LoaderButton(Context context) {
        super(context);
    }

    public LoaderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LoaderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        super.setOnClickListener(l);
        mButton.setOnClickListener(l);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoaderButton, defStyleAttr, 0);
        mButtonText = typedArray.getString(R.styleable.LoaderButton_ctext);
        boolean isEnabled = typedArray.getBoolean(R.styleable.LoaderButton_cenabled, true);
        @ColorInt int buttonBackground = typedArray.getColor(R.styleable.LoaderButton_cbackground, ContextCompat.getColor(context, R.color.colorAccent));
        typedArray.recycle();

        View customView = LayoutInflater.from(getContext()).inflate(R.layout.loader_button_layout, this, false);
        addView(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mButton = customView.findViewById(R.id.custom_button);
        mLoader = customView.findViewById(R.id.custom_loader);
        mButton.setEnabled(isEnabled);
        mButton.setText(mButtonText);
        mButton.setBackground(makeSelector(buttonBackground));
    }

    public void setEnabled(boolean isEnabled) {
        mButton.setEnabled(isEnabled);
    }

    public void handleLoadingStatus(boolean isLoading) {
        if (isLoading) {
            mButton.setText("");
            mLoader.setVisibility(VISIBLE);
        } else {
            mButton.setText(mButtonText);
            mLoader.setVisibility(GONE);
        }
    }

    private StateListDrawable makeSelector(@ColorInt int color) {
        StateListDrawable res = new StateListDrawable();
        res.addState(new int[]{-android.R.attr.state_enabled}, getBackgroundDrawable(color, true));
        res.addState(new int[]{}, getBackgroundDrawable(color, false));
        return res;
    }

    private GradientDrawable getBackgroundDrawable(@ColorInt int color, boolean isDisabled) {
        GradientDrawable shape = new GradientDrawable();
        float cornerRadius = 60f;
        shape.setCornerRadius(cornerRadius);
        float cornerRadii = 90f;
        shape.setCornerRadii(new float[]{cornerRadii, cornerRadii, cornerRadii, cornerRadii, cornerRadii, cornerRadii, cornerRadii, cornerRadii});
        shape.setAlpha(isDisabled ? 50 : 0xFF);
        shape.setColor(color);
        return shape;
    }
}
