//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.utils;

import android.graphics.Bitmap;

import com.vincent.projectanalysis.utils.ImageFetcher;
import com.vincent.projectanalysis.utils.ImageFetcher.ImageFetcherListener;

import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private static ImageLoader mImageLoader;
    private List<ImageFetcherListener> mFetcherListeners;
    private ImageFetcherListener mFetcherListener = new ImageFetcherListener() {
        public void onLoadComplete(String imageUrl, Bitmap bitmap, Object object) {
            if(ImageLoader.this.mFetcherListeners != null) {
                for(int i = 0; i < ImageLoader.this.mFetcherListeners.size(); ++i) {
                    ImageFetcherListener listener = (ImageFetcherListener)ImageLoader.this.mFetcherListeners.get(i);
                    listener.onLoadComplete(imageUrl, bitmap, object);
                }
            }

        }
    };

    private ImageLoader() {
    }

    public static ImageLoader getImageLoader() {
        if(mImageLoader == null) {
            mImageLoader = new ImageLoader();
        }

        return mImageLoader;
    }

    public Bitmap loadImage(String url) {
        Bitmap bitmap = ImageFetcher.getImageFetcher().getBitmapInCache(url);
        if(bitmap != null && !bitmap.isRecycled()) {
            return bitmap;
        } else {
            ImageFetcher.getImageFetcher().loadImage(url, url, this.mFetcherListener);
            return null;
        }
    }

    public void addImageFetcherListener(ImageFetcherListener listener) {
        if(this.mFetcherListeners == null) {
            this.mFetcherListeners = new ArrayList();
        }

        if(!this.mFetcherListeners.contains(listener)) {
            this.mFetcherListeners.add(listener);
        }

    }

    public void removeImageFetcherListener(ImageFetcherListener listener) {
        if(this.mFetcherListeners != null) {
            this.mFetcherListeners.remove(listener);
        }
    }
}
