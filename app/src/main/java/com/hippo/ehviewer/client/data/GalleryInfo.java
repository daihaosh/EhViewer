/*
 * Copyright 2017 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.client.data;

/*
 * Created by Hippo on 1/29/2017.
 */

import android.util.Log;
import com.hippo.ehviewer.client.EhUtils;
import com.hippo.ehviewer.util.JsonStore;
import com.hippo.yorozuya.ObjectUtils;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Gallery Information.
 * <p>
 * There three ways to obtain a {@code GalleryInfo}.
 * <ul>
 * <li>Parsing gallery list html</li>
 * <li>Parsing gallery detail html</li>
 * <li>Gallery Metadata API</li>
 * </ul>
 * None of these methods can fill all fields.
 */
@JsonStore.Info(
    version = 1,
    name = "com.hippo.ehviewer:GalleryInfo"
)
public class GalleryInfo implements JsonStore.Item {

  private static final String LOG_TAG = GalleryInfo.class.getSimpleName();

  /**
   * Gallery ID.
   * <p>
   * {@code int} should be enough for a long long time.
   * But I like {@code long}.
   */
  public long gid;
  /**
   * Gallery token. Most gallery operations need it.
   * <p>
   * NonNull, must be valid.
   * <p>
   * Regex:<pre>{@code
   * [0-9a-f]{10}
   * }</pre>
   * Example:<pre>{@code
   * c219d2cf41
   * }</pre>
   */
  public String token;
  /**
   * Gallery title.
   * <p>
   * May be {@code null} if user enable show jp title.
   * <p>
   * One of {@code title} and {@code titleJpn} must be non-null.
   */
  public String title;
  /**
   * Gallery title.
   * <p>
   * {@code null} if can't get it.
   * <p>
   * One of {@code title} and {@code titleJpn} must be non-null.
   */
  public String titleJpn;
  /**
   * The fingerprint of the first image.
   * <p>
   * Format:
   * {@code [sha1]-[size]-[width]-[height]-[format]}
   * <p>
   * Regex:
   * {@code [0-9a-f]{40}-\d+-\d+-\d+-[0-9a-z]+}
   * <p>
   * Example:
   * {@code 7dd3e4a62807a6938910a14407d9867b18a58a9f-2333088-2831-4015-jpg}
   * <p>
   * {@code null} if can't get it.
   */
  public String cover;
  /**
   * The url of the cover.
   * <p>
   * {@code null} if can't get it.
   */
  public String coverUrl;
  /**
   * Cover width / Cover height.
   * <p>
   * {@link Float#NaN} if can't it.
   */
  public float coverRatio = Float.NaN;
  /**
   * Gallery category.
   * <p>
   * {@link com.hippo.ehviewer.client.EhUtils#UNKNOWN} if can't get it.
   */
  public int category = EhUtils.UNKNOWN;
  /**
   * Posted time stamp.
   * <p>
   * {@code 0} if can't get it.
   */
  public long date;
  /**
   * Who uploads the gallery.
   * <p>
   * {@code null} if can't get it.
   */
  public String uploader;
  /**
   * Gallery Rating.
   * <p>
   * Range: {@code [0.5, 5]}
   * <p>
   * {@link Float#NaN} if can't it, or if no rating temporarily.
   */
  public float rating = Float.NaN;

  /**
   * Gallery Language.
   * <p>
   * {@link EhUtils#LANG_UNKNOWN} if can't get it.
   */
  public int language = EhUtils.LANG_UNKNOWN;

  /**
   * Favourite slot.
   * <p>
   * Range: {@code [-1, 9]}
   * <p>
   * {@code -1} for un-favourited.
   */
  public int favouriteSlot = -1;

  /**
   * Expunged, deleted or replaced.
   */
  public boolean invalid = false;

  /**
   * The key to download archive.
   */
  public String archiverKey;

  /**
   * Gallery Pages.
   * <p>
   * {@code -1} for unknown.
   */
  public int pages = -1;

  /**
   * Gallery size in bytes.
   * <p>
   * {@code -1} for unknown.
   */
  public long size = -1;

  /**
   * Torrent count.
   * <p>
   * {@code 0} for default.
   */
  public int torrentCount = 0;

  /**
   * Gallery tags.
   * <p>
   * Default empty map.
   */
  public final LinkedHashMap<String, List<String>> tags = new LinkedHashMap<>();

  /**
   * Merges data in {@code info} to this {@code GalleryInfo}.
   */
  public void merge(GalleryInfo info) {
    if (info == null) {
      return;
    }
    if (info.gid != gid || !ObjectUtils.equals(info.token, token)) {
      Log.w(LOG_TAG, "Can't merge different GalleryInfo");
    }

    if (info.title != null) {
      title = info.title;
    }
    if (info.titleJpn != null) {
      titleJpn = info.titleJpn;
    }
    if (info.cover != null) {
      cover = info.cover;
    }
    if (info.coverUrl != null) {
      coverUrl = info.coverUrl;
    }
    if (!Float.isNaN(info.coverRatio)) {
      coverRatio = info.coverRatio;
    }
    if (info.category != EhUtils.UNKNOWN) {
      category = info.category;
    }
    if (info.date != 0) {
      date = info.date;
    }
    if (info.uploader != null) {
      uploader = info.uploader;
    }
    if (!Float.isNaN(info.rating)) {
      rating = info.rating;
    }
    if (info.language != EhUtils.LANG_UNKNOWN) {
      language = info.language;
    }
    if (info.favouriteSlot != -1) {
      favouriteSlot = info.favouriteSlot;
    }
    if (info.invalid) {
      invalid = true;
    }
    if (info.archiverKey != null) {
      archiverKey = info.archiverKey;
    }
    if (info.pages != -1) {
      pages = info.pages;
    }
    if (info.size != -1) {
      size = info.size;
    }
    if (info.torrentCount != 0) {
      torrentCount = info.torrentCount;
    }
    if (!info.tags.isEmpty()) {
      tags.clear();
      tags.putAll(info.tags);
    }
  }

  /**
   * Merges the first same {@code GalleryInfo} in {@code list}.
   */
  public void merge(List<GalleryInfo> list) {
    if (list == null) {
      return;
    }
    for (GalleryInfo info: list) {
      if (info != null && info.gid == gid && ObjectUtils.equals(info.token, token)) {
        merge(info);
        return;
      }
    }
  }

  @Override
  public boolean onFetch(int version) {
    return true;
  }
}
