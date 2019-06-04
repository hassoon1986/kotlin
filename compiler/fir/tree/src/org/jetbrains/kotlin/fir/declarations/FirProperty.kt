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
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirProperty(
    session: FirSession,
    psi: PsiElement?,
    name: Name,
    visibility: Visibility,
    modality: Modality?,
    isExpect: Boolean,
    isActual: Boolean,
    isOverride: Boolean,
    receiverTypeRef: FirTypeRef?,
    returnTypeRef: FirTypeRef
) : @VisitedSupertype FirCallableMemberDeclaration<FirProperty>(
    session, psi, name, visibility, modality, isExpect, isActual, isOverride, receiverTypeRef, returnTypeRef
), FirValVarOwner, FirDelegateOwner, FirSymbolOwner<FirProperty> {
    val isConst: Boolean get() = status.isConst

    val isLateInit: Boolean get() = status.isLateInit

    // Should it be nullable or have some default?
    abstract val getter: FirPropertyAccessor

    abstract val setter: FirPropertyAccessor?

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitProperty(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        initializer?.accept(visitor, data)
        delegate?.accept(visitor, data)
        getter.accept(visitor, data)
        setter?.accept(visitor, data)
    }
}