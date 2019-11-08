package com.vincent.projectanalysis.camera.options;

/**
 * Facing value indicates which camera sensor should be used for the current session.
 *
 * @see CameraView#setFacing(Facing)
 */
public enum Facing {

    /**
     * Back-facing camera sensor.
     */
    BACK(0),

    /**
     * Front-facing camera sensor.
     */
    FRONT(1);

    public final static Facing DEFAULT = BACK;

    private int value;

    Facing(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static Facing fromValue(int value) {
        Facing[] list = Facing.values();
        for (Facing action : list) {
            if (action.value() == value) {
                return action;
            }
        }
        return null;
    }
}
