// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

// FILE: test.kt
import kotlinx.serialization.*

open class NonSerializableParent(val arg: Int)

<!PLUGIN_ERROR!>@Serializable<!>
class Derived(val someData: String): NonSerializableParent(42)
