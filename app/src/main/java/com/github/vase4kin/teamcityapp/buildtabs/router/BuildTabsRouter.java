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

package com.github.vase4kin.teamcityapp.buildtabs.router;

import com.github.vase4kin.teamcityapp.buildlist.api.Build;

/**
 * Router for {@link com.github.vase4kin.teamcityapp.buildtabs.view.BuildTabsActivity}
 */
public interface BuildTabsRouter {

    /**
     * Reopen {@link com.github.vase4kin.teamcityapp.buildtabs.view.BuildTabsActivity}
     *
     * @param build - Build is passed as new intent
     */
    void reopenBuildTabsActivity(Build build);

    /**
     * Share build web url
     *
     * @param webUrl - url to share
     */
    void startShareBuildWebUrlActivity(String webUrl);
}