/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.symbols.ConeClassifierSymbol
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol
import org.jetbrains.kotlin.name.Name

abstract class FirClassLikeDeclaration<F : FirClassLikeDeclaration<F>>(
    session: FirSession,
    psi: PsiElement?,
    name: Name,
    visibility: Visibility,
    modality: Modality?,
    isExpect: Boolean,
    isActual: Boolean
) : @VisitedSupertype FirMemberDeclaration(session, psi, name, visibility, modality, isExpect, isActual), FirSymbolOwner<F> {
    abstract override val symbol: FirClassLikeSymbol<F>
}

fun ConeClassifierSymbol.toFirClassLike(): FirClassLikeDeclaration<*>? =
    when (this) {
        is FirClassSymbol -> this.fir
        is FirTypeAliasSymbol -> this.fir
        else -> null
    }
