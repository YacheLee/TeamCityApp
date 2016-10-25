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

package com.github.vase4kin.teamcityapp.account.create.data;

import com.github.vase4kin.teamcityapp.storage.SharedUserStorage;

/**
 * Impl of {@link CreateAccountDataModel}
 */
public class CreateAccountDataModelImpl implements CreateAccountDataModel {

    private SharedUserStorage mSharedUserStorage;

    public CreateAccountDataModelImpl(SharedUserStorage sharedUserStorage) {
        this.mSharedUserStorage = sharedUserStorage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGuestAccountWithUrl(String url) {
        return mSharedUserStorage.hasGuestAccountWithUrl(url);
    }

    @Override
    public boolean hasAccountWithUrl(String url, String userName, String password) {
        return mSharedUserStorage.hasAccountWithUrl(url, userName, password);
    }
}
