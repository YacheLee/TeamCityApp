/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.drawer.view;

import android.support.v7.app.AppCompatActivity;

import com.github.vase4kin.teamcityapp.R;

/**
 * Custom animation drawer view impl
 */
public class CustomAnimationDrawerViewImpl extends DrawerViewImpl {

    public CustomAnimationDrawerViewImpl(AppCompatActivity activity, int drawerSelection, boolean isBackArrowEnabled) {
        super(activity, drawerSelection, isBackArrowEnabled);
    }

    /**
     * Override pending transitions
     */
    @Override
    public void overridePendingTransition() {
        mActivity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
