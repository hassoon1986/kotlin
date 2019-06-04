/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirPropertyAccessor(
    session: FirSession,
    psi: PsiElement?
) : @VisitedSupertype FirDeclarationWithBody(session, psi), FirFunction, FirTypedDeclaration {
    abstract val isGetter: Boolean

    val isSetter: Boolean get() = !isGetter

    abstract val status: FirDeclarationStatus

    val visibility: Visibility get() = status.visibility

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitPropertyAccessor(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super.acceptChildren(visitor, data)
        returnTypeRef.accept(visitor, data)
        for (parameter in valueParameters) {
            parameter.accept(visitor, data)
        }
        status.accept(visitor, data)
    }
}