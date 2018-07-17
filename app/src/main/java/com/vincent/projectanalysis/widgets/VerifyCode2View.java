package com.vincent.projectanalysis.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.UIUtils;

/**
 * description: 自定义view 注册验证码 输入框
 * Created by dengfa on 2018/7/17.
 */
public class VerifyCode2View extends RelativeLayout {

    private LinearLayout containerEt;

    private EditText et;
    // 输入框数量
    private int mEtNumber;
    // 输入框的宽度
    private int mEtWidth;
    //输入框分割线
    private Drawable mEtDividerDrawable;
    //输入框文字颜色
    private int mEtTextColor;
    //输入框文字大小
    private float mEtTextSize;
    //输入框获取焦点时背景
    private Drawable mEtBackgroundDrawableFill;
    // 输入框没有焦点时背景
    private Drawable mEtBackgroundDrawableEmpty;

    //存储TextView的数据 数量由自定义控件的属性传入
    private EditText[] mCodeEts;
    private int mEtDividerWidth;

    public VerifyCode2View(Context context) {
        this(context, null);
    }

    public VerifyCode2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCode2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    //初始化 布局和属性
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeView, defStyleAttr, 0);
        mEtNumber = typedArray.getInteger(R.styleable.CodeView_icv_et_number, 1);
        mEtWidth = typedArray.getDimensionPixelSize(R.styleable.CodeView_icv_et_width, 42);
        mEtDividerWidth = typedArray.getDimensionPixelSize(R.styleable.CodeView_icv_et_divider_width, UIUtils.dip2px(8));
        mEtDividerDrawable = typedArray.getDrawable(R.styleable.CodeView_icv_et_divider_drawable);
        mEtTextSize = typedArray.getDimensionPixelSize(R.styleable.CodeView_icv_et_text_size, 16);
        mEtTextColor = typedArray.getColor(R.styleable.CodeView_icv_et_text_color, Color.WHITE);
        mEtBackgroundDrawableFill = typedArray.getDrawable(R.styleable.CodeView_icv_et_bg_focus);
        mEtBackgroundDrawableEmpty = typedArray.getDrawable(R.styleable.CodeView_icv_et_bg_normal);
        //释放资源
        typedArray.recycle();

        containerEt = new LinearLayout(getContext());
        LayoutParams linearLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        linearLp.addRule(CENTER_VERTICAL);
        containerEt.setOrientation(LinearLayout.HORIZONTAL);
        containerEt.setLayoutParams(linearLp);
        addView(containerEt);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initTextViews(getContext(), mEtNumber, mEtWidth, mEtTextColor);
        initEtContainer(mCodeEts);
        setOnKeyListener(onKeyListener);
    }

    //初始化TextView
    private void initTextViews(Context context, int etNumber, float etTextSize, int etTextColor) {
        // 设置 editText 的输入长度
        mCodeEts = new EditText[etNumber];
        for (int i = 0; i < mCodeEts.length; i++) {
            EditText etCode = new EditText(context);
            etCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            etCode.setTextSize(etTextSize);
            etCode.setTextColor(etTextColor);
            etCode.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            etCode.setBackgroundDrawable(TextUtils.isEmpty(etCode.getText()) ?
                    mEtBackgroundDrawableEmpty : mEtBackgroundDrawableFill);
            etCode.setGravity(Gravity.CENTER);
            if (i == 0) {
                etCode.requestFocus();
            }
            etCode.setEnabled(i == 0);
            etCode.addTextChangedListener(watcher);
            etCode.setOnKeyListener(onKeyListener);
            mCodeEts[i] = etCode;
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1) {
                nextCode();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //初始化存储TextView 的容器
    private void initEtContainer(TextView[] mTextViews) {
        for (int i = 0; i < mTextViews.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != mTextViews.length - 1) {
                params.rightMargin = mEtDividerWidth;
            } else {
                params.rightMargin = 0;
            }
            containerEt.addView(mTextViews[i], params);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                reqFocus();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    // 监听删除按键
    OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                onKeyDelete();
                return true;
            }
            return false;
        }
    };

    // 给TextView 设置文字
    public void setText(String inputContent) {
        for (int i = 0; i < mCodeEts.length; i++) {
            TextView tv = mCodeEts[i];
            if (tv.getText().toString().trim().equals("")) {
                tv.setText(inputContent);
                tv.setBackgroundDrawable(mEtBackgroundDrawableFill);
                // 添加输入完成的监听
                if (inputCompleteListener != null) {
                    inputCompleteListener.inputComplete();
                }
                break;
            }
        }
    }

    public void nextCode() {
        int cur = -1;
        for (int i = 0; i < mCodeEts.length; i++) {
            EditText tv = mCodeEts[i];
            if (tv.getText().toString().trim().equals("")) {
                cur = i;
                break;
            }
        }
        if (cur > -1) {
            EditText curEt = mCodeEts[cur];
            curEt.setEnabled(true);
            curEt.requestFocus();
            if (cur != 0) {
                EditText preEt = mCodeEts[cur - 1];
                preEt.setBackgroundDrawable(mEtBackgroundDrawableFill);
                preEt.setEnabled(false);
            }

            // 添加输入完成的监听
            if (inputCompleteListener != null) {
                inputCompleteListener.inputComplete();
            }
        } else {
            //最后一位填完隐藏光标
            mCodeEts[mCodeEts.length - 1].setCursorVisible(false);
            mCodeEts[mCodeEts.length - 1].setBackgroundDrawable(mEtBackgroundDrawableFill);
        }
    }

    // 监听删除
    public void onKeyDelete() {
        int cur = -1;
        for (int i = mCodeEts.length - 1; i >= 0; i--) {
            TextView tv = mCodeEts[i];
            if (!tv.getText().toString().trim().equals("")) {
                cur = i;
                break;
            }
        }
        if (cur < 0) {
            return;
        }
        EditText curEt = mCodeEts[cur];
        curEt.setText("");
        curEt.setEnabled(true);
        curEt.requestFocus();
        curEt.setBackgroundDrawable(mEtBackgroundDrawableEmpty);
        if (cur != mCodeEts.length - 1) {
            EditText nextEt = mCodeEts[cur + 1];
            nextEt.setEnabled(false);
        } else {
            //最后一位删除时显示光标
            curEt.setCursorVisible(true);
        }
        // 添加删除完成监听
        if (inputCompleteListener != null) {
            inputCompleteListener.deleteContent();
        }
    }

    public void reqFocus() {
        int cur = -1;
        for (int i = 0; i < mCodeEts.length; i++) {
            EditText tv = mCodeEts[i];
            if (tv.getText().toString().trim().equals("")) {
                cur = i;
                break;
            }
        }
        mCodeEts[cur > -1 ? cur : mCodeEts.length - 1].requestFocus();
    }

    /**
     * 获取输入文本
     *
     * @return
     */
    public String getTextContent() {
        StringBuffer buffer = new StringBuffer();
        for (TextView tv : mCodeEts) {
            buffer.append(tv.getText().toString().trim());
        }
        return buffer.toString();
    }

    /**
     * 删除所有内容
     */
    public void clearAllText() {
        for (int i = 0; i < mCodeEts.length; i++) {
            mCodeEts[i].setText("");
            mCodeEts[i].setBackgroundDrawable(mEtBackgroundDrawableEmpty);
        }
    }

    /**
     * 获取输入的位数
     *
     * @return
     */
    public int getTextCount() {
        return mEtNumber;
    }

    // 输入完成 和 删除成功 的监听
    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();

        void deleteContent();
    }
}
