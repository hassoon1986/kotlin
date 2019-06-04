/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirNamedFunction :
    @VisitedSupertype FirCallableMemberDeclaration<FirNamedFunction>, FirFunction, FirSymbolOwner<FirNamedFunction> {

    // NB: FirNamedFunctionSymbol or FirAccessorSymbol
    override val symbol: FirCallableSymbol<FirNamedFunction>

    constructor(
        session: FirSession,
        psi: PsiElement?,
        symbol: FirCallableSymbol<FirNamedFunction>,
        name: Name,
        receiverTypeRef: FirTypeRef?,
        returnTypeRef: FirTypeRef
    ) : super(session, psi, name, receiverTypeRef, returnTypeRef) {
        this.symbol = symbol
        symbol.bind(this)
    }

    constructor(
        session: FirSession,
        psi: PsiElement?,
        symbol: FirNamedFunctionSymbol,
        name: Name,
        visibility: Visibility,
        modality: Modality?,
        isExpect: Boolean,
        isActual: Boolean,
        isOverride: Boolean,
        isOperator: Boolean,
        isInfix: Boolean,
        isInline: Boolean,
        isTailRec: Boolean,
        isExternal: Boolean,
        isSuspend: Boolean,
        receiverTypeRef: FirTypeRef?,
        returnTypeRef: FirTypeRef
    ) : super(
        session, psi, name, visibility, modality,
        isExpect, isActual, isOverride, receiverTypeRef, returnTypeRef
    ) {
        status.isOperator = isOperator
        status.isInfix = isInfix
        status.isInline = isInline
        status.isTailRec = isTailRec
        status.isExternal = isExternal
        status.isSuspend = isSuspend
        this.symbol = symbol
        symbol.bind(this)
    }

    val isOperator: Boolean get() = status.isOperator

    val isInfix: Boolean get() = status.isInfix

    val isInline: Boolean get() = status.isInline

    val isTailRec: Boolean get() = status.isTailRec

    val isExternal: Boolean get() = status.isExternal

    val isSuspend: Boolean get() = status.isSuspend

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitNamedFunction(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        for (parameter in valueParameters) {
            parameter.accept(visitor, data)
        }
        body?.accept(visitor, data)
    }
}