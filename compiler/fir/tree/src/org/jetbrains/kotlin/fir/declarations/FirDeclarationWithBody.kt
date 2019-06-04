/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirBlock
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirDeclarationWithBody(
    session: FirSession,
    psi: PsiElement?
) : FirDeclaration(session, psi), FirBodyContainer {
    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitDeclarationWithBody(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        body?.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }
}