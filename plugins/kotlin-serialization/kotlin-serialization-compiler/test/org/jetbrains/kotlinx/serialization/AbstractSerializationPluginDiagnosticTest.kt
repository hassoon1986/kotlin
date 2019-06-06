/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlinx.serialization

import junit.framework.TestCase
import org.jetbrains.kotlin.checkers.AbstractDiagnosticsTest
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor
import org.jetbrains.kotlin.utils.PathUtil
import org.jetbrains.kotlinx.serialization.compiler.extensions.SerializationPluginComponentContainerContributor
import java.io.File

abstract class AbstractSerializationPluginDiagnosticTest : AbstractDiagnosticsTest() {
    private val runtimeLibraryPath = getSerializationLibraryRuntimeJar()

    fun testRuntimeLibraryExists() {
        TestCase.assertNotNull(
            "kotlinx-serialization runtime library is not found. Make sure it is present in test classpath",
            runtimeLibraryPath
        )
    }

    override fun createEnvironment(file: File) = super.createEnvironment(file).apply {
        StorageComponentContainerContributor.registerExtension(project, SerializationPluginComponentContainerContributor())
        updateClasspath(listOf(JvmClasspathRoot(runtimeLibraryPath!!)))
    }
}

private fun getSerializationLibraryRuntimeJar(): File? = try {
    PathUtil.getResourcePathForClass(Class.forName("kotlinx.serialization.KSerializer"))
} catch (e: ClassNotFoundException) {
    null
}
