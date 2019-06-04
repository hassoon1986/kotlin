/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.expressions.FirArgumentContainer
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name

abstract class FirEnumEntry(
    session: FirSession,
    psi: PsiElement?,
    name: Name
) : @VisitedSupertype FirRegularClass(session, psi, name, Visibilities.UNKNOWN, Modality.FINAL, false, false), FirArgumentContainer {
    abstract val typeRef: FirTypeRef

    abstract fun replaceTypeRef(newTypeRef: FirTypeRef)

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitEnumEntry(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        for (argument in arguments) {
            argument.accept(visitor, data)
        }
        typeRef.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }
}