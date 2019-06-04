/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirReference
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirAssignment
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirOperation
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment
import org.jetbrains.kotlin.fir.transformSingle
import org.jetbrains.kotlin.fir.visitors.FirTransformer

class FirVariableAssignmentImpl(
    session: FirSession,
    psi: PsiElement?,
    override var rValue: FirExpression,
    override val operation: FirOperation,
    override var safe: Boolean = false
) : FirVariableAssignment(session, psi), FirModifiableQualifiedAccess<FirReference> {
    override lateinit var calleeReference: FirReference

    override var explicitReceiver: FirExpression? = null

    override var lValue: FirReference
        get() = calleeReference
        set(value) {
            calleeReference = value
        }

    override fun <D> transformRValue(transformer: FirTransformer<D>, data: D): FirAssignment {
        rValue = rValue.transformSingle(transformer, data)
        return this
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        calleeReference = calleeReference.transformSingle(transformer, data)
        explicitReceiver = explicitReceiver?.transformSingle(transformer, data)
        rValue = rValue.transformSingle(transformer, data)
        return super.transformChildren(transformer, data)
    }
}