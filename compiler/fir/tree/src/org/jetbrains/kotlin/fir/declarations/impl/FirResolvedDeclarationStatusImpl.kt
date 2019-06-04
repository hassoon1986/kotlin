/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations.impl

import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus

class FirResolvedDeclarationStatusImpl(
    session: FirSession,
    override val visibility: Visibility,
    override val modality: Modality
) : FirResolvedDeclarationStatus(session, null) {

    internal constructor(
        session: FirSession,
        visibility: Visibility,
        modality: Modality,
        flags: Int
    ) : this(session, visibility, modality) {
        this.flags = flags
    }
}