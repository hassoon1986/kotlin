// !DIAGNOSTICS: -UNUSED_PARAMETER,-UNUSED_VARIABLE

// FILE: test.kt
import kotlinx.serialization.*

class NonSerializable

@Serializable
class Basic(val foo: <!PLUGIN_ERROR!>NonSerializable<!>)

@Serializable
class Inside(val foo: List<<!PLUGIN_ERROR!>NonSerializable<!>>)

@Serializable
class WithImplicitType {
    <!PLUGIN_ERROR!>val foo = NonSerializable()<!>
}