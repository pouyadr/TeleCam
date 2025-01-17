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
package ir.irani.telecam.messenger.exoplayer.upstream;

import ir.irani.telecam.messenger.exoplayer.C;
import ir.irani.telecam.messenger.exoplayer.util.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A {@link DataSink} for writing to a byte array.
 */
public final class ByteArrayDataSink implements DataSink {

  private ByteArrayOutputStream stream;

  @Override
  public DataSink open(DataSpec dataSpec) throws IOException {
    if (dataSpec.length == C.LENGTH_UNBOUNDED) {
      stream = new ByteArrayOutputStream();
    } else {
      Assertions.checkArgument(dataSpec.length <= Integer.MAX_VALUE);
      stream = new ByteArrayOutputStream((int) dataSpec.length);
    }
    return this;
  }

  @Override
  public void close() throws IOException {
    stream.close();
  }

  @Override
  public void write(byte[] buffer, int offset, int length) throws IOException {
    stream.write(buffer, offset, length);
  }

  /**
   * Returns the data written to the sink since the last call to {@link #open(DataSpec)}.
   *
   * @return The data, or null if {@link #open(DataSpec)} has never been called.
   */
  public byte[] getData() {
    return stream == null ? null : stream.toByteArray();
  }

}
