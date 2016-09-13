/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gemstone.gemfire.internal.cache.partitioned;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

import com.gemstone.gemfire.cache.Operation;
import com.gemstone.gemfire.internal.HeapDataOutputStream;
import com.gemstone.gemfire.internal.cache.EntryEventImpl.OldValueImporter;
import com.gemstone.gemfire.internal.cache.OldValueImporterTestBase;
import com.gemstone.gemfire.internal.cache.partitioned.PutMessage.PutReplyMessage;
import com.gemstone.gemfire.test.junit.categories.UnitTest;

@Category(UnitTest.class)
public class PutPutReplyMessageJUnitTest extends OldValueImporterTestBase {

  @Override
  protected OldValueImporter createImporter() {
    return new PutReplyMessage(1, true, Operation.PUT_IF_ABSENT, null, null, null);
  }

  @Override
  protected Object getOldValueFromImporter(OldValueImporter ovi) {
    return ((PutReplyMessage)ovi).getOldValue();
  }

  @Override
  protected void toData(OldValueImporter ovi, HeapDataOutputStream hdos) throws IOException {
    ((PutReplyMessage)ovi).toData(hdos);
  }

  @Override
  protected void fromData(OldValueImporter ovi, byte[] bytes) throws IOException, ClassNotFoundException {
    ((PutReplyMessage)ovi).fromData(new DataInputStream(new ByteArrayInputStream(bytes)));
  }
}