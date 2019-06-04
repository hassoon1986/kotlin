/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.visitors.FirVisitor

@BaseTransformedType
abstract class FirVariableAssignment(
    session: FirSession,
    psi: PsiElement?
) : FirStatement(session, psi), FirAssignment {

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitVariableAssignment(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        calleeReference.accept(visitor, data)
        rValue.accept(visitor, data)
        explicitReceiver?.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }
}