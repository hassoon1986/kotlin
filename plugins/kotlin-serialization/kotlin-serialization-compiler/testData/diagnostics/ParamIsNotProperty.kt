// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

// FILE: test.kt
import kotlinx.serialization.*

<!PLUGIN_ERROR!>@Serializable<!>
class Test(val someData: String, cantBeDeserialized: Int)
