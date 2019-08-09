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
package com.linecorp.lich.component

import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Creates a new instance of a [Lazy] that returns a singleton instance of component created from
 * [factory].
 *
 * This is a sample code:
 * ```
 * class FooActivity : Activity() {
 *
 *     private val fooComponent by component(FooComponent)
 *
 *     // snip...
 * }
 * ```
 *
 * @see Fragment.component
 * @see Context.getComponent
 * @see ComponentFactory
 */
fun <T : Any> Context.component(factory: ComponentFactory<T>): Lazy<T> =
    object : ComponentLazy<T>(factory) {
        override val context: Context
            get() = this@component
    }

/**
 * Creates a new instance of a [Lazy] that returns a singleton instance of component created from
 * [factory].
 *
 * This is a sample code:
 * ```
 * class FooFragment : Fragment() {
 *
 *     private val fooComponent by component(FooComponent)
 *
 *     // snip...
 * }
 * ```
 *
 * @see Context.component
 * @see Context.getComponent
 * @see ComponentFactory
 */
fun <T : Any> Fragment.component(factory: ComponentFactory<T>): Lazy<T> =
    object : ComponentLazy<T>(factory) {
        override val context: Context
            get() = requireContext()
    }

private abstract class ComponentLazy<T : Any>(
    private val factory: ComponentFactory<T>
) : Lazy<T> {
    @Volatile
    private var component: T? = null

    protected abstract val context: Context

    // Since "Component" is singleton, no extra synchronization is needed.
    // NOTE: We cannot use `lazy(LazyThreadSafetyMode.NONE) {...}` instead of `ComponentLazy`,
    // because it clears its `initializer` field after creation. It's not thread-safe.
    override val value: T
        get() = component ?: context.getComponent(factory).also { component = it }

    override fun isInitialized(): Boolean = component != null

    override fun toString(): String = component?.toString() ?: "Component is not assigned yet."
}
