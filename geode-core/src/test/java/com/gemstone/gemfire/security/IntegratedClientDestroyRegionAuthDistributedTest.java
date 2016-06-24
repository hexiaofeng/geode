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
package com.gemstone.gemfire.security;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.test.junit.categories.DistributedTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(DistributedTest.class)
public class IntegratedClientDestroyRegionAuthDistributedTest extends AbstractIntegratedClientAuthDistributedTest {

  @Test
  public void testDestroyRegion() throws InterruptedException {
    client1.invoke(()-> {
      Cache cache = SecurityTestUtils.createCacheClient("dataWriter", "1234567", serverPort, SecurityTestUtils.NO_EXCEPTION);
      Region region = cache.getRegion(SecurityTestUtils.REGION_NAME);
      assertNotAuthorized(()->region.destroyRegion(), "DATA:MANAGE");
    });

    client2.invoke(()-> {
      Cache cache = SecurityTestUtils.createCacheClient("authRegionManager", "1234567", serverPort, SecurityTestUtils.NO_EXCEPTION);
      Region region = cache.getRegion(SecurityTestUtils.REGION_NAME);
      assertNotAuthorized(()->region.destroyRegion(), "DATA:MANAGE");
    });

    client3.invoke(()-> {
      Cache cache = SecurityTestUtils.createCacheClient("super-user", "1234567", serverPort, SecurityTestUtils.NO_EXCEPTION);
      Region region = cache.getRegion(SecurityTestUtils.REGION_NAME);
      region.destroyRegion();
    });
  }

}