/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirField(
    session: FirSession,
    override val symbol: FirFieldSymbol,
    name: Name,
    visibility: Visibility,
    modality: Modality?,
    returnTypeRef: FirTypeRef,
    override val isVar: Boolean
) : @VisitedSupertype FirCallableMemberDeclaration<FirField>(
    session,
    psi = null,
    name = name,
    visibility = visibility,
    modality = modality,
    isExpect = false,
    isActual = false,
    isOverride = false,
    receiverTypeRef = null,
    returnTypeRef = returnTypeRef
), FirValVarOwner {
    abstract val delegate: FirExpression?

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitField(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        initializer?.accept(visitor, data)
        delegate?.accept(visitor, data)
    }
}