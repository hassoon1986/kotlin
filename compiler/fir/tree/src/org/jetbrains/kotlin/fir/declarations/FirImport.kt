/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

@BaseTransformedType
abstract class FirImport(
    session: FirSession,
    psi: PsiElement?
) : FirElement(session, psi) {
    abstract val importedFqName: FqName?

    abstract val isAllUnder: Boolean

    abstract val aliasName: Name?

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitImport(this, data)
}