/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.web.api.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.xml.bind.annotation.XmlType;

/**
 * A user group in this NiFi.
 */
@XmlType(name = "userGroup")
public class UserGroupDTO {

    private String group;
    private Set<String> userIds;
    private Set<String> authorities;
    private String status;

    /**
     * @return user group
     */
    @ApiModelProperty(
            value = "The user group."
    )
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return users in this group
     */
    @ApiModelProperty(
            value = "The users that belong to the group."
    )
    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    /**
     * @return status of the users account
     */
    @ApiModelProperty(
            value = "The status of the users accounts."
    )
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return users authorities
     */
    @ApiModelProperty(
            value = "The authorities of the users."
    )
    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
