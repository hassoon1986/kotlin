/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirValueParameter
import org.jetbrains.kotlin.fir.transformInplace
import org.jetbrains.kotlin.fir.transformSingle
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.types.FirTypeRefWithNullability
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.fir.visitors.FirVisitor

class FirFunctionTypeRefImpl(
    session: FirSession,
    psi: PsiElement?,
    isNullable: Boolean,
    override var receiverTypeRef: FirTypeRef?,
    override var returnTypeRef: FirTypeRef
) : FirTypeRefWithNullability(session, psi, isNullable), FirFunctionTypeRef {
    override val valueParameters = mutableListOf<FirValueParameter>()

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        receiverTypeRef?.accept(visitor, data)
        returnTypeRef.accept(visitor, data)
        for (parameter in valueParameters) {
            parameter.accept(visitor, data)
        }
        super.acceptChildren(visitor, data)
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        receiverTypeRef = receiverTypeRef?.transformSingle(transformer, data)
        returnTypeRef = returnTypeRef.transformSingle(transformer, data)
        valueParameters.transformInplace(transformer, data)

        return super.transformChildren(transformer, data)
    }
}