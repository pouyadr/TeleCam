/*
 * Copyright (C) 2014 The Android Open Source Project
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
package ir.irani.telecam.messenger.exoplayer;

import ir.irani.telecam.messenger.exoplayer.drm.DrmInitData;

/**
 * Holds a {@link MediaFormat} and corresponding drm scheme initialization data.
 */
public final class MediaFormatHolder {

  /**
   * The format of the media.
   */
  public MediaFormat format;
  /**
   * Initialization data for drm schemes supported by the media. Null if the media is not encrypted.
   */
  public DrmInitData drmInitData;

}
