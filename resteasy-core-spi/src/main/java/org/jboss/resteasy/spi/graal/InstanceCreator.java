/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.resteasy.spi.graal;

import java.lang.reflect.Constructor;

/**
 *
 * @author jdenise
 */
public interface InstanceCreator {
    public static final String INSTANCE_CREATOR_SERVLET_PARAMETER = "org.jboss.resteasy.graal.instance.creator";

    public Constructor[] getConstructors(Class clazz);

    public Constructor[] getDeclaredConstructors(Class clazz);

    public <T> T newInstance(Class<T> type) throws Exception;
}
