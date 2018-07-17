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
 * Created by 邓发 on 2018/7/17.
 */
public class VerifyCodeView extends RelativeLayout {

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
    private Drawable mEtBackgroundDrawableFocus;
    // 输入框没有焦点时背景
    private Drawable mEtBackgroundDrawableNormal;

    //存储TextView的数据 数量由自定义控件的属性传入
    private TextView[] mTextViews;
    private int mEtDividerWidth;

    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    //初始化 布局和属性
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        containerEt = new LinearLayout(getContext());
        LayoutParams linearLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        linearLp.addRule(CENTER_IN_PARENT);
        containerEt.setOrientation(LinearLayout.HORIZONTAL);
        containerEt.setLayoutParams(linearLp);
        addView(containerEt);

        et = new EditText(getContext());
        LayoutParams editLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        editLp.addRule(CENTER_IN_PARENT);
        et.setBackgroundResource(R.color.transparent);
        et.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        et.requestFocus();
        et.setLayoutParams(editLp);
        addView(et);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnthenticationCodeView, defStyleAttr, 0);
        mEtNumber = typedArray.getInteger(R.styleable.AnthenticationCodeView_icv_et_number, 1);
        mEtWidth = typedArray.getDimensionPixelSize(R.styleable.AnthenticationCodeView_icv_et_width, 42);
        mEtDividerWidth = typedArray.getDimensionPixelSize(R.styleable.AnthenticationCodeView_icv_et_divider_width, UIUtils.dip2px(8));
        mEtDividerDrawable = typedArray.getDrawable(R.styleable.AnthenticationCodeView_icv_et_divider_drawable);
        mEtTextSize = typedArray.getDimensionPixelSize(R.styleable.AnthenticationCodeView_icv_et_text_size, 16);
        mEtTextColor = typedArray.getColor(R.styleable.AnthenticationCodeView_icv_et_text_color, Color.WHITE);
        mEtBackgroundDrawableFocus = typedArray.getDrawable(R.styleable.AnthenticationCodeView_icv_et_bg_focus);
        mEtBackgroundDrawableNormal = typedArray.getDrawable(R.styleable.AnthenticationCodeView_icv_et_bg_normal);
        //释放资源
        typedArray.recycle();
    }

    public EditText getEditext() {
        return et;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initTextViews(getContext(), mEtNumber, mEtWidth, mEtDividerDrawable, mEtTextSize, mEtTextColor);
        initEtContainer(mTextViews);
        setListener();
    }

    //初始化TextView
    private void initTextViews(Context context, int etNumber, int etWidth, Drawable etDividerDrawable, float etTextSize, int etTextColor) {
        // 设置 editText 的输入长度
        et.setCursorVisible(false);//将光标隐藏
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(etNumber)}); //最大输入长度
        mTextViews = new TextView[etNumber];
        for (int i = 0; i < mTextViews.length; i++) {
            TextView textView = new EditText(context);
            textView.setTextSize(etTextSize);
            textView.setTextColor(etTextColor);
            textView.setBackgroundDrawable(TextUtils.isEmpty(textView.getText()) ?
                    mEtBackgroundDrawableNormal : mEtBackgroundDrawableFocus);
            textView.setGravity(Gravity.CENTER);
            textView.setFocusable(false);
            mTextViews[i] = textView;
        }
    }

    //初始化存储TextView 的容器
    private void initEtContainer(TextView[] mTextViews) {
        for (int i = 0; i < mTextViews.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != mTextViews.length - 1) {
                params.rightMargin = mEtDividerWidth;
            } else {
                params.rightMargin = 0;
            }
            containerEt.addView(mTextViews[i], params);
        }
    }

    private void setListener() {
        // 监听输入内容
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputStr = editable.toString();
                if (inputStr != null && !inputStr.equals("")) {
                    setText(inputStr);
                    et.setText("");
                }
            }
        });

        // 监听删除按键
        et.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onKeyDelete();
                    return true;
                }
                return false;
            }
        });
    }

    // 给TextView 设置文字
    public void setText(String inputContent) {
        for (int i = 0; i < mTextViews.length; i++) {
            TextView tv = mTextViews[i];
            if (tv.getText().toString().trim().equals("")) {
                tv.setText(inputContent);
                tv.setBackgroundDrawable(mEtBackgroundDrawableFocus);
                // 添加输入完成的监听
                if (inputCompleteListener != null) {
                    inputCompleteListener.inputComplete();
                }
                break;
            }
        }
    }

    // 监听删除
    public void onKeyDelete() {
        for (int i = mTextViews.length - 1; i >= 0; i--) {
            TextView tv = mTextViews[i];
            if (!tv.getText().toString().trim().equals("")) {
                tv.setText("");
                tv.setBackgroundDrawable(mEtBackgroundDrawableNormal);
                // 添加删除完成监听
                if (inputCompleteListener != null) {
                    inputCompleteListener.deleteContent();
                }
                break;
            }
        }
    }

    /**
     * 获取输入文本
     *
     * @return
     */
    public String getTextContent() {
        StringBuffer buffer = new StringBuffer();
        for (TextView tv : mTextViews) {
            buffer.append(tv.getText().toString().trim());
        }
        return buffer.toString();
    }

    /**
     * 删除所有内容
     */
    public void clearAllText() {
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i].setText("");
            mTextViews[i].setBackgroundDrawable(mEtBackgroundDrawableNormal);
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
