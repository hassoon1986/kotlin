/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.VisitedSupertype
import org.jetbrains.kotlin.fir.transformSingle
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.fir.visitors.FirVisitor
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource

abstract class FirCallableMemberDeclaration<F : FirCallableMemberDeclaration<F>> :
    @VisitedSupertype FirMemberDeclaration, FirCallableDeclaration<F> {

    constructor(
        session: FirSession,
        psi: PsiElement?,
        name: Name,
        receiverTypeRef: FirTypeRef?,
        returnTypeRef: FirTypeRef
    ) : super(session, psi, name) {
        this.receiverTypeRef = receiverTypeRef
        this.returnTypeRef = returnTypeRef
    }

    constructor(
        session: FirSession,
        psi: PsiElement?,
        name: Name,
        visibility: Visibility,
        modality: Modality?,
        isExpect: Boolean,
        isActual: Boolean,
        isOverride: Boolean,
        receiverTypeRef: FirTypeRef?,
        returnTypeRef: FirTypeRef
    ) : super(session, psi, name, visibility, modality, isExpect, isActual) {
        this.receiverTypeRef = receiverTypeRef
        this.returnTypeRef = returnTypeRef
        status.isOverride = isOverride
    }

    val isOverride: Boolean get() = status.isOverride

    val isStatic: Boolean get() = status.isStatic

    var containerSource: DeserializedContainerSource? = null

    final override var receiverTypeRef: FirTypeRef?

    final override var returnTypeRef: FirTypeRef

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R = visitor.visitCallableMemberDeclaration(this, data)

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        receiverTypeRef?.accept(visitor, data)
        returnTypeRef.accept(visitor, data)
        super.acceptChildren(visitor, data)
    }

    override fun <D> transformReturnTypeRef(transformer: FirTransformer<D>, data: D) {
        returnTypeRef = returnTypeRef.transformSingle(transformer, data)
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        receiverTypeRef = receiverTypeRef?.transformSingle(transformer, data)
        returnTypeRef = returnTypeRef.transformSingle(transformer, data)

        return super.transformChildren(transformer, data)
    }

}
