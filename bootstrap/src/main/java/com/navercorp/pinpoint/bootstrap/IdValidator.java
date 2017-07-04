/*
 * Copyright 2016 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.navercorp.pinpoint.bootstrap;

import com.navercorp.pinpoint.bootstrap.util.IdValidateUtils;
import com.navercorp.pinpoint.bootstrap.util.NetworkUtils;


import java.util.Properties;

/**
 * @author Woonduk Kang(emeroad)
 */
public class IdValidator {

    private final BootLogger logger = BootLogger.getLogger(IdValidator.class.getName());

    private final Properties property;
    private static final int MAX_ID_LENGTH = 50;

    public IdValidator() {
        property = new Properties();
        property.putAll(System.getenv());
        property.putAll(System.getProperties());
        //this(props);
    }

    public IdValidator(Properties property) {
        if (property == null) {
            throw new NullPointerException("property must not be null");
        }
        this.property = property;
    }

    private String getValidId(String propertyName, int maxSize) {
        logger.info("check -D" + propertyName);
        String value = property.getProperty(propertyName);
        if (value == null){
            logger.warn("-D" + propertyName + " is null. value:null");
            return null;
        }
        // blanks not permitted around value
        value = value.trim();
        if (value.isEmpty()) {
            logger.warn("-D" + propertyName + " is empty. value:''");
            return null;
        }

        if (!IdValidateUtils.validateId(value, maxSize)) {
            logger.warn("invalid Id. " + propertyName + " can only contain [a-zA-Z0-9], '.', '-', '_'. maxLength:" + maxSize + " value:" + value);
            return null;
        }

        if (logger.isInfoEnabled()) {
            logger.info("check success. -D" + propertyName + ":" + value + " length:" + IdValidateUtils.getLength(value));
        }
        return value;
    }


    public String getApplicationName() {
        String applicationName =  this.getValidId("pinpoint.applicationName", MAX_ID_LENGTH);
        if (applicationName == null || "".equals(applicationName.trim())) {
            applicationName = this.getValidId("pinpoint_applicationName", MAX_ID_LENGTH);
        }
        return applicationName;
    }

    public String getAgentId() {
        String agentId =  this.getValidId("pinpoint.agentId", MAX_ID_LENGTH);
        if (agentId == null || "".equals(agentId.trim())) {
            agentId =  this.getValidId("pinpoint_agentId", MAX_ID_LENGTH);
        }
        // generate a agentId if it's not been set
        if (agentId == null || "".equals(agentId.trim())) {
            //String machineName = NetworkUtils.getHostName();
            String hostIp = NetworkUtils.getRepresentationHostIp();
            agentId = hostIp + "_"  + getApplicationName();
        }
        return agentId;
    }
}
