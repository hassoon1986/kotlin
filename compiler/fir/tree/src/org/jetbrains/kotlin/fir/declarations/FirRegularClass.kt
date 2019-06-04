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
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

// May be all containers should be properties and not base classes
// About descriptors: introduce something like FirDescriptor which is FirUnresolved at the beginning and FirSymbol(descriptor) at the end
abstract class FirRegularClass(
    session: FirSession,
    psi: PsiElement?,
    name: Name,
    visibility: Visibility,
    modality: Modality?,
    isExpect: Boolean,
    isActual: Boolean
) : @VisitedSupertype FirClassLikeDeclaration<FirRegularClass>(
    session, psi, name, visibility, modality, isExpect, isActual
), FirClass, FirSymbolOwner<FirRegularClass> {
    val isInner: Boolean get() = status.isInner

    val isCompanion: Boolean get() = status.isCompanion

    val isData: Boolean get() = status.isData

    val isInline: Boolean get() = status.isInline

    abstract val companionObject: FirRegularClass?

    abstract override val symbol: FirClassSymbol

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitRegularClass(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super<FirClassLikeDeclaration>.acceptChildren(visitor, data)
        super<FirClass>.acceptChildren(visitor, data)
    }

    abstract fun replaceSupertypes(newSupertypes: List<FirTypeRef>): FirRegularClass
}

val FirRegularClass.classId get() = symbol.classId
