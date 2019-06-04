/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types.impl

import org.jetbrains.kotlin.analyzer.ModuleInfo
import org.jetbrains.kotlin.fir.FirSession
import kotlin.reflect.KClass

object FirTypingStubSession : FirSession {
    override val moduleInfo: ModuleInfo?
        get() = error("Stub session")
    override val components: Map<KClass<*>, Any>
        get() = error("Stub session")
}