package com.meteorhead.leave.screens.bestleaves.list

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by wierzchanowskig on 01.03.2017.
 */
class LeaveListAdapterTest{
    @Test
    fun test() {
        var x = "x"
        var y: String? = null
        var z = y ?: x

        Assert.assertEquals(z, x)
    }

    @Test
    fun test2() {
        var x = "x"
        var y: String? = "y"
        var z = y ?: x

        Assert.assertEquals(z, y)
    }
}