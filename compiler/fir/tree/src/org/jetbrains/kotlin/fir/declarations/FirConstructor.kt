/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirConstructor : @VisitedSupertype FirCallableMemberDeclaration<FirConstructor>, FirFunction, FirBodyContainer {

    override val symbol: FirConstructorSymbol

    val delegatedConstructor: FirDelegatedConstructorCall?

    constructor(
        session: FirSession,
        psi: PsiElement?,
        symbol: FirConstructorSymbol,
        visibility: Visibility,
        isExpect: Boolean,
        isActual: Boolean,
        delegatedSelfTypeRef: FirTypeRef,
        delegatedConstructor: FirDelegatedConstructorCall?
    ) : super(
        session, psi, NAME, visibility, Modality.FINAL,
        isExpect, isActual, isOverride = false, receiverTypeRef = null, returnTypeRef = delegatedSelfTypeRef
    ) {
        this.symbol = symbol
        this.delegatedConstructor = delegatedConstructor
        symbol.bind(this)
    }

    constructor(
        session: FirSession,
        psi: PsiElement?,
        symbol: FirConstructorSymbol,
        receiverTypeRef: FirTypeRef?,
        returnTypeRef: FirTypeRef
    ) : super(session, psi, NAME, receiverTypeRef, returnTypeRef) {
        this.symbol = symbol
        this.delegatedConstructor = null
        symbol.bind(this)
    }

    open val isPrimary: Boolean get() = false

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitConstructor(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        acceptAnnotations(visitor, data)
        status.accept(visitor, data)
        delegatedConstructor?.accept(visitor, data)
        for (parameter in valueParameters) {
            parameter.accept(visitor, data)
        }
        returnTypeRef.accept(visitor, data)
        body?.accept(visitor, data)
    }

    companion object {
        val NAME = Name.special("<init>")
    }
}