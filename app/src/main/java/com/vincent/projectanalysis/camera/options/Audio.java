package com.vincent.projectanalysis.camera.options;

/**
 * Audio values indicate whether to record audio stream when record video.
 *
 * @see CameraView#setAudio(Audio)
 */
public enum Audio {

    /**
     * No Audio.
     */
    OFF(0),

    /**
     * With Audio.
     */
    ON(1);

    public final static Audio DEFAULT = ON;

    private int value;

    Audio(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public  static Audio fromValue(int value) {
        Audio[] list = Audio.values();
        for (Audio action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return null;
    }
}
