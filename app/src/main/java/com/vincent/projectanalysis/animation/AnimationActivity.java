package com.vincent.projectanalysis.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity {

    @BindView(R.id.rl_container)
    RelativeLayout mRlContainer;

    @BindView(R.id.tv_bottom)
    View mTvBottom;

    private int       mScreenWidth;
    private ImageView mTargetView;
    private int       mTargetViewWidth;
    private int       mTargetViewHeight;
    private int       mDefaultValue;
    private Matrix    mBaseMatrix = new Matrix();
    private Matrix    mSuppMatrix = new Matrix();
    private Matrix    mDrawMatrix = new Matrix();
    private float     mFinalScale;
    private float     mPx;
    private float     mPy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);

        mTargetView = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(UIUtils.dip2px(200),
                UIUtils.dip2px(100));
        mTargetView.setImageResource(R.drawable.anakin);
        mTargetView.setScaleType(ImageView.ScaleType.MATRIX);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRlContainer.addView(mTargetView, params);
        mTargetView.setVisibility(View.GONE);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;

        mTargetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimationActivity.this,
                        "onclick", Toast.LENGTH_LONG).show();
            }
        });
        mTargetView.post(new Runnable() {
            @Override
            public void run() {
                mTargetViewWidth = mTargetView.getWidth();
                mTargetViewHeight = mTargetView.getHeight();
                Drawable drawable = mTargetView.getDrawable();
                int drawableWidth = drawable.getIntrinsicWidth();
                int drawableHeight = drawable.getIntrinsicHeight();
                mFinalScale = mTargetViewWidth * 1.0f / drawableWidth;
                mBaseMatrix.postTranslate(-(drawableWidth - mTargetViewWidth) / 2,
                        -(drawableHeight - mTargetViewHeight) / 2);
                mTargetView.setImageMatrix(mBaseMatrix);
                mPx = mTargetViewWidth / 2;
                mPy = mTargetViewHeight / 2;
            }
        });
    }

    @OnClick(R.id.btn_start)
    public void start() {

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scale = new ObjectAnimator();


        ValueAnimator.ofInt();

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTargetView.getLayoutParams();

        ValueAnimator animator = ValueAnimator.ofFloat(1, mScreenWidth / mTargetViewWidth);
        animator.setDuration(3 * 1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //View外部动画的方式
                //1.修改parmas后调用requestLayout或setlayoutparams
                //doAnimate(value, params);
                //2.Scrollxx移动的是view里的内容
                //mTargetView.scrollBy(-2,2);
                //mTargetView.requestLayout();
                //3.整体都会内容移动
                //mRlContainer.scrollBy(-2,2);
                //mRlContainer.requestLayout();
                //4.通过layout
                //mTargetView.layout(mTargetView.getLeft() + 1, mTargetView.getTop() + 1, mTargetView.getRight() + 1, mTargetView.getBottom() + 1);
                //5.更改属性
                ViewHelper.setTranslationX(mTargetView, ViewHelper.getTranslationX(mTargetView) - 2);

            }
        });
        animator.start();

        set.playTogether(animator, animator);
    }

    private void doAnimate(float value, RelativeLayout.LayoutParams params) {
        int curWidth = (int) (value * mTargetViewWidth);
        int curHeight = (int) (value * mTargetViewHeight);
        mSuppMatrix.postTranslate((curWidth - params.width) / 2, (curHeight - params.height) / 2);
        params.width = curWidth;
        params.height = curHeight;
        params.topMargin = mDefaultValue++;
        mTargetView.requestLayout();
        mSuppMatrix.postScale(0.999f, 0.999f, params.width / 2, params.height / 2);
        mTargetView.setImageMatrix(getDisplayMatrix());
    }


    private final float[] mMatrixValues = new float[9];

    private float getValue(Matrix matrix, int whichValue) {
        matrix.getValues(mMatrixValues);
        return mMatrixValues[whichValue];
    }

    private void resetMatrix() {
        mSuppMatrix.reset();
        mTargetView.setImageMatrix(getDisplayMatrix());
    }

    protected Matrix getDisplayMatrix() {
        mDrawMatrix.set(mBaseMatrix);
        mDrawMatrix.postConcat(mSuppMatrix);
        return mDrawMatrix;
    }

    public void add(View view) {
        mSuppMatrix.postScale(1.1f, 1.1f, mPx, mPy);
        mTargetView.setImageMatrix(getDisplayMatrix());
    }

    public void less(View view) {
        mSuppMatrix.postScale(0.9f, 0.9f, mPx, mPy);
        mTargetView.setImageMatrix(getDisplayMatrix());
    }

    public void topButtonClick(View view) {
        LogUtil.d("vincent", "topButtonClick");
    }

    public void bottomButtonClick(View view) {
        LogUtil.d("vincent", "bottomButtonClick");
        SimpleCustomAnimation animation = new SimpleCustomAnimation();
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);
    }

    public void topTextClick(final View view) {
        LogUtil.d("vincent", "topTextClick");
        LogUtil.d("vincent", "view width before " + view.getWidth());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", view.getX() + 200),
                ObjectAnimator.ofFloat(view, "scaleX", 1.5f),
                ObjectAnimator.ofFloat(view, "scaleY", 1.5f));
        animatorSet.setDuration(1000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtil.d("vincent", "view width " + view.getWidth());
            }
        });
        animatorSet.start();
    }
}
