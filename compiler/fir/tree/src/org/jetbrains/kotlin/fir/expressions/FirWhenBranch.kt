/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.expressions

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirWhenBranch(
    session: FirSession,
    psi: PsiElement?
) : FirElement(session, psi) {
    // NB: we can represent subject, if it's inside, as a special kind of expression
    // when (mySubject) {
    //     $subj == 42$ -> doSmth()
    // }
    abstract val condition: FirExpression

    abstract val result: FirBlock

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitWhenBranch(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        condition.accept(visitor, data)
        result.accept(visitor, data)
    }
}