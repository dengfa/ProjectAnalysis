package com.vincent.projectanalysis.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

public class ImageUtil {
    private static final String TAG = "ImageUtils";

    private static final int MAX_SIZE = 400;// 最大尺寸400

    /**
     * Convert drawable to Bitmap
     *
     * @param drawable
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;

        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
                            .getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                            : Config.RGB_565);
        } catch (OutOfMemoryError e) {
            Log.d(TAG, "Exception : " + e.getMessage());
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Resize bitmap in original scale
     *
     * @param bitmap
     * @param aMaxWidth
     * @param aMaxHeight
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int aMaxWidth, int aMaxHeight) {

        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // no need to resize
        if (originWidth < aMaxWidth && originHeight < aMaxHeight) {
            return bitmap;
        }

        int newWidth = originWidth;
        int newHeight = originHeight;

        if (originWidth > aMaxWidth) {
            newWidth = aMaxWidth;

            double i = originWidth * 1.0 / aMaxWidth;
            newHeight = (int) Math.floor(originHeight / i);

            bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        }

        if (newHeight > aMaxHeight) {
//			newHeight = aMaxHeight;
            int startPointY = (int) ((newHeight - aMaxHeight) / 2.0);
            bitmap = Bitmap.createBitmap(bitmap, 0, startPointY, newWidth, aMaxHeight);
        }

        return bitmap;
    }

    /**
     * 增加圆角
     *
     * @param bitmap
     * @param width
     * @param height
     * @param radius
     * @return
     */
    public static Bitmap round(Bitmap bitmap, int width, int height, int radius,
                               boolean recycleSource) {
        if (width == 0 || height == 0 || radius <= 0 || bitmap == null)
            return bitmap;

        Bitmap ret = null;
        try {
            ret = Bitmap.createBitmap(width, height, Config.RGB_565);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError",
                    "OutOfMemoryError in ImageUtils.round(): " + e.getMessage());
        }
        if (ret == null)
            return null;

        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (recycleSource)
            ImageUtil.clear(bitmap);
        return ret;
    }

    /**
     * 增加圆角
     *
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap round(Bitmap bitmap, int radius, boolean recycleSource) {
        if (radius <= 0 || bitmap == null)
            return bitmap;
        return round(bitmap, bitmap.getWidth(), bitmap.getHeight(), radius, recycleSource);
    }

    /**
     * Get rounded bitmap image
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {
        if (width == 0 || height == 0) {
            return null;
        }
        Bitmap resizedBitmap = null;
        try {
            resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError",
                    "OutOfMemoryError in ImageUtils.getResizedBitmap(): " + e.getMessage());
        }
        if (resizedBitmap == null)
            return null;
        else {
            ImageUtil.clear(bitmap);
            return resizedBitmap;
        }
    }

    /**
     * 裁剪图片，默认先缩放
     *
     * @param bitmapPath 原图片路径
     * @param width
     * @param height
     * @return
     */
    public static Bitmap crop(String bitmapPath, int width, int height) {
        Bitmap bitmap = createBitmap(bitmapPath);
        bitmap = scale(bitmap, width, height, ScaleType.CENTER_CROP, true);
        return crop(bitmap, width, height, true);
    }

    /**
     * 裁剪图片，默认先缩放
     *
     * @param bitmap        原图
     * @param width
     * @param height
     * @param recycleSource 是否回收原图
     * @return
     */
    public static Bitmap scaleAndCrop(Bitmap bitmap, int width, int height, boolean recycleSource) {
        LogUtil.d(TAG, "scaleAndCrop()...");
        bitmap = scale(bitmap, width, height, ScaleType.CENTER_CROP, recycleSource);
        return crop(bitmap, width, height, true);
    }

    /**
     * 剪裁图片 思路：取原图与目标大小的交叉部分
     *
     * @param sourceBitmap  原图
     * @param targetWidth   剪裁到的宽度
     * @param targetHeight  剪裁到的高度
     * @param recycleSource 是否回收原图
     * @return
     */
    private static Bitmap crop(Bitmap sourceBitmap, int targetWidth, int targetHeight,
                               boolean recycleSource) {
        LogUtil.d(TAG, "crop()...");
        if (sourceBitmap == null)
            return null;

        Bitmap croppedBitmap = null;

        // 获取原图缩放之后与目标图的交叉区域
        int xDiff = Math.max(0, sourceBitmap.getWidth() - targetWidth);
        int yDiff = Math.max(0, sourceBitmap.getHeight() - targetHeight);
        int x = xDiff / 2;
        int y = yDiff / 2;

        try {
            // 根据交叉区域进行剪裁
            croppedBitmap = Bitmap.createBitmap(sourceBitmap, x, y, targetWidth, targetHeight);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError",
                    "OutOfMemoryError in BitmapUtil.crop() : " + e.getMessage());
        }

        if (recycleSource && sourceBitmap != croppedBitmap)
            clear(sourceBitmap);
        return croppedBitmap;
    }

    /**
     * 等比缩放图片
     *
     * @param sourceBitmap  原图
     * @param targetWidth   目标宽度
     * @param targetHeight  目标高度
     * @param scaleType     缩放类型同ImageView.ScaleType，但只用到CENTER_CROP和 CENTER_INSIDE
     * @param recycleSource 是否回收原图
     * @return
     */
    public static Bitmap scale(Bitmap sourceBitmap, float targetWidth, float targetHeight,
                               ScaleType scaleType, boolean recycleSource) {
        LogUtil.d(TAG, "scale()...");
        if (sourceBitmap != null)
            LogUtil.d(TAG, "sourceBitmap.isRecycled() : " + sourceBitmap.isRecycled());
        if (sourceBitmap == null || sourceBitmap.isRecycled())
            return null;

        Bitmap scaledBitmap = null;

        float scale;

        float sourceWidth = sourceBitmap.getWidth();
        float sourceHeight = sourceBitmap.getHeight();

        float sourceRatio = sourceWidth / sourceHeight;
        float targetRatio = targetWidth / targetHeight;

        // 计算缩放比例，比较(原图宽/高比)和(目标图的宽/高比)，若前者大用高度比例，否则用宽度比例
        if (scaleType == ScaleType.CENTER_CROP)
            scale = sourceRatio > targetRatio ? targetHeight / sourceHeight : targetWidth
                    / sourceWidth;
        else
            scale = sourceRatio < targetRatio ? targetHeight / sourceHeight : targetWidth
                    / sourceWidth;

        // 不需要缩放，直接返回
        if (scale == 1.0F)
            return sourceBitmap;

        // 创建缩放矩阵
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);

        // LogUtil.d(TAG, "sourceWidth : " + sourceWidth + " , sourceHeight : "
        // + sourceHeight);
        try {
            // 将原图缩放
            scaledBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(),
                    sourceBitmap.getHeight(), matrix, true);

            // LogUtil.d(TAG, "scaled: width : " + scaledBitmap.getWidth());
            // LogUtil.d(TAG, "scaled: height : " + scaledBitmap.getHeight());
        } catch (IllegalArgumentException e) {
            LogUtil.e("IllegalArgumentException",
                    "IllegalArgumentException in BitmapUtil.scale(): " + e.getMessage());
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError",
                    "OutOfMemoryError in BitmapUtil.scale(): " + e.getMessage());
        }

        if (recycleSource && sourceBitmap != scaledBitmap)
            clear(sourceBitmap);
        return scaledBitmap;
    }

    public static Options getBitmapOptions(String path, int maxSize) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = (options.outWidth > options.outHeight ? options.outWidth
                : options.outHeight) / (maxSize < 1 ? MAX_SIZE : maxSize);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        return options;
    }

    public static Options getBitmapOptions(InputStream is, int maxSize) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = (options.outWidth > options.outHeight ? options.outWidth
                : options.outHeight) / maxSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        return options;
    }

    public static Options getBitmapOptions(Resources res, int resId, int maxSize) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = (options.outWidth > options.outHeight ? options.outWidth
                : options.outHeight) / maxSize;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        return options;
    }

    public static Options getBitmapOptions(Context context, Uri uri, int maxSize) {
        if (context == null || uri == null)
            return null;
        Options options = new Options();

        FileDescriptor fd = getFileDescriptor(context, uri);

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);

        options.inSampleSize = (options.outWidth > options.outHeight ? options.outWidth
                : options.outHeight) / maxSize;

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.RGB_565;
        return options;
    }

    public static FileDescriptor getFileDescriptor(Context context, Uri uri) {
        if (context == null || uri == null)
            return null;
        ContentResolver res = context.getContentResolver();

        ParcelFileDescriptor parcelFileDescriptor = null;
        FileDescriptor fd = null;
        try {
            parcelFileDescriptor = res.openFileDescriptor(uri, "r");
            fd = parcelFileDescriptor.getFileDescriptor();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (parcelFileDescriptor != null)
                    parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fd;
    }

    public static Bitmap createBitmap(String path) {
        return createBitmap(path, MAX_SIZE);
    }

    public static Bitmap createBitmap(String path, int maxSize) {
        if (TextUtils.isEmpty(path))
            return null;
        Bitmap bitmap = null;
        try {
            Options options = getBitmapOptions(path, maxSize);
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError", "OOM in BitmapUtil.createBitmap : " + e.getMessage());
        }
        return bitmap;
    }

    public static Bitmap createBitmap(int width, int height, Config config) {
        if (width <= 0 || height <= 0)
            return null;
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError", "OOM in BitmapUtil.createBitmap : " + e.getMessage());
        }
        return bitmap;
    }

    public static Bitmap createBitmap(InputStream is) {
        if (is == null)
            return null;
        Bitmap bitmap = null;
        try {
            Options options = getBitmapOptions(is, MAX_SIZE);
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError", "OOM in BitmapUtil.createBitmap : " + e.getMessage());
        }
        return bitmap;
    }

    public static Bitmap createBitmap(InputStream is, Rect outPadding, Options opts) {
        if (is == null)
            return null;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, outPadding, opts);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError", "OOM in BitmapUtil.createBitmap : " + e.getMessage());
        }
        return bitmap;
    }

    public static Bitmap createBitmap(Context context, int resId) {
        if (context == null)
            return null;
        Bitmap bitmap = null;
        try {
            Options options = getBitmapOptions(context.getResources(), resId, MAX_SIZE);
            bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError", "OOM in BitmapUtil.createBitmap : " + e.getMessage());
        }
        return bitmap;
    }

    /**
     * 从Uri中获取一张bitmap
     *
     * @param context
     * @param uri
     * @param maxSize
     * @return
     */
    public static Bitmap createBitmap(Context context, Uri uri, int maxSize) {
        if (context == null || uri == null)
            return null;
        FileDescriptor fd = getFileDescriptor(context, uri);
        Options options = getBitmapOptions(context, uri, maxSize);
        if (fd == null || options == null)
            return null;
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd, null, options);
        if (bitmap != null
                && (bitmap.getWidth() > options.outWidth || bitmap.getHeight() > options.outHeight)) {
            Bitmap tmp = Bitmap.createScaledBitmap(bitmap, options.outWidth, options.outHeight,
                    true);
            if (tmp != null && tmp != bitmap)
                ImageUtil.clear(bitmap);
            if (tmp != null)
                bitmap = tmp;
        }
        return bitmap;
    }

    /**
     * 根据参数修整图片
     *
     * @param bitmap
     * @param width
     * @param height
     * @param radius
     * @param needCrop
     * @param needScale
     * @param recycleSource 是否回收原图
     * @return
     */
    public static Bitmap revise(Bitmap bitmap, int width, int height, int radius, boolean needCrop,
                                boolean needScale, boolean recycleSource) {
        if (bitmap == null)
            return null;
        LogUtil.d(TAG, "revise to width : " + width + " height : " + height);
        if (needCrop && needScale || (radius > 0 && (width > 0 || height > 0))) {
            bitmap = scaleAndCrop(bitmap, width, height, recycleSource);
        } else if (needScale) {
            bitmap = scale(bitmap, width, height, ScaleType.CENTER_INSIDE, recycleSource);
        } else if (needCrop)
            bitmap = crop(bitmap, width, height, recycleSource);
        if (radius > 0)
            bitmap = ImageUtil.round(bitmap, width, height, radius, recycleSource);
        return bitmap;
    }

    /**
     * 将bitmap写入文件
     *
     * @param bitmap
     * @param path
     * @param quality
     * @param recycleSource
     * @return
     */
    public static String writeToFile(Bitmap bitmap, String path, int quality, boolean recycleSource) {
        LogUtil.d(TAG, "writeToFile : " + path);
        if (bitmap == null)
            return null;

        try {
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            File f = new File(path);

            if (f.exists())
                f.delete();

            if (f.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (recycleSource)
            clear(bitmap);

        return path;
    }

    /**
     * 释放bitmap
     *
     * @param bitmap
     */
    public static void clear(Bitmap bitmap) {
        if (bitmap != null)
            bitmap.recycle();
    }

    /**
     * 刷新ImageView上的图片，并将原来的图片回收
     *
     * @param iv
     * @param imgTagId
     * @param bitmap
     * @param defaultBitmap
     */
    public static void refresh(ImageView iv, int imgTagId, Bitmap bitmap, Bitmap defaultBitmap) {
        if (iv == null)
            return;
        Bitmap oldBitmap = (Bitmap) iv.getTag(imgTagId);
        if (oldBitmap != bitmap)
            clear(oldBitmap);
        iv.setImageBitmap(bitmap == null ? defaultBitmap : bitmap);
        iv.setTag(imgTagId, bitmap);
    }

    public static int refresh(ImageView iv, int imgTagId, Bitmap bitmap, int resId) {
        if (iv == null)
            return 0;
        int hashCode = 0;
        Bitmap oldBitmap = (Bitmap) iv.getTag(imgTagId);
        if (oldBitmap != null && oldBitmap != bitmap) {
            hashCode = oldBitmap.hashCode();
            clear(oldBitmap);
        }
        if (bitmap == null)
            iv.setImageResource(resId);
        else
            iv.setImageBitmap(bitmap);
        iv.setTag(imgTagId, bitmap);
        return hashCode;
    }

    public static Bitmap createBitmap(Context context, String filename) {
        return createBitmap(filename, MAX_SIZE);
    }

    public static Bitmap createBitmap(Context context, String filename, int maxSize) {
        if (context == null || TextUtils.isEmpty(filename))
            return null;
        filename = context.getFilesDir() + File.separator + filename;
        return createBitmap(filename, maxSize);
    }

    public static Bitmap createBitmapFromAsset(Context context, String filename) {
        if (context == null || TextUtils.isEmpty(filename))
            return null;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(filename);
            bitmap = ImageUtil.createBitmap(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static Bitmap createBitmapFromFile(Context context, String filename) {
        if (context == null || TextUtils.isEmpty(filename))
            return null;
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            is = context.openFileInput(filename);
            bitmap = ImageUtil.createBitmap(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 对Bitmap.createBitmap的原始封装，只是加了内存溢出判断
     *
     * @param src
     * @param x
     * @param y
     * @param width
     * @param height
     * @param m
     * @param filter
     * @return
     */
    public static Bitmap createBitmap(Bitmap src, int x, int y, int width, int height, Matrix m,
                                      boolean filter) {
        if (src == null)
            return null;
        Bitmap bitmap = src;
        try {
            // 将原图缩放
            bitmap = Bitmap.createBitmap(src, x, y, width, height, m, filter);
        } catch (IllegalArgumentException e) {
            LogUtil.e("IllegalArgumentException",
                    "IllegalArgumentException in BitmapUtil.rotate(): " + e.getMessage());
        } catch (OutOfMemoryError e) {
            LogUtil.e("OutOfMemoryError",
                    "OutOfMemoryError in BitmapUtil.rotate(): " + e.getMessage());
        }
        return bitmap;
    }

    /**
     * 将Bitmap转化成int数组
     *
     * @param bitmap
     * @return
     */
    public static int[] getIntArray(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        int pix[] = null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        try {
            pix = new int[w * h];
            bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            LogUtil.e(TAG, "method getIntArray w : " + w);
            LogUtil.e(TAG, "method getIntArray h : " + h);
            LogUtil.e(TAG, "OutOfMemoryError in method getIntArray : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pix;
    }

    public static byte[] toBytes(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static String getImageNameByTime() {
        Calendar calendar = Calendar.getInstance();
        return "IMG_" + String.valueOf(calendar.get(Calendar.YEAR))
                + String.valueOf(calendar.get(Calendar.MONTH))
                + String.valueOf(calendar.get(Calendar.DATE)) + "_"
                + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                + String.valueOf(calendar.get(Calendar.MINUTE))
                + String.valueOf(calendar.get(Calendar.SECOND)) + ".jpg";
    }

    /**
     * 从url获取该图片的文件名
     *
     * @param url
     * @param postfix
     * @return
     */
    public static String getImageNameFromUrl(String url, String postfix) {
        return getImageNameFromUrl(url, postfix, null);
    }

    /**
     * 从url获取当前图片的文件名，如果url以ignoreTag开头则直接返回该url；如果ignoreTag为空，则不会判断ignoreTag
     *
     * @param url
     * @param postfix
     * @param ignoreTag
     * @return
     */
    public static String getImageNameFromUrl(String url, String postfix, String ignoreTag) {
        if (TextUtils.isEmpty(url)
                || ((!TextUtils.isEmpty(ignoreTag)) && url.startsWith(ignoreTag)))
            return url;
        int lastIndex = url.lastIndexOf(postfix);
        if (lastIndex < 0)
            lastIndex = url.length() - 1;
        int beginIndex = url.lastIndexOf("/") + 1;
        int slashIndex = url.lastIndexOf("%2F") + 3;
        int finalSlashIndex = url.lastIndexOf("%252F") + 5;
        beginIndex = Math.max(Math.max(beginIndex, slashIndex), finalSlashIndex);

        if (beginIndex >= lastIndex) {
            return null;
        }
        return url.substring(beginIndex, lastIndex);
    }

    public static Bitmap copy(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled())
            return null;
        return bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
    }

    /**
     * 图片操作回调
     *
     * @author wangzengyang 2013-4-11
     */
    public interface ImageListener {
        /**
         * 剪裁回调(UI线程中)
         *
         * @param bitmap
         */
        void onRevise(Bitmap bitmap);
    }

    /**
     * 在Bitmap上做装饰
     *
     * @param bitmap 原始图片
     * @param dots   装饰用的点
     * @return 装饰后的图片对象
     */
    public static Bitmap decorate(Bitmap bitmap, int[] dots) {
        if (bitmap == null || dots == null)
            return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        try {
            if (dots.length != w * h)
                return null;
            if (dots.length > 0) {
                bitmap.setPixels(dots, 0, w, 0, 0, w, h);
                return bitmap;
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            LogUtil.e(TAG, "method decorate w : " + w);
            LogUtil.e(TAG, "method decorate h : " + h);
            LogUtil.e(TAG, "OutOfMemoryError in method decorate : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将图片更新到MediaStore
     *
     * @param resolver
     * @param title
     * @param location
     * @param orientation
     * @param jpeg        图片二进制数据
     * @param path        图片路径
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Uri saveToMediaStore(ContentResolver resolver, String title, Location location,
                                       int orientation, byte[] jpeg, String path) {
        ContentValues values = new ContentValues(9);
        values.put(ImageColumns.TITLE, title);
        values.put(ImageColumns.DISPLAY_NAME, title + ".jpg");
        Date d = new Date();
        values.put(ImageColumns.DATE_TAKEN, d.getDate());
        values.put(ImageColumns.MIME_TYPE, "image/jpeg");
        values.put(ImageColumns.ORIENTATION, orientation);
        values.put(ImageColumns.DATA, path);
        values.put(ImageColumns.SIZE, jpeg.length);

        if (location != null) {
            values.put(ImageColumns.LATITUDE, location.getLatitude());
            values.put(ImageColumns.LONGITUDE, location.getLongitude());
        }

        Uri uri = resolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri == null) {
            LogUtil.e(TAG, "Failed to write MediaStore");
            return null;
        }
        return uri;
    }

    public static BitmapDrawable decodeWithOOMHandling(Resources res, String imagePath) {
        BitmapDrawable result = null;

        if (TextUtils.isEmpty(imagePath))
            return result;

        try {
            result = new BitmapDrawable(res, imagePath);
        } catch (OutOfMemoryError e) {
            LogUtil.e(TAG, e.getMessage(), e);
            System.gc();
            //handle exception
            LogUtil.d(TAG, "++++decodeWithOOMHandling,OutOfMemoryError");
            // Wait some time while GC is working
            SystemClock.sleep(1000);
            System.gc();
        }
        return result;
    }

    /**
     *
     * */
    public static int computeSampleSize(Options options, int minSideLength,
                                        int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength,
                                                int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h
                / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 150 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 根据URI获取图片物理路径
     */
    @SuppressWarnings("deprecation")
    protected static String getAbsoluteImagePath(Uri uri, Activity activity) {
        String[] proj = {Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * 滤镜效果
     *
     * @param bitmap
     * @param maskBitmap
     * @return
     */
    public static Bitmap maskDrawable(Bitmap bitmap, Bitmap maskBitmap) {
        Bitmap output = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFF000000);
        new NinePatch(maskBitmap, maskBitmap.getNinePatchChunk(), null).draw(canvas, new Rect(0, 0,
                bitmap.getWidth(), bitmap.getHeight()));
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;
    }

    /**
     * 处理成圆角图片
     *
     * @param bitmap
     * @param roundPX
     * @return
     */
    public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
        Bitmap dstbmp = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(dstbmp);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        paint.setFilterBitmap(true);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return dstbmp;
    }

    /**
     * 处理成圆角图片
     *
     * @param bitmap
     * @param roundPX
     * @return
     */
    public static Bitmap getRCB(Bitmap bitmap, float roundPX, float roundPY) {
        Bitmap dstbmp = Bitmap
                .createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(dstbmp);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        paint.setFilterBitmap(true);
        canvas.drawRoundRect(rectF, roundPX, roundPY, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return dstbmp;
    }

    /**
     * 从文件读取图片并调整尺寸
     *
     * @param file
     * @param MAX_SIZE
     * @param defaultWidth
     * @param defaultHeight
     * @return
     */
    @SuppressWarnings("null")
    public static byte[] getBitmapBytes(File file, final int MAX_SIZE, final int defaultWidth,
                                        final int defaultHeight) {
        long fileSize = file.length() / 1024;
        Options options = new Options();
        Bitmap bitmap = null;
        Bitmap srcBitmap = null;

        if (fileSize >= MAX_SIZE) {
            int sqr = (int) Math.ceil(Math.sqrt(((double) fileSize / MAX_SIZE)));
            options.inSampleSize = sqr;
            try {
                srcBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            } catch (OutOfMemoryError e) {
                LogUtil.e("OutOfMemoryError",
                        "OOM in BitmapUtil.filterFileImage : " + e.getMessage());
                return null;
            }
            if (srcBitmap == null)
                return null;

            bitmap = resizeBitmap(srcBitmap, defaultWidth, defaultHeight);

            // 重新生成上传的图片文件
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] content = baos.toByteArray();
                if (content != null && content.length > 0) {
                    file.delete();
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(content);
                    if (fos != null)
                        fos.close();
                }
                return content;
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            srcBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (srcBitmap == null)
                return null;
            bitmap = resizeBitmap(srcBitmap, defaultWidth, defaultHeight);
        }
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] result = baos.toByteArray();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        if (srcBitmap != null) {
            srcBitmap.recycle();
            srcBitmap = null;
        }
        return result;
    }

    /**
     * 按宽度等比缩放
     *
     * @param bitmap
     * @param reqWidth
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int reqWidth) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //按屏幕宽度等比缩放
        Matrix matrix = new Matrix();
        float scaleFactor = reqWidth * 1f / bitmapWidth;
        matrix.postScale(scaleFactor, scaleFactor);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
        bitmap.recycle();
        return scaledBitmap;
    }

    /**
     * 按所需宽度加载本地图片
     */
    public static Bitmap decodeBitmap(String path, int reqWidth) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSizeByWidth(options, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 按宽度计算采样率
     */
    public static int calculateInSampleSizeByWidth(Options options, int reqWidth) {
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            final int halfWidth = width / 2;
            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static final int CORNER_NONE = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_TOP = CORNER_TOP_LEFT | CORNER_TOP_RIGHT;
    public static final int CORNER_BOTTOM = CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_LEFT = CORNER_TOP_LEFT | CORNER_BOTTOM_LEFT;
    public static final int CORNER_RIGHT = CORNER_TOP_RIGHT | CORNER_BOTTOM_RIGHT;

    public static Bitmap cornerBitmap(Bitmap bitmap, int roundPx, int corners) {
        try {
            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
            // 然后在画板上画出一个想要的形状的区域。
            // 最后把源图片帖上。
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();

            Bitmap paintingBoard = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(paintingBoard);
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);

            //画出4个圆角
            final RectF rectF = new RectF(0, 0, width, height);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            //把不需要的圆角去掉
            int notRoundedCorners = corners ^ CORNER_ALL;
            if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
                clipTopLeft(canvas, paint, roundPx, width, height);
            }
            if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
                clipTopRight(canvas, paint, roundPx, width, height);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
                clipBottomLeft(canvas, paint, roundPx, width, height);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
                clipBottomRight(canvas, paint, roundPx, width, height);
            }
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

            final Rect src = new Rect(0, 0, width, height);
            final Rect dst = src;
            canvas.drawBitmap(bitmap, src, dst, paint);
            return paintingBoard;
        } catch (Exception exp) {
            return bitmap;
        }
    }

    private static void clipTopLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, 0, offset, offset);
        canvas.drawRect(block, paint);
    }

    private static void clipTopRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(width - offset, 0, width, offset);
        canvas.drawRect(block, paint);
    }

    private static void clipBottomLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, height - offset, offset, height);
        canvas.drawRect(block, paint);
    }

    private static void clipBottomRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(width - offset, height - offset, width, height);
        canvas.drawRect(block, paint);
    }

    public static void loadImage(String url, ImageView imageView, int defaultRes) {
        ImageFetcher.getImageFetcher().loadImage(url, imageView, defaultRes);
    }
}
