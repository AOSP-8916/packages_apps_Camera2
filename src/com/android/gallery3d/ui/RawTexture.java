/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.gallery3d.ui;

import javax.microedition.khronos.opengles.GL11;

public class RawTexture extends BasicTexture {
    private static final String TAG = "RawTexture";

    private final boolean mOpaque;

    public RawTexture(int width, int height, boolean opaque) {
        mOpaque = opaque;
        setSize(width, height);
        GLId glId = GLCanvas.getGLId();
        mId = glId.generateTexture();
    }

    @Override
    public boolean isOpaque() {
        return mOpaque;
    }

    protected void prepare(GLCanvas canvas) {
        canvas.setTextureParameters(this);
        canvas.initializeTextureSize(this, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE);
        mState = STATE_LOADED;
        setAssociatedCanvas(canvas);
    }

    @Override
    protected boolean onBind(GLCanvas canvas) {
        if (isLoaded()) return true;
        Log.w(TAG, "lost the content due to context change");
        return false;
    }

    @Override
     public void yield() {
         // we cannot free the texture because we have no backup.
     }

    @Override
    protected int getTarget() {
        return GL11.GL_TEXTURE_2D;
    }
}
