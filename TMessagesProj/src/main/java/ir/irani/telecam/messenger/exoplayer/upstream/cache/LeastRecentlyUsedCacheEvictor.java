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
package ir.irani.telecam.messenger.exoplayer.upstream.cache;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Evicts least recently used cache files first.
 */
public final class LeastRecentlyUsedCacheEvictor implements CacheEvictor, Comparator<CacheSpan> {

  private final long maxBytes;
  private final TreeSet<CacheSpan> leastRecentlyUsed;

  private long currentSize;

  public LeastRecentlyUsedCacheEvictor(long maxBytes) {
    this.maxBytes = maxBytes;
    this.leastRecentlyUsed = new TreeSet<>(this);
  }

  @Override
  public void onStartFile(Cache cache, String key, long position, long length) {
    evictCache(cache, length);
  }

  @Override
  public void onSpanAdded(Cache cache, CacheSpan span) {
    leastRecentlyUsed.add(span);
    currentSize += span.length;
    evictCache(cache, 0);
  }

  @Override
  public void onSpanRemoved(Cache cache, CacheSpan span) {
    leastRecentlyUsed.remove(span);
    currentSize -= span.length;
  }

  @Override
  public void onSpanTouched(Cache cache, CacheSpan oldSpan, CacheSpan newSpan) {
    onSpanRemoved(cache, oldSpan);
    onSpanAdded(cache, newSpan);
  }

  @Override
  public int compare(CacheSpan lhs, CacheSpan rhs) {
    long lastAccessTimestampDelta = lhs.lastAccessTimestamp - rhs.lastAccessTimestamp;
    if (lastAccessTimestampDelta == 0) {
      // Use the standard compareTo method as a tie-break.
      return lhs.compareTo(rhs);
    }
    return lhs.lastAccessTimestamp < rhs.lastAccessTimestamp ? -1 : 1;
  }

  private void evictCache(Cache cache, long requiredSpace) {
    while (currentSize + requiredSpace > maxBytes) {
      cache.removeSpan(leastRecentlyUsed.first());
    }
  }

}
