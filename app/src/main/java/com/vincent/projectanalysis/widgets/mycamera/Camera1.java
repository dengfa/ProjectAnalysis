package com.vincent.projectanalysis.widgets.mycamera;

import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.view.SurfaceHolder;

import com.vincent.projectanalysis.camera.CameraView;
import com.vincent.projectanalysis.camera.Size;
import com.vincent.projectanalysis.camera.options.Audio;
import com.vincent.projectanalysis.camera.options.Facing;
import com.vincent.projectanalysis.camera.options.Flash;
import com.vincent.projectanalysis.camera.options.Gesture;
import com.vincent.projectanalysis.camera.options.Hdr;
import com.vincent.projectanalysis.camera.options.SessionType;
import com.vincent.projectanalysis.camera.options.VideoQuality;
import com.vincent.projectanalysis.camera.options.WhiteBalance;
import com.vincent.projectanalysis.camera.utils.CameraLogger;

import java.io.File;


@SuppressWarnings("deprecation")
class Camera1 extends Controller {

    private static final String       TAG = Camera1.class.getSimpleName();
    private static final CameraLogger LOG = CameraLogger.create(TAG);

    private       Camera  mCamera;
    private final Object  mLock    = new Object();
    private       boolean mIsSetup = false;
    private       int     mCameraId;
    private       int     mSensorOffset;


    Camera1(CameraView.CameraCallbacks callback) {
        super(callback);
    }

    // The act of binding an "open" camera to a "ready" preview.
    // These can happen at different times but we want to end up here.
    @WorkerThread
    private void setup() throws Exception {
        LOG.i("setup:", "Started");
        Object output = mPreview.getOutput();
        if (mPreview.getOutputClass() == SurfaceHolder.class) {
            mCamera.setPreviewDisplay((SurfaceHolder) output);
        } else {
            mCamera.setPreviewTexture((SurfaceTexture) output);
        }

        boolean invertPreviewSizes = shouldFlipSizes();
        //mCaptureSize = computeCaptureSize();
        //mPreviewSize = computePreviewSize();
        mCaptureSize = new Size(400, 400);
        mPreviewSize = new Size(400, 400);
        LOG.i("setup:", "Dispatching onCameraPreviewSizeChanged.");
        if (mCameraCallbacks != null) {
            mCameraCallbacks.onCameraPreviewSizeChanged();
        }
        mPreview.setDesiredSize(
                invertPreviewSizes ? mPreviewSize.getHeight() : mPreviewSize.getWidth(),
                invertPreviewSizes ? mPreviewSize.getWidth() : mPreviewSize.getHeight()
        );
        LOG.i("setup:", "Starting preview with startPreview().");
        mCamera.startPreview();
        LOG.i("setup:", "Started preview with startPreview().");
        mIsSetup = true;
    }

    @Override
    void onStart() throws Exception {
        if (isCameraAvailable()) {
            LOG.w("onStart:", "Camera not available. Should not happen.");
            onStop(); // Should not happen.
        }
        if (collectCameraId()) {
            mCamera = Camera.open(mCameraId);
            // Try starting preview.
            mCamera.setDisplayOrientation(computeSensorToDisplayOffset()); // <- not allowed during preview
            if (shouldSetup())
                setup();
            LOG.i("onStart:", "Ended");
        }
    }

    private boolean collectCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mSensorOffset = cameraInfo.orientation;
                mCameraId = i;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns how much should the sensor image be rotated before being shown.
     * It is meant to be fed to Camera.setDisplayOrientation().
     */
    private int computeSensorToDisplayOffset() {
        return (mSensorOffset - mDisplayOffset + 360) % 360;
    }

    private boolean shouldSetup() {
        return isCameraAvailable() && mPreview != null && mPreview.isReady() && !mIsSetup;
    }


    @Override
    void onStop() throws Exception {

    }

    @Override
    void setSessionType(SessionType sessionType) {

    }

    @Override
    void setFacing(Facing facing) {

    }

    @Override
    boolean setZoom(float zoom) {
        return false;
    }

    @Override
    boolean setExposureCorrection(float EVvalue) {
        return false;
    }

    @Override
    void setFlash(Flash flash) {

    }

    @Override
    void setWhiteBalance(WhiteBalance whiteBalance) {

    }

    @Override
    void setHdr(Hdr hdr) {

    }

    @Override
    void setLocation(Location location) {

    }

    @Override
    void setAudio(Audio audio) {

    }

    @Override
    void setVideoQuality(VideoQuality videoQuality) {

    }

    @Override
    boolean capturePicture() {
        return false;
    }

    @Override
    boolean captureSnapshot() {
        return false;
    }

    @Override
    boolean startVideo(@NonNull File file) {
        return false;
    }

    @Override
    boolean endVideo() {
        return false;
    }

    @Override
    boolean shouldFlipSizes() {
        return false;
    }

    @Override
    boolean startAutoFocus(@Nullable Gesture gesture, PointF point) {
        return false;
    }


    @Override
    public void onSurfaceAvailable() {

    }

    @Override
    public void onSurfaceChanged() {

    }


    private boolean isCameraAvailable() {
        switch (mState) {
            // If we are stopped, don't.
            case STATE_STOPPED:
                return false;
            // If we are going to be closed, don't act on camera.
            // Even if mCamera != null, it might have been released.
            case STATE_STOPPING:
                return false;
            // If we are started, act as long as there is no stop/restart scheduled.
            // At this point mCamera should never be null.
            case STATE_STARTED:
                return !mScheduledForStop && !mScheduledForRestart;
            // If we are starting, theoretically we could act.
            // Just check that camera is available.
            case STATE_STARTING:
                return mCamera != null && !mScheduledForStop && !mScheduledForRestart;
        }
        return false;
    }
}
