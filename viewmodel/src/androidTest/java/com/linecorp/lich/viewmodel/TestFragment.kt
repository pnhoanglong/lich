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
package com.linecorp.lich.viewmodel

import androidx.fragment.app.Fragment

class TestFragment : Fragment() {

    val testViewModel by viewModel(TestViewModel)

    val testActivityViewModel by activityViewModel(TestViewModel)

    override fun onStart() {
        super.onStart()
        println("TestFragment.onStart: testViewModel = $testViewModel")
        println("TestFragment.onStart: testActivityViewModel = $testActivityViewModel")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("TestFragment.onDestroy")
    }
}
