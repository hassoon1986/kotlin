/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.expressions.FirErrorStatement
import org.jetbrains.kotlin.fir.expressions.FirLoop
import org.jetbrains.kotlin.fir.expressions.impl.FirEmptyExpressionBlock
import org.jetbrains.kotlin.fir.expressions.impl.FirErrorExpressionImpl

class FirErrorLoop(
    session: FirSession,
    psi: PsiElement?,
    override val reason: String
) : FirLoop(session, psi, FirErrorExpressionImpl(session, psi, reason)), FirErrorStatement {
    init {
        block = FirEmptyExpressionBlock(session)
    }
}