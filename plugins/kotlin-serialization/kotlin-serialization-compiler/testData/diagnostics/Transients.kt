// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

// FILE: test.kt
import kotlinx.serialization.*

@Serializable
data class WithTransients(<!PLUGIN_ERROR!>@Transient val missing: Int<!>) {
    <!PLUGIN_WARNING!>@Transient val redundant: Int get() = 42<!>
}
