/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.expressions.FirAnnotationContainer
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.FqName

@BaseTransformedType
abstract class FirFile(
    session: FirSession,
    psi: PsiElement?
) : @VisitedSupertype FirPackageFragment(session, psi), FirAnnotationContainer {

    @Suppress("DEPRECATION")
    val fileSession: FirSession
        get() = session

    abstract val name: String

    abstract val packageFqName: FqName

    abstract val imports: List<FirImport>

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitFile(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        acceptAnnotations(visitor, data)
        for (import in imports) {
            import.accept(visitor, data)
        }
        super.acceptChildren(visitor, data)
    }
}