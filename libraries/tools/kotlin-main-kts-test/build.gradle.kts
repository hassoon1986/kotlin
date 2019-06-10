
description = "Kotlin \"main\" script definition tests"

plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly("org.apache.ivy:ivy:2.4.0") // for jps/pill
    testCompile(commonDep("junit"))
    testCompile(kotlinStdlib())
    testRuntime(project(":kotlin-reflect"))
    testCompileOnly(project(":kotlin-main-kts"))
    testCompileOnly(project(":kotlin-scripting-jvm-host"))
    testRuntimeOnly(projectRuntimeJar(":kotlin-main-kts"))
    testRuntimeOnly(project(":kotlin-scripting-jvm-host-embeddable"))
}

sourceSets {
    "main" { }
    "test" { projectDefault() }
}

projectTest(parallel = true)
