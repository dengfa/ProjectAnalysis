package com.vincent.projectanalysis.camera;

import android.annotation.TargetApi;
import android.graphics.PointF;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

import com.vincent.projectanalysis.camera.options.Audio;
import com.vincent.projectanalysis.camera.options.Facing;
import com.vincent.projectanalysis.camera.options.Flash;
import com.vincent.projectanalysis.camera.options.Gesture;
import com.vincent.projectanalysis.camera.options.Hdr;
import com.vincent.projectanalysis.camera.options.SessionType;
import com.vincent.projectanalysis.camera.options.VideoQuality;
import com.vincent.projectanalysis.camera.options.WhiteBalance;

@TargetApi(21)
class Camera2 extends CameraController {

    public Camera2(CameraView.CameraCallbacks callback) {
        super(callback);
    }

    @Override
    public void onSurfaceAvailable() {

    }

    @Override
    public void onSurfaceChanged() {

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
    void setAudio(Audio audio) {

    }

    @Override
    void setLocation(Location location) {

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
}
