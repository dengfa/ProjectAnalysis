//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.blocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.vincent.projectanalysis.module.coretext.TextEnv;
import com.vincent.projectanalysis.module.coretext.utils.ImageLoader;
import com.vincent.projectanalysis.utils.ImageFetcher;
import com.vincent.projectanalysis.utils.LogUtil;


public class CYImageBlock extends CYPlaceHolderBlock {
    protected Bitmap mBitmap;
    private String mUrl;
    private Paint mPaint = new Paint(1);
    private ImageFetcher.ImageFetcherListener mImageFetcherListener = new ImageFetcher.ImageFetcherListener() {
        public void onLoadComplete(String imageUrl, Bitmap bitmap, Object object) {
            if(!TextUtils.isEmpty(CYImageBlock.this.mUrl) && CYImageBlock.this.mUrl.equals(imageUrl)) {
                LogUtil.v("yangzc", "setBitmap net: " + (bitmap == null));
                CYImageBlock.this.setBitmap(bitmap);
            }

        }
    };

    public CYImageBlock(TextEnv textEnv, String content) {
        super(textEnv, content);
        this.mPaint.setColor(-855308);
        ImageLoader.getImageLoader().addImageFetcherListener(this.mImageFetcherListener);
    }

    public CYImageBlock setResId(Context context, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        this.setBitmap(bitmap);
        LogUtil.v("yangzc", "setBitmap res: " + (bitmap == null));
        return this;
    }

    public CYImageBlock setDefaultBackGroundColor(int color) {
        this.mPaint.setColor(color);
        return this;
    }

    public CYImageBlock setResUrl(String url) {
        this.mUrl = url;
        if(TextUtils.isEmpty(url)) {
            return this;
        } else {
            Bitmap bitmap = ImageLoader.getImageLoader().loadImage(url);
            if(bitmap != null) {
                LogUtil.v("yangzc", "setBitmap local: " + (bitmap == null));
                this.setBitmap(bitmap);
            }

            return this;
        }
    }

    protected void setBitmap(Bitmap bitmap) {
        if(bitmap != null && !bitmap.isRecycled()) {
            this.mBitmap = bitmap;
        }

        this.postInvalidate();
    }

    public CYImageBlock setAlignStyle(AlignStyle style) {
        return (CYImageBlock)super.setAlignStyle(style);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(this.mBitmap != null && !this.mBitmap.isRecycled()) {
            canvas.drawBitmap(this.mBitmap, (Rect)null, this.getContentRect(), this.mPaint);
        } else {
            canvas.drawRect(this.getContentRect(), this.mPaint);
        }

    }

    public void release() {
        super.release();
        ImageLoader.getImageLoader().removeImageFetcherListener(this.mImageFetcherListener);
    }
}
