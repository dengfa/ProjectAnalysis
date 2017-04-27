package com.vincent.projectanalysis.module.mapScene.render;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;

import com.vincent.projectanalysis.R;
import com.vincent.projectanalysis.module.mapScene.Director;
import com.vincent.projectanalysis.module.mapScene.StateSprite;
import com.vincent.projectanalysis.module.mapScene.base.texture.CTexture;
import com.vincent.projectanalysis.utils.ImageFetcher;
import com.vincent.projectanalysis.utils.UIUtils;

/**
 * Created by yangzc on 16/6/21.
 */
public class AnchorSprite extends StateSprite {

    protected String mImageUrl;
    protected Bitmap mDefaultIcon;
    protected Bitmap mUserIcon;
    private int mIconPaddingOuter = UIUtils.dip2px(3);
    private int mIconPaddingInner = UIUtils.dip2px(2);
    private int mBorderColor = Color.WHITE;

    public static AnchorSprite create(Director director, CTexture anchorTexture, String imageUrl) {
        AnchorSprite sprite = new AnchorSprite(director, anchorTexture, imageUrl);
        return sprite;
    }

    protected AnchorSprite(Director director, CTexture texture, String imageUrl) {
        super(director, texture);
        this.mImageUrl = imageUrl;
        mDefaultIcon = BitmapFactory.decodeResource(director.getContext().getResources(),
                R.drawable.default_student);
        updateShader();
        updateUserIcon();
        ImageFetcher.getImageFetcher().addImageFetcherListener(mImageFetcherListener);
    }

    @Override
    public synchronized void render(Canvas canvas) {
        super.render(canvas);

        Point position = this.getPosition();
        int rc = canvas.save();
        if (position == null) {
            canvas.translate(0.0F, 0.0F);
        } else {
            canvas.translate((float) position.x, (float) position.y);
        }
        drawIcon(canvas);
        canvas.restoreToCount(rc);
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
        updateUserIcon();

    }

    @Override
    public synchronized void update(float dt) {
        super.update(dt);
    }

    private void updateUserIcon() {
        if (TextUtils.isEmpty(mImageUrl))
            return;

        ImageFetcher.getImageFetcher().loadImage(mImageUrl, mImageUrl, new ImageFetcher.ImageFetcherListener() {
            @Override
            public void onLoadComplete(String s, Bitmap bitmap, Object o) {}
        });
    }

    private ImageFetcher.ImageFetcherListener mImageFetcherListener
            = new ImageFetcher.ImageFetcherListener() {

        @Override
        public void onLoadComplete(String s, Bitmap bitmap, Object o) {
            if (o instanceof String && !TextUtils.isEmpty(mImageUrl) && mImageUrl.equals(o)
                    && bitmap != null) {
                mUserIcon = bitmap;
                updateShader();
            }
        }
    };

    public void release() {
        ImageFetcher.getImageFetcher().removeImageFetcherListener(mImageFetcherListener);
    }

    protected RectF        mRectF        = new RectF();
    protected Paint        mPaint        = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected BitmapShader mBitmapShader = null;

    private void updateShader() {
        Bitmap bitmap = null;
        if (mUserIcon != null) {
            bitmap = mUserIcon;
        } else {
            if (mDefaultIcon != null) {
                bitmap = mDefaultIcon;
            }
        }
        if (bitmap == null)
            return;

        RectF rawRectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        int width = getWidth() - mIconPaddingOuter * 2;
        int height = width;
        mRectF.set(mIconPaddingOuter,
                mIconPaddingOuter,
                mIconPaddingOuter + width,
                mIconPaddingOuter + height);

        Matrix shaderMatrix = new Matrix();
        shaderMatrix.setRectToRect(rawRectF, mRectF, Matrix.ScaleToFit.FILL);

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader.setLocalMatrix(shaderMatrix);
    }

    protected boolean mHasBorder = true;

    public void setHasBorder(boolean hasBorder){
        this.mHasBorder = hasBorder;
    }

    public void setBorderColor(int color){
        this.mBorderColor = color;
    }

    protected void drawIcon(Canvas canvas) {
        mPaint.reset();

        int width = getWidth() - mIconPaddingOuter * 2;
        int height = width;
        //外径
        mRectF.set(mIconPaddingOuter,
                mIconPaddingOuter,
                mIconPaddingOuter + width,
                mIconPaddingOuter + height);
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), mRectF.width() / 2, mPaint);

        if (mBitmapShader != null) {

            if (mHasBorder) {
                width = getWidth() - mIconPaddingInner * 2 - mIconPaddingOuter * 2;
                height = width;
                mRectF.set(mIconPaddingInner + mIconPaddingOuter,
                        mIconPaddingInner + mIconPaddingOuter,
                        mIconPaddingInner + mIconPaddingOuter + width,
                        mIconPaddingInner + mIconPaddingOuter + height);
            }

            mPaint.reset();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            mPaint.setShader(mBitmapShader);
            canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), width / 2, mPaint);
        }
    }
}
