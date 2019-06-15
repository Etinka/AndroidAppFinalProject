package com.colman.finalproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.colman.finalproject.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class LoaderButton extends ConstraintLayout {

    private Button button;
    private ProgressBar loader;
    private String buttonText;

    private Drawable buttonBackground;

    public LoaderButton(Context context) {
        super(context);
    }

    public LoaderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        buttonBackground = getResources().getDrawable(R.drawable.app_button_background);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoaderButton, 0, 0);
        String buttonText = typedArray.getString(R.styleable.LoaderButton_ctext);
        boolean isEnabled = typedArray.getBoolean(R.styleable.LoaderButton_cenabled, true);
        Drawable background = typedArray.getDrawable(R.styleable.LoaderButton_cbackground);
        if(background != null){
            buttonBackground = background;
        }
        typedArray.recycle();
        init(buttonText, isEnabled);
    }

    public LoaderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoaderButton, defStyleAttr, 0);
        String buttonText = typedArray.getString(R.styleable.LoaderButton_ctext);
        boolean isEnabled = typedArray.getBoolean(R.styleable.LoaderButton_cenabled, true);
        Drawable background = typedArray.getDrawable(R.styleable.LoaderButton_cbackground);
        if(background != null){
            buttonBackground = background;
        }
        typedArray.recycle();
        init(buttonText, isEnabled);
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        super.setOnClickListener(l);
        button.setOnClickListener(l);
    }

    private void init(String text, boolean isEnabled) {
        buttonText = text;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.loader_button_layout, this, false);
        addView(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        button = customView.findViewById(R.id.custom_button);
        loader = customView.findViewById(R.id.custom_loader);
        button.setEnabled(isEnabled);
        button.setText(text);
        button.setBackground(buttonBackground);
    }

    public void setEnabled(boolean isEnabled){
        button.setEnabled(isEnabled);
    }

    public void handleLoadingStatus(boolean isLoading) {
        if (isLoading) {
            button.setText("");
            loader.setVisibility(VISIBLE);
        } else {
            button.setText(buttonText);
            loader.setVisibility(GONE);
        }
    }
}
