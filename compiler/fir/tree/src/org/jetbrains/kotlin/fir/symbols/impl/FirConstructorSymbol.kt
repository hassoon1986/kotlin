/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.symbols.impl

import org.jetbrains.kotlin.fir.declarations.FirConstructor
import org.jetbrains.kotlin.fir.symbols.CallableId
import org.jetbrains.kotlin.fir.types.ConeKotlinType

class FirConstructorSymbol(
    override val callableId: CallableId
) : FirFunctionSymbol<FirConstructor>() {
    override val parameters: List<ConeKotlinType>
        get() = emptyList()
}
