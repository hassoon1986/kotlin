/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types

import org.jetbrains.kotlin.fir.declarations.FirValueParameter

interface FirFunctionTypeRef {
    val receiverTypeRef: FirTypeRef?

    // May be it should inherit FirFunction?
    val valueParameters: List<FirValueParameter>

    val returnTypeRef: FirTypeRef
}


val FirFunctionTypeRef.parametersCount: Int
    get() = if (receiverTypeRef != null)
        valueParameters.size + 1
    else
        valueParameters.size