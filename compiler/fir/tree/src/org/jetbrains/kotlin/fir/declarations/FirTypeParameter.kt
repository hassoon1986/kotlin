/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirAnnotationContainer
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.Variance

abstract class FirTypeParameter(
    session: FirSession,
    psi: PsiElement?,
    name: Name
) : FirNamedDeclaration(session, psi, name), FirSymbolOwner<FirTypeParameter> {
    abstract val variance: Variance

    abstract val isReified: Boolean

    abstract val bounds: List<FirTypeRef>

    abstract override val symbol: FirTypeParameterSymbol

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitTypeParameter(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        for (bound in bounds) {
            bound.accept(visitor, data)
        }
    }
}