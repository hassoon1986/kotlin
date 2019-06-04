/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirVariable(
    session: FirSession,
    psi: PsiElement?,
    name: Name
) : @VisitedSupertype FirNamedDeclaration(session, psi, name), FirValVarOwner, FirDelegateOwner, FirCallableDeclaration<FirVariable> {
    abstract override val symbol: FirVariableSymbol

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitVariable(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        initializer?.accept(visitor, data)
        delegate?.accept(visitor, data)
        receiverTypeRef?.accept(visitor, data)
        returnTypeRef.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }
}