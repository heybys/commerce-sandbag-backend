package org.example.app

import kotlin.test.Test
import kotlin.test.assertEquals

class MessageUtilsTest {
    @Test fun testGetMessage() {
        assertEquals("Hello World!", MessageUtils.getMessage())
    }
}
