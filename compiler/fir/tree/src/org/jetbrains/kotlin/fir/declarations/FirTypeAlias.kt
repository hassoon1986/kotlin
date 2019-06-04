/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol
import org.jetbrains.kotlin.fir.types.ConeClassLikeType
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.types.coneTypeSafe
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirTypeAlias(
    session: FirSession,
    psi: PsiElement?,
    name: Name,
    visibility: Visibility,
    isExpect: Boolean,
    isActual: Boolean
) : FirClassLikeDeclaration<FirTypeAlias>(
    session, psi, name, visibility, Modality.FINAL, isExpect, isActual
), FirSymbolOwner<FirTypeAlias> {
    abstract fun replaceExpandTypeRef(typeRef: FirTypeRef): FirTypeAlias

    abstract val expandedTypeRef: FirTypeRef

    abstract override val symbol: FirTypeAliasSymbol

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitTypeAlias(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        expandedTypeRef.accept(visitor, data)
    }
}


val FirTypeAlias.expandedConeType: ConeClassLikeType? get() = expandedTypeRef.coneTypeSafe()
