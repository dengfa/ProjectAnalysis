/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.vincent.projectanalysis.widgets.photoviewextend;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/***
 * 扩展成ViewGroup
 */
public class PhotoViewExtend extends RelativeLayout implements IPhotoView {


    public PhotoViewExtend(Context context) {
        this(context, null);
    }

    public PhotoViewExtend(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoViewExtend(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    @Override
    public boolean canZoom() {
        return false;
    }

    @Override
    public RectF getDisplayRect() {
        return null;
    }

    @Override
    public float getMinScale() {
        return 0;
    }

    @Override
    public float getMidScale() {
        return 0;
    }

    @Override
    public float getMaxScale() {
        return 0;
    }

    @Override
    public float getScale() {
        return 0;
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return null;
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {

    }

    @Override
    public void setMinScale(float minScale) {

    }

    @Override
    public void setMidScale(float midScale) {

    }

    @Override
    public void setMaxScale(float maxScale) {

    }

    @Override
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {

    }

    @Override
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {

    }

    @Override
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {

    }

    @Override
    public void setScaleType(ImageView.ScaleType scaleType) {

    }

    @Override
    public void setZoomable(boolean zoomable) {

    }

    @Override
    public void zoomTo(float scale, float focalX, float focalY) {

    }
}