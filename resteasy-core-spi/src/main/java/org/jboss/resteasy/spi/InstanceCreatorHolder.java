/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.resteasy.spi;

import java.lang.reflect.Constructor;

import org.jboss.resteasy.spi.graal.InstanceCreator;

/**
 *
 * @author jdenise
 */
public class InstanceCreatorHolder {
    static class DefaultCreator implements InstanceCreator {

        @Override
        public Constructor[] getConstructors(Class clazz) {
            return clazz.getConstructors();
        }

        @Override
        public <T> T newInstance(Class<T> type) throws Exception {
            return type.newInstance();
        }

        @Override
        public Constructor[] getDeclaredConstructors(Class clazz) {
            return clazz.getDeclaredConstructors();
        }

    }

    private static InstanceCreator CREATOR = new DefaultCreator();

    public static InstanceCreator getCreator() {
        return CREATOR;
    }

    public static void setCreator(InstanceCreator creator) {
        CREATOR = creator;
    }
}
