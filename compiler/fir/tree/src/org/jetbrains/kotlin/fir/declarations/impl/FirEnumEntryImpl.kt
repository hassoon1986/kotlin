/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry
import org.jetbrains.kotlin.fir.declarations.FirRegularClass
import org.jetbrains.kotlin.fir.expressions.FirArgumentContainer
import org.jetbrains.kotlin.fir.expressions.FirExpression
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol
import org.jetbrains.kotlin.fir.transformInplace
import org.jetbrains.kotlin.fir.transformSingle
import org.jetbrains.kotlin.fir.types.FirTypeRef
import org.jetbrains.kotlin.fir.types.impl.FirImplicitEnumTypeRef
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.addToStdlib.firstIsInstanceOrNull

class FirEnumEntryImpl(
    session: FirSession,
    psi: PsiElement?,
    override val symbol: FirClassSymbol,
    name: Name
) : FirEnumEntry(session, psi, name), FirModifiableClass {
    init {
        symbol.bind(this)
    }

    override val classKind: ClassKind
        get() = ClassKind.ENUM_ENTRY

    override var typeRef: FirTypeRef = FirImplicitEnumTypeRef(session, null)

    override val arguments = mutableListOf<FirExpression>()

    override val superTypeRefs = mutableListOf<FirTypeRef>()

    override val declarations = mutableListOf<FirDeclaration>()

    override val companionObject: FirRegularClass?
        get() = null

    fun addDeclaration(declaration: FirDeclaration) {
        declarations += declaration
    }

    override fun replaceTypeRef(newTypeRef: FirTypeRef) {
        typeRef = newTypeRef
    }

    override fun replaceSupertypes(newSupertypes: List<FirTypeRef>): FirRegularClass {
        superTypeRefs.clear()
        superTypeRefs.addAll(newSupertypes)
        return this
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        typeRef = typeRef.transformSingle(transformer, data)
        arguments.transformInplace(transformer, data)
        superTypeRefs.transformInplace(transformer, data)
        declarations.transformInplace(transformer, data)

        return super.transformChildren(transformer, data) as FirRegularClass
    }

    override fun <D> transformArguments(transformer: FirTransformer<D>, data: D): FirArgumentContainer {
        arguments.transformInplace(transformer, data)

        return this
    }
}