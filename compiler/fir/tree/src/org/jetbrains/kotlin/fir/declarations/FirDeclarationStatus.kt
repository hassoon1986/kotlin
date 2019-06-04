/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.BaseTransformedType
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus.Modifier.*
import org.jetbrains.kotlin.fir.visitors.FirVisitor

@BaseTransformedType
abstract class FirDeclarationStatus(
    session: FirSession,
    psi: PsiElement?
) : FirElement(session, psi) {
    abstract val visibility: Visibility

    abstract val modality: Modality?

    protected var flags: Int = 0

    private operator fun get(modifier: Modifier): Boolean = (flags and modifier.mask) != 0

    private operator fun set(modifier: Modifier, value: Boolean) {
        flags = if (value) {
            flags or modifier.mask
        } else {
            flags and modifier.mask.inv()
        }
    }

    var isExpect: Boolean
        get() = this[EXPECT]
        set(value) {
            this[EXPECT] = value
        }

    var isActual: Boolean
        get() = this[ACTUAL]
        set(value) {
            this[ACTUAL] = value
        }

    var isOverride: Boolean
        get() = this[OVERRIDE]
        set(value) {
            this[OVERRIDE] = value
        }

    var isOperator: Boolean
        get() = this[OPERATOR]
        set(value) {
            this[OPERATOR] = value
        }

    var isInfix: Boolean
        get() = this[INFIX]
        set(value) {
            this[INFIX] = value
        }

    var isInline: Boolean
        get() = this[INLINE]
        set(value) {
            this[INLINE] = value
        }

    var isTailRec: Boolean
        get() = this[TAILREC]
        set(value) {
            this[TAILREC] = value
        }

    var isExternal: Boolean
        get() = this[EXTERNAL]
        set(value) {
            this[EXTERNAL] = value
        }

    var isConst: Boolean
        get() = this[CONST]
        set(value) {
            this[CONST] = value
        }

    var isLateInit: Boolean
        get() = this[LATEINIT]
        set(value) {
            this[LATEINIT] = value
        }

    var isInner: Boolean
        get() = this[INNER]
        set(value) {
            this[INNER] = value
        }

    var isCompanion: Boolean
        get() = this[COMPANION]
        set(value) {
            this[COMPANION] = value
        }

    var isData: Boolean
        get() = this[DATA]
        set(value) {
            this[DATA] = value
        }

    var isSuspend: Boolean
        get() = this[SUSPEND]
        set(value) {
            this[SUSPEND] = value
        }

    var isStatic: Boolean
        get() = this[STATIC]
        set(value) {
            this[STATIC] = value
        }

    private enum class Modifier(val mask: Int) {
        EXPECT(0x1),
        ACTUAL(0x2),
        OVERRIDE(0x4),
        OPERATOR(0x8),
        INFIX(0x10),
        INLINE(0x20),
        TAILREC(0x40),
        EXTERNAL(0x80),
        CONST(0x100),
        LATEINIT(0x200),
        INNER(0x400),
        COMPANION(0x800),
        DATA(0x1000),
        SUSPEND(0x2000),
        STATIC(0x4000)
    }

    override fun <R, D> accept(visitor: FirVisitor<R, D>, data: D): R =
        visitor.visitDeclarationStatus(this, data)
}