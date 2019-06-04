/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.visitors.FirVisitor

// Is it necessary?
abstract class FirErrorDeclaration(
    session: FirSession,
    psi: PsiElement?
) : FirDeclaration(session, psi) {
    abstract val reason: String

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitErrorDeclaration(this, data)
}