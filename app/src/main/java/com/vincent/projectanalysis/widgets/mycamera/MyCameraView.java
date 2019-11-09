package com.vincent.projectanalysis.widgets.mycamera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.vincent.projectanalysis.camera.CameraView;
import com.vincent.projectanalysis.camera.Size;
import com.vincent.projectanalysis.camera.views.CameraPreview;
import com.vincent.projectanalysis.camera.views.SurfaceCameraPreview;

import java.util.ArrayList;
import java.util.List;

public class MyCameraView extends FrameLayout {
    private final static   String TAG                     = MyCameraView.class.getSimpleName();
    protected final static int    DEFAULT_WIDTH           = 400;
    protected final static int    DEFAULT_HEIGHT          = 400;
    public final static    int    PERMISSION_REQUEST_CODE = 16;

    private CameraPreview mCameraPreview;
    private Controller    mCameraController;

    public MyCameraView(@NonNull Context context) {
        super(context, null);
        init(context, null);
    }

    public MyCameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {


        mCameraController = instantiateCameraController(null);
    }

    void instantiatePreview() {
        mCameraPreview = new SurfaceCameraPreview(getContext(), this, null, 800, 800);
        mCameraController.setPreview(mCameraPreview);
    }

    protected Controller instantiateCameraController(CameraView.CameraCallbacks callbacks) {
        return new Camera1(callbacks);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mCameraPreview == null) {
            instantiatePreview();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = DEFAULT_WIDTH;
        int measureHeight = DEFAULT_HEIGHT;

        // Let's which dimensions need to be adapted.
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthValue = MeasureSpec.getSize(widthMeasureSpec);
        final int heightValue = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.AT_MOST) {
            measureWidth = widthValue;
        }
        if (heightMode != MeasureSpec.AT_MOST) {
            measureHeight = heightValue;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * Returns the size used for the preview,
     * or null if it hasn't been computed (for example if the surface is not ready).
     *
     * @return a Size
     */
    @Nullable
    public Size getPreviewSize() {
        return mCameraController != null ? mCameraController.getPreviewSize() : null;
    }

    /**
     * Starts the camera preview, if not started already.
     * This should be called onResume(), or when you are ready with permissions.
     */
    public void start() {
        if (!isEnabled())
            return;

        if (checkPermissions()) {
            // Update display orientation for current CameraController
            //mOrientationHelper.enable(getContext());
            mCameraController.start();
        }
    }

    @SuppressLint("NewApi")
    protected boolean checkPermissions() {
        // Manifest is OK at this point. Let's check runtime permissions.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        Context c = getContext();
        boolean needsCamera = c.checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED;
        if (needsCamera) {
            requestPermissions();
            return false;
        }
        return true;
    }

    // If we end up here, we're in M.
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        Activity activity = null;
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                activity = (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        if (activity != null) {
            activity.requestPermissions(permissions.toArray(new String[permissions.size()]),
                    PERMISSION_REQUEST_CODE);
        }
    }

    public void stop() {
        mCameraController.stop();
    }
}