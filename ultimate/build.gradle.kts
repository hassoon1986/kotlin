
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

description = "Kotlin IDEA Ultimate plugin"

plugins {
    kotlin("jvm")
}

val ideaProjectResources = provider { project(":idea").mainSourceSet.output.resourcesDir }
val intellijUltimateEnabled : Boolean by rootProject.extra

val springClasspath by configurations.creating
val ideaPlugin by configurations.creating

dependencies {
    if (intellijUltimateEnabled) {
        testRuntime(intellijUltimateDep())
    }

    compileOnly(project(":kotlin-reflect-api"))
    compile(kotlinStdlib("jdk8"))
    compile(project(":core:descriptors")) { isTransitive = false }
    compile(project(":compiler:psi")) { isTransitive = false }
    compile(project(":core:descriptors.jvm")) { isTransitive = false }
    compile(project(":core:util.runtime")) { isTransitive = false }
    compile(project(":compiler:light-classes")) { isTransitive = false }
    compile(project(":core:type-system")) { isTransitive = false }
    compile(project(":compiler:frontend")) { isTransitive = false }
    compile(project(":compiler:frontend.common")) { isTransitive = false }
    compile(project(":compiler:frontend.java")) { isTransitive = false }
    compile(project(":compiler:util")) { isTransitive = false }
    compile(project(":js:js.frontend")) { isTransitive = false }
    compile(project(":idea")) { isTransitive = false }
    compile(project(":idea:idea-jvm")) { isTransitive = false }
    compile(project(":idea:idea-core")) { isTransitive = false }
    compile(project(":idea:ide-common")) { isTransitive = false }
    compile(project(":idea:idea-gradle")) { isTransitive = false }
    compile(project(":compiler:util")) { isTransitive = false }
    compile(project(":idea:idea-jps-common")) { isTransitive = false }
    compileOnly(intellijCoreDep()) { includeJars("intellij-core") }

    if (intellijUltimateEnabled) {
        compileOnly(nodeJSPlugin())
        compileOnly(intellijUltimateDep()) { includeJars("trove4j", "openapi", "platform-api", "platform-impl", "java-api", "java-impl", "idea", "util", "jdom") }
        compileOnly(intellijUltimatePluginDep("CSS"))
        compileOnly(intellijUltimatePluginDep("DatabaseTools"))
        compileOnly(intellijUltimatePluginDep("JavaEE"))
        compileOnly(intellijUltimatePluginDep("jsp"))
        compileOnly(intellijUltimatePluginDep("PersistenceSupport"))
        compileOnly(intellijUltimatePluginDep("Spring"))
        compileOnly(intellijUltimatePluginDep("properties"))
        compileOnly(intellijUltimatePluginDep("java-i18n"))
        compileOnly(intellijUltimatePluginDep("gradle"))
        compileOnly(intellijUltimatePluginDep("Groovy"))
        compileOnly(intellijUltimatePluginDep("junit"))
        compileOnly(intellijUltimatePluginDep("uml"))
        compileOnly(intellijUltimatePluginDep("JavaScriptLanguage"))
        compileOnly(intellijUltimatePluginDep("JavaScriptDebugger"))
    }

    testCompile(project(":kotlin-test:kotlin-test-jvm"))
    testCompile(projectTests(":idea:idea-test-framework")) { isTransitive = false }
    testCompile(project(":plugins:lint")) { isTransitive = false }
    testCompile(project(":idea:idea-jvm")) { isTransitive = false }
    testCompile(projectTests(":compiler:tests-common"))
    testCompile(projectTests(":idea")) { isTransitive = false }
    testCompile(projectTests(":generators:test-generator"))
    testCompile(commonDep("junit:junit"))

    testCompile(project(":idea:idea-native")) { isTransitive = false }
    testCompile(project(":idea:idea-gradle-native")) { isTransitive = false }
    testRuntime(project(":kotlin-native:kotlin-native-library-reader")) { isTransitive = false }
    testRuntime(project(":kotlin-native:kotlin-native-utils")) { isTransitive = false }

    if (intellijUltimateEnabled) {
        testCompileOnly(intellijUltimateDep()) { includeJars("platform-api", "platform-impl", "gson", "trove4j", "openapi", "idea", "util", "jdom", rootProject = rootProject) }
    }
    testCompile(commonDep("org.jetbrains.kotlinx", "kotlinx-coroutines-core")) { isTransitive = false }

    testRuntime(project(":kotlin-reflect"))
    testRuntime(project(":kotlin-script-runtime"))
    testRuntimeOnly(project(":kotlin-compiler"))
    testRuntime(project(":plugins:android-extensions-ide")) { isTransitive = false }
    testRuntime(project(":plugins:android-extensions-compiler")) { isTransitive = false }
    testRuntime(project(":plugins:annotation-based-compiler-plugins-ide-support")) { isTransitive = false }
    testRuntime(project(":idea:idea-android")) { isTransitive = false }
    testRuntime(project(":idea:idea-maven")) { isTransitive = false }
    testRuntime(project(":idea:idea-jps-common")) { isTransitive = false }
    testRuntime(project(":idea:formatter")) { isTransitive = false }
    testRuntime(project(":sam-with-receiver-ide-plugin")) { isTransitive = false }
    testRuntime(project(":kotlin-sam-with-receiver-compiler-plugin")) { isTransitive = false }
    testRuntime(project(":noarg-ide-plugin")) { isTransitive = false }
    testRuntime(project(":kotlin-noarg-compiler-plugin")) { isTransitive = false }
    testRuntime(project(":allopen-ide-plugin")) { isTransitive = false }
    testRuntime(project(":kotlin-allopen-compiler-plugin")) { isTransitive = false }
    testRuntime(project(":kotlin-scripting-idea")) { isTransitive = false }
    testRuntime(project(":kotlin-scripting-compiler-impl")) { isTransitive = false }
    testRuntime(project(":kotlinx-serialization-compiler-plugin")) { isTransitive = false }
    testRuntime(project(":kotlinx-serialization-ide-plugin")) { isTransitive = false }
    testRuntime(project(":plugins:kapt3-idea")) { isTransitive = false }
    testRuntime(project(":plugins:uast-kotlin"))
    testRuntime(project(":plugins:uast-kotlin-idea"))
    testRuntime(intellijPluginDep("smali"))

    if (intellijUltimateEnabled) {
        testCompile(nodeJSPlugin())
        testCompile(intellijUltimatePluginDep("CSS"))
        testCompile(intellijUltimatePluginDep("DatabaseTools"))
        testCompile(intellijUltimatePluginDep("JavaEE"))
        testCompile(intellijUltimatePluginDep("jsp"))
        testCompile(intellijUltimatePluginDep("PersistenceSupport"))
        testCompile(intellijUltimatePluginDep("Spring"))
        testCompile(intellijUltimatePluginDep("uml"))
        testCompile(intellijUltimatePluginDep("JavaScriptLanguage"))
        testCompile(intellijUltimatePluginDep("JavaScriptDebugger"))
        testCompile(intellijUltimatePluginDep("properties"))
        testCompile(intellijUltimatePluginDep("java-i18n"))
        testCompile(intellijUltimatePluginDep("gradle"))
        testCompile(intellijUltimatePluginDep("Groovy"))
        testCompile(intellijUltimatePluginDep("junit"))
        testRuntime(intellijUltimatePluginDep("coverage"))
        testRuntime(intellijUltimatePluginDep("maven"))
        testRuntime(intellijUltimatePluginDep("android"))
        testRuntime(intellijUltimatePluginDep("testng"))
        testRuntime(intellijUltimatePluginDep("IntelliLang"))
        testRuntime(intellijUltimatePluginDep("copyright"))
        testRuntime(intellijUltimatePluginDep("java-decompiler"))
    }

    testRuntime(files("${System.getProperty("java.home")}/../lib/tools.jar"))

    springClasspath(commonDep("org.springframework", "spring-core"))
    springClasspath(commonDep("org.springframework", "spring-beans"))
    springClasspath(commonDep("org.springframework", "spring-context"))
    springClasspath(commonDep("org.springframework", "spring-tx"))
    springClasspath(commonDep("org.springframework", "spring-web"))

    ideaPlugin(project(":prepare:idea-plugin", configuration = "runtimeJar"))
}

val preparedResources = File(buildDir, "prepResources")

sourceSets {
    "main" { projectDefault() }
    "test" {
        projectDefault()
        resources.srcDir(preparedResources)
    }
}

val ultimatePluginXmlContent: String by lazy {
    val sectRex = Regex("""^\s*</?idea-plugin>\s*$""")
    File(projectDir, "resources/META-INF/ultimate-plugin.xml")
            .readLines()
            .filterNot { it.matches(sectRex) }
            .joinToString("\n")
}

val prepareResources by task<Copy> {
    dependsOn(":idea:assemble")
    from(ideaProjectResources, {
        exclude("META-INF/plugin.xml")
    })
    into(preparedResources)
}

val preparePluginXml by task<Copy> {
    dependsOn(":idea:assemble")
    from(ideaProjectResources, { include("META-INF/plugin.xml") })
    into(preparedResources)
    filter {
        it?.replace("<!-- ULTIMATE-PLUGIN-PLACEHOLDER -->", ultimatePluginXmlContent)
    }
}

val jar = runtimeJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(preparePluginXml)
    dependsOn(ideaPlugin)
    
    from(provider { zipTree(ideaPlugin.singleFile) }) { 
        exclude("META-INF/plugin.xml") 
    }
    
    from(preparedResources, { include("META-INF/plugin.xml") })
    from(mainSourceSet.output)
    archiveName = "kotlin-plugin.jar"
}

val ideaPluginDir: File by rootProject.extra
val ideaUltimatePluginDir: File by rootProject.extra

task<Copy>("ideaUltimatePlugin") {
    dependsOn(":ideaPlugin")
    into(ideaUltimatePluginDir)
    from(ideaPluginDir) { exclude("lib/kotlin-plugin.jar") }
    from(jar, { into("lib") })
}

task("idea-ultimate-plugin") {
    dependsOn("ideaUltimatePlugin")
    doFirst { logger.warn("'$name' task is deprecated, use '${dependsOn.last()}' instead") }
}

task("ideaUltimatePluginTest") {
    dependsOn("check")
}

projectTest {
    dependsOn(prepareResources)
    dependsOn(preparePluginXml)
    workingDir = rootDir
    doFirst {
        if (intellijUltimateEnabled) {
            systemProperty("idea.home.path", intellijUltimateRootDir().canonicalPath)
        }
        systemProperty("spring.classpath", springClasspath.asPath)
    }
}

val generateTests by generator("org.jetbrains.kotlin.tests.GenerateUltimateTestsKt")
