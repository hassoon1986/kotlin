// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

// FILE: test.kt
import kotlinx.serialization.*

<!PLUGIN_ERROR!>@Serializable<!>
interface INonSerializable

<!PLUGIN_ERROR!>@Serializable<!>
enum class ENonSerializable { A, B }
