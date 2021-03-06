package com.vincent.interview.android;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vincent.projectanalysis.utils.AppPreferences;
import com.vincent.projectanalysis.utils.LogUtil;
import com.vincent.projectanalysis.utils.MD5Util;
import com.vincent.projectanalysis.utils.UIUtils;
import com.vincent.projectanalysis.widgets.event.TestEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dengfa on 2019-11-25.
 * Des:
 */
public class PreDownLoadService extends IntentService {

    public static final  String TAG           = "preload";
    private static       int    start         = 5;
    // 5MB. This is the max image header size we can handle, we preallocate a much smaller buffer but will resize up to
    // this amount if necessary.
    private static final int    MARK_POSITION = 5 * 1024 * 1024;

    public PreDownLoadService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PreDownLoadService(String name) {
        super(name);
    }

    private static class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.d("vincent", "handleMessage - " + msg.what);
            switch (msg.what) {
                case 0:
                    start = 5;
                    break;
            }
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        EventBus.getDefault().post(new TestEvent("PreDownLoadService"));

        String url = intent.getStringExtra("url");
        /*start = 5;
        while (start > 0) {
            LogUtil.d("vincent", "PreDownLoad - " + url + start);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            start--;
        }*/
        downloadimage(url);
    }


    public Bitmap downloadimage(String url) {
        LogUtil.d("vincent", "downloadimage = " + url);
        URL myFileUrl;
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        try {
            myFileUrl = new URL(url);
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() != 200) {
                return null;
            }
            InputStream is = conn.getInputStream();
            loadBitmapFromStream(is, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            conn.disconnect();
        }
        return bitmap;
    }

    public Bitmap loadBitmap(String url) {
        String path = getPath(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(path, options);
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        LogUtil.d("vincent", "outHeight = " + outHeight);
        LogUtil.d("vincent", "outWidth = " + outWidth);
        int inSampleSize = 1;
        while (outWidth > UIUtils.dip2px(300)) {
            outWidth /= 2;
            inSampleSize *= 2;
        }
        LogUtil.d("vincent", "inSampleSize = " + inSampleSize);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    public Bitmap loadBitmap(String url, float scale) {
        String path = getPath(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    public Bitmap loadBitmap(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int n;
        byte[] buffer = new byte[1024];
        while ((n = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, n);
        }
        inputStream.close();
        byte[] data = outputStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;

        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        LogUtil.d("vincent", "outHeight = " + outHeight);
        LogUtil.d("vincent", "outWidth = " + outWidth);
        int inSampleSize = 1;
        while (outWidth > UIUtils.dip2px(300)) {
            outWidth /= 2;
            inSampleSize *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public Bitmap loadBitmapFromStream(InputStream inputStream, String url) throws IOException {
        BufferedInputStream stream = new BufferedInputStream(inputStream);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        stream.mark(MARK_POSITION);
        BitmapFactory.decodeStream(stream, null, options);
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int inSampleSize = 1;
        while (outWidth > UIUtils.dip2px(300)) {
            outWidth /= 2;
            inSampleSize *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = inSampleSize;
        stream.reset();
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        LogUtil.d("vincent", "loadBitmapFromStream - " + bitmap.getByteCount());
        File file = getFile(url + "_loadBitmapFromStream");
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        return bitmap;
    }

    public void saveImage(Bitmap bitmap, String url) throws IOException {
        if (bitmap == null) {
            return;
        }
        File file = getFile(url);
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        LogUtil.d("vincent", "saveImageToPhotos");
        AppPreferences.setStringValue("path", file.getAbsolutePath());
    }

    @NonNull
    private File getFile(String url) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = MD5Util.encode(url) + ".jpg";
        File file = new File(appDir, fileName);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    public void saveImage(InputStream is, String url) throws IOException {
        // 首先保存图片
        File file = getFile(url);

        BufferedInputStream in = new BufferedInputStream(is);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    public String getPath(String url) {
        String fileName = MD5Util.encode(url) + ".jpg";
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        File file = new File(appDir, fileName);
        LogUtil.d("vincent", "getPath = " + file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("vincent", "PreDownLoad - onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtil.d("vincent", "PreDownLoad - onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtil.d("vincent", "PreDownLoad - onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    Messenger messenger = new Messenger(new MsgHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("vincent", "PreDownLoad - onBind");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("vincent", "PreDownLoad - onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtil.d("vincent", "PreDownLoad - onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("vincent", "PreDownLoad - onDestroy");
    }
}
