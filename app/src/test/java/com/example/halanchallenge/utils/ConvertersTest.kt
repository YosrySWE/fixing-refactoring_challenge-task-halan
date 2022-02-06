package com.example.halanchallenge.utils


import org.junit.Test
import com.google.common.truth.Truth.*
class ConvertersTest {

    @Test
    fun listToJson() {
        val list = listOf("item1", "item2","item3", "item4","item5", "item6","item7", "item8")
        val str = Converters().listToJson(list)
        assertThat(str).isInstanceOf(String::class.java)
        assertThat(str).isNotEmpty()
        assertThat(str).isNotNull()
        assertThat(str).contains("item1")
    }

    @Test
    fun jsonToList() {
        val str = "[\"item1\",\"item2\",\"item3\",\"item4\",\"item5\",\"item6\",\"item7\",\"item8\"]"
        val list = Converters().jsonToList(str)
        assertThat(list).isNotEmpty()
        assertThat(list).hasSize(8)
        assertThat(list[0]).isEqualTo("item1")
    }
}