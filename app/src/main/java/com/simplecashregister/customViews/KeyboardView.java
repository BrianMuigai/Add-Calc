package com.simplecashregister.customViews;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.simplecashregister.R;

public class KeyboardView extends FrameLayout implements View.OnClickListener, View.OnLongClickListener {

    private EditText text;
    private KeyboardListener listener;

    public KeyboardView(Context context) {
        super(context);
        init();
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAddListener(KeyboardListener listener) {
        this.listener = listener;
    }

    private void init() {
        inflate(getContext(), R.layout.keyboard, this);
        initViews();
    }

    private void initViews() {
        text = $(R.id.text_field);
        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_0).setOnLongClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_clear).setOnClickListener(this);
        $(R.id.t9_key_add).setOnClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        text.append(".");
        return true;
    }

    @Override
    public void onClick(View v) {
        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            TextView view = (TextView) v;
            float newVal = Float.parseFloat(text.getText().toString() + view.getText());
            if (Float.parseFloat(text.getText().toString().isEmpty() ? "0" : text.getText().toString()) < 1000000 && newVal < 1000000) {
                text.append(view.getText());
            }
            return;
        }
        switch (v.getId()) {
            case R.id.t9_key_clear: {// handle backspace button
                // delete one character
                Editable editable = text.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
            }
            break;
            case R.id.t9_key_add: {  // handle add button
                if (!text.getText().toString().isEmpty()) {
                    if (listener != null) listener.onAdd(text.getText().toString());
                    text.setText(null);
                }
            }
            break;
        }
    }

    public String getInputText() {
        return text.getText().toString();
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    public interface KeyboardListener {
        void onAdd(String val);
    }
}
