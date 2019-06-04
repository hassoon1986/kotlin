/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import org.jetbrains.kotlin.fir.FirReference
import org.jetbrains.kotlin.fir.visitors.FirTransformer

interface FirQualifiedAccess {
    val calleeReference: FirReference

    val safe: Boolean get() = false

    val explicitReceiver: FirExpression? get() = null

    fun <D> transformCalleeReference(transformer: FirTransformer<D>, data: D): FirQualifiedAccess

    fun <D> transformExplicitReceiver(transformer: FirTransformer<D>, data: D): FirQualifiedAccess
}