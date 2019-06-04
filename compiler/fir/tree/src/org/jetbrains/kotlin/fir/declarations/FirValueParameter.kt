/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.expressions.FirVariable
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirValueParameter(
    session: FirSession,
    psi: PsiElement?,
    name: Name
) : @VisitedSupertype FirVariable(session, psi, name), FirTypedDeclaration {
    abstract val isCrossinline: Boolean

    abstract val isNoinline: Boolean

    abstract val isVararg: Boolean

    abstract val defaultValue: FirExpression?

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitValueParameter(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        defaultValue?.accept(visitor, data)
    }
}