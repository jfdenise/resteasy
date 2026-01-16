/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.resteasy.core.graal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletConfig;

import org.jboss.resteasy.core.ResteasyContext;
import org.jboss.resteasy.spi.InstanceCreatorHolder;
import org.jboss.resteasy.spi.graal.GraalCache;
import org.jboss.resteasy.spi.graal.GraalExecutionPhase;
import org.jboss.resteasy.spi.graal.InstanceCreator;

/**
 *
 * @author jdenise
 */
public class GraalSetup {

    private static final Map<String, GraalCache> GRAAL_CACHE = new ConcurrentHashMap<>();
    // Can be build or run
    private static final String GRAAL_EXECUTION = "org.jboss.resteasy.graal.execution";

    private static boolean isRuntime;

    public static void addServlet(ServletConfig servletConfig) {

        String name = servletConfig.getServletName();
        String mode = servletConfig.getInitParameter(GraalExecutionPhase.EXECUTION_PATH_SERVLET_PARAMETER);
        if (mode == null) {
            return;
        }
        GraalExecutionPhase phase = GraalExecutionPhase.valueOf(mode.toUpperCase());
        System.out.println("GRAAL PHASE " + phase);
        Map<Class<?>, Object> map = ResteasyContext.getContextDataMap();
        if (phase.equals(GraalExecutionPhase.RUNTIME)) {
            isRuntime = true;
            GraalCache cache = GRAAL_CACHE.get(name);
            if (cache != null) {
                map.put(GraalCache.class, cache);
            }
        } else {
            if (phase.equals(GraalExecutionPhase.BUILDTIME)) {
                GraalCache cache = new GraalCache();
                GRAAL_CACHE.put(name, cache);
                map.put(GraalCache.class, cache);
            }
        }
        Object obj = servletConfig.getServletContext().getAttribute(InstanceCreator.INSTANCE_CREATOR_SERVLET_PARAMETER);
        if (obj != null) {
            System.out.println("GRAAL, INSTALLING InstanceCreator " + obj);
            InstanceCreatorHolder.setCreator((InstanceCreator) obj);
        }
    }

    public static boolean isBuildTime() {
        return !isRuntime;
    }

    public static boolean isRuntime() {
        return isRuntime;
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
