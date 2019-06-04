/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirAnnotationContainer
import org.jetbrains.kotlin.fir.expressions.impl.FirMutableAnnotationContainer
import org.jetbrains.kotlin.fir.visitors.FirVisitor

@BaseTransformedType
abstract class FirTypeRef(
    session: FirSession,
    psi: PsiElement?
) : FirElement(session, psi), FirAnnotationContainer {
    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitTypeRef(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        acceptAnnotations(visitor, data)
        super.acceptChildren(visitor, data)
    }
}