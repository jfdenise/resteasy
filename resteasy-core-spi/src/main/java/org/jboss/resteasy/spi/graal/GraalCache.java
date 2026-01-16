/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.resteasy.spi.graal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jdenise
 */
public class GraalCache {

    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    // We have a cache of proxies to not make Graal VM be confused with multiple proxies
    // implementing same interface.
    private final Map<Class, Object> proxies = new ConcurrentHashMap<>();

    public GraalCache() {
    }

    public Map<String, Object> getCache() {
        return cache;
    }

    public void add(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void addProxy(Class itf, Object value) {
        proxies.put(itf, value);
    }

    public Object getProxy(Class itf) {
        return proxies.get(itf);
    }
}
