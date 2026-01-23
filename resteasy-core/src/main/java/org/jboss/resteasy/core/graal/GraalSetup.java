/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.resteasy.core.graal;

import java.util.Map;

import jakarta.servlet.ServletConfig;

import org.jboss.resteasy.core.ResteasyContext;
import org.wildfly.graal.runtime.WildFlyGraalSetup;
import org.wildfly.graal.runtime.WildFlyGraalSetup.GraalCache;

/**
 *
 * @author jdenise
 */
public class GraalSetup {

    public static void addServlet(ServletConfig servletConfig) {

        String name = servletConfig.getServletName();

        Map<Class<?>, Object> map = ResteasyContext.getContextDataMap();
        if (WildFlyGraalSetup.isRuntime()) {
            GraalCache cache = WildFlyGraalSetup.getCache(name);
            if (cache != null) {
                map.put(GraalCache.class, cache);
            }
        } else {
            if (WildFlyGraalSetup.isBuildTime()) {
                GraalCache cache = WildFlyGraalSetup.initCache(name);
                map.put(GraalCache.class, cache);
            }
        }
    }

    public static boolean isBuildTime() {
        return WildFlyGraalSetup.isBuildTime();
    }

    public static boolean isRuntime() {
        return WildFlyGraalSetup.isRuntime();
    }

    public static Object getFromCache(String key) {
        Map<Class<?>, Object> map = ResteasyContext.getContextDataMap();
        GraalCache cache = (GraalCache) map.get(GraalCache.class);
        Object value = null;
        if (cache != null) {
            value = cache.get(key);
        }
        return value;
    }

    public static void addToCache(String key, Object value) {
        Map<Class<?>, Object> map = ResteasyContext.getContextDataMap();
        GraalCache cache = (GraalCache) map.get(GraalCache.class);
        if (cache != null) {
            cache.add(key, value);
        }
    }

    public static Object getProxyFromCache(Class<?> key) {
        Map<Class<?>, Object> map = ResteasyContext.getContextDataMap();
        GraalCache cache = (GraalCache) map.get(GraalCache.class);
        Object value = null;
        if (cache != null) {
            value = cache.getProxy(key);
        }
        return value;
    }

    public static void addProxyToCache(Class<?> key, Object value) {
        Map<Class<?>, Object> map = ResteasyContext.getContextDataMap();
        GraalCache cache = (GraalCache) map.get(GraalCache.class);
        if (cache != null) {
            cache.addProxy(key, value);
        }
    }
}
