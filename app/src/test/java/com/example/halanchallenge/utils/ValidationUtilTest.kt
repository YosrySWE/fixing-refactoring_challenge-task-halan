package com.example.halanchallenge.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidationUtilTest{

    @Test
    fun `validate if username is empty return false`(){
        val value = ValidationUtil.validateLogin(username = "", password = "hfajhfjashjfh")
        assertThat(value.result).isFalse()
    }

    @Test
    fun `validate if password is empty return false`(){
        val value = ValidationUtil.validateLogin(username = "", password = "P@SSW0rd")
        assertThat(value.result).isFalse()
    }

    @Test
    fun `username is bigger than 15 letters return false`(){
        val value = ValidationUtil.validateLogin(username = "testUsernameIsBiggerThan15", password = "P@SSW0rd")
        assertThat(value.result).isFalse()
    }

    @Test
    fun `username is less than 6 letters return false`(){
        val value = ValidationUtil.validateLogin(username = "test", password = "P@SSW0rd")
        assertThat(value.result).isFalse()
    }

    @Test
    fun `password is less than 8 letters return false`(){
        val value = ValidationUtil.validateLogin(username = "testUsername", password = "P@SSW0")
        assertThat(value.result).isFalse()
    }

    @Test
    fun `username must be only alphanumeric return false`(){
        val value = ValidationUtil.validateLogin(username = "UserName@test.ar", password = "hfajhfjashjfh")
        assertThat(value.result).isFalse()
    }
}