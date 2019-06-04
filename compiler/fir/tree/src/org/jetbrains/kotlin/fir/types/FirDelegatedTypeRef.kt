/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirDelegatedTypeRef(
    session: FirSession,
    psi: PsiElement?
) : FirTypeRef(session, psi) {
    abstract val delegate: FirExpression?

    abstract val typeRef: FirTypeRef

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitDelegatedTypeRef(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        delegate?.accept(visitor, data)
        typeRef.accept(visitor, data)
    }
}