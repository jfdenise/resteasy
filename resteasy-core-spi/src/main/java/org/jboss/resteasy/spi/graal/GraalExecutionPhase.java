/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.resteasy.spi.graal;

/**
 *
 * @author jfdenise
 */
public enum GraalExecutionPhase {
    RUNTIME("runtime"),
    BUILDTIME("buildtime");

    public static final String EXECUTION_PATH_SERVLET_PARAMETER = "org.jboss.resteasy.graal.execution.phase";
    private final String name;

    GraalExecutionPhase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
