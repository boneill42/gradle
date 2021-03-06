/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.model.internal.core;

import org.gradle.api.Nullable;
import org.gradle.model.internal.core.rule.describe.ModelRuleDescriptor;
import org.gradle.model.internal.type.ModelType;

import java.util.Collections;
import java.util.List;

public class FactoryBasedNodeInitializer<T, S extends T> implements NodeInitializer {
    private final InstanceFactory<T> instanceFactory;
    private final ModelType<S> type;

    public FactoryBasedNodeInitializer(InstanceFactory<T> instanceFactory, ModelType<S> type) {
        this.instanceFactory = instanceFactory;
        this.type = type;
    }

    @Override
    public List<? extends ModelReference<?>> getInputs() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MutableModelNode modelNode, List<ModelView<?>> inputs) {
        S item = instanceFactory.create(type, modelNode, modelNode.getPath().getName());
        modelNode.setPrivateData(type, item);
    }

    @Override
    public List<? extends ModelProjection> getProjections() {
        return Collections.singletonList(UnmanagedModelProjection.of(type));
    }

    @Nullable
    @Override
    public ModelAction getProjector(final ModelPath path, final ModelRuleDescriptor descriptor) {
        return new AbstractModelAction<Object>(ModelReference.of(path), descriptor) {
            @Override
            public void execute(MutableModelNode modelNode, List<ModelView<?>> inputs) {
                for (ModelType<?> internalView : instanceFactory.getInternalViews(type)) {
                    modelNode.addProjection(UnmanagedModelProjection.of(internalView));
                }
            }
        };
    }
}
