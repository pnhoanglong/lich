/*
 * Copyright 2019 LINE Corporation
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
package com.linecorp.lich.component.debug.internal

import com.linecorp.lich.component.provider.ComponentProvider
import com.linecorp.lich.component.provider.internal.ComponentCreator
import com.linecorp.lich.component.provider.internal.ComponentProviderFactory

class DebugComponentProviderFactory : ComponentProviderFactory {

    private val decorator: ComponentProviderDecorator? = try {
        Class.forName("com.linecorp.lich.component.test.internal.MockComponentProviderDecorator")
            .newInstance() as ComponentProviderDecorator
    } catch (e: Exception) {
        null
    }

    override fun newComponentProvider(creator: ComponentCreator): ComponentProvider {
        val debugComponentProvider = DebugComponentProviderImpl(creator)
        return decorator?.decorate(debugComponentProvider) ?: debugComponentProvider
    }
}
