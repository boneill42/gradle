/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.logging.internal;

import org.gradle.api.logging.LogLevel;
import org.gradle.internal.progress.OperationIdentifier;

public class ProgressEvent extends CategorisedOutputEvent {
    private final String status;
    private final OperationIdentifier operationId;

    public ProgressEvent(OperationIdentifier operationId, long timestamp, String category, String status) {
        super(timestamp, category, LogLevel.LIFECYCLE);
        this.operationId = operationId;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("Progress %s", status);
    }

    public OperationIdentifier getOperationId() {
        return operationId;
    }
}
