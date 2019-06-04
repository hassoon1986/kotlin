/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

abstract class FirResolvedImport(
    session: FirSession,
    psi: PsiElement?
) : FirImport(session, psi) {
    abstract val packageFqName: FqName

    abstract val relativeClassName: FqName?

    val resolvedClassId: ClassId? get() = relativeClassName?.let { ClassId(packageFqName, it, false) }

    val importedName: Name? get() = importedFqName?.shortName()

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitResolvedImport(this, data)
}
