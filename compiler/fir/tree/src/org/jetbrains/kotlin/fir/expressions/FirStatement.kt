/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.impl.FirMutableAnnotationContainer
import org.jetbrains.kotlin.fir.transformInplace
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirStatement(
    session: FirSession,
    psi: PsiElement?
) : FirElement(session, psi), FirMutableAnnotationContainer {

    final override val annotations = mutableListOf<FirAnnotationCall>()

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitStatement(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        acceptAnnotations(visitor, data)
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        annotations.transformInplace(transformer, data)
        return this
    }
}