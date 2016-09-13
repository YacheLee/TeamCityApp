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

package com.github.vase4kin.teamcityapp.artifact.api;

import android.support.annotation.VisibleForTesting;

import com.github.vase4kin.teamcityapp.api.interfaces.Collectible;

import java.util.List;

/**
 * Artifact files
 */
public class Files implements Collectible<File> {

    private List<File> file;

    @Override
    public List<File> getObjects() {
        return file;
    }

    @VisibleForTesting
    public Files(List<File> file) {
        this.file = file;
    }
}