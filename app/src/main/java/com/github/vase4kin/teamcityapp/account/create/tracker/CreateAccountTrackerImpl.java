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

package com.github.vase4kin.teamcityapp.account.create.tracker;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.LoginEvent;

import io.fabric.sdk.android.Fabric;

/**
 * Impl of {@link CreateAccountTracker}
 */
public class CreateAccountTrackerImpl implements CreateAccountTracker {

    private static final String USER_LOGIN_METHOD = "User";
    private static final String GUEST_USER_LOGIN_METHOD = "GuestUser";

    /**
     * {@inheritDoc}
     */
    @Override
    public void trackUserLoginSuccess() {
        if (!Fabric.isInitialized()) return;
        Answers.getInstance().logLogin(new LoginEvent()
                .putMethod(USER_LOGIN_METHOD)
                .putSuccess(true));
    }

    @Override
    public void trackGuestUserLoginSuccess() {
        if (!Fabric.isInitialized()) return;
        Answers.getInstance().logLogin(new LoginEvent()
                .putMethod(GUEST_USER_LOGIN_METHOD)
                .putSuccess(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trackUserLoginFailed(String errorMessage) {
        if (!Fabric.isInitialized()) return;
        Answers.getInstance().logLogin(new LoginEvent()
                .putMethod(USER_LOGIN_METHOD)
                .putSuccess(false)
                .putCustomAttribute("errorMessage", errorMessage));
    }

    @Override
    public void trackGuestUserLoginFailed(String errorMessage) {
        if (!Fabric.isInitialized()) return;
        Answers.getInstance().logLogin(new LoginEvent()
                .putMethod(GUEST_USER_LOGIN_METHOD)
                .putSuccess(false)
                .putCustomAttribute("errorMessage", errorMessage));
    }

    @Override
    public void trackUserDataSaveFailed() {
        trackUserLoginFailed("Failed to save user data!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trackView() {
        if (!Fabric.isInitialized()) return;
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Create account"));
    }
}
