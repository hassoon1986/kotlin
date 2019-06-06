// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

// FILE: test.kt
import kotlinx.serialization.*

@Serializable
open class Parent(open val arg: Int)

<!PLUGIN_ERROR!>@Serializable<!>
class Derived(override val arg: Int): Parent(arg)
