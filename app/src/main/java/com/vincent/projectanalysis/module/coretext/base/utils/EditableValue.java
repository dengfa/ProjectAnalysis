//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.utils;

public class EditableValue {
    private int color = -1;
    private String value;

    public EditableValue() {
    }

    public EditableValue(int color, String value) {
        this.color = color;
        this.value = value;
    }

    public int getColor() {
        return this.color;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
