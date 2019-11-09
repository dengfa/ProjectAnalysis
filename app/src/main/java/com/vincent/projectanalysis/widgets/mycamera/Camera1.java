package com.vincent.projectanalysis.widgets.mycamera;

import android.graphics.PointF;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vincent.projectanalysis.camera.CameraView;
import com.vincent.projectanalysis.camera.Mapper;
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

    private static final String TAG = Camera1.class.getSimpleName();
    private static final CameraLogger LOG = CameraLogger.create(TAG);



    private Mapper mMapper = new Mapper.Mapper1();
    private boolean mIsSetup = false;
    private boolean mIsCapturingImage = false;
    private boolean mIsCapturingVideo = false;
    private final Object mLock = new Object();


    Camera1(CameraView.CameraCallbacks callback) {
        super(callback);
    }

    @Override
    void onStart() throws Exception {

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
}
