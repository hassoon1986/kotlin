/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.*
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirLoop(
    session: FirSession,
    psi: PsiElement?,
    var condition: FirExpression
) : @VisitedSupertype FirStatement(session, psi), FirLabeledElement, FirAnnotationContainer {
    lateinit var block: FirBlock

    override var label: FirLabel? = null

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitLoop(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        condition.accept(visitor, data)
        block.accept(visitor, data)
        label?.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        condition = condition.transformSingle(transformer, data)
        block = block.transformSingle(transformer, data)
        label = label?.transformSingle(transformer, data)
        return super.transformChildren(transformer, data)
    }
}