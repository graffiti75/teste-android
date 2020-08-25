package br.cericatto.easynvest

import br.cericatto.easynvest.utils.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DatesUnitTest {
    /**
     * String.twoDecimals() : String
     */

    @Test
    fun twoDecimalsTestEqualValues() {
        val entry = "1"
        val expected = "01"
        val tested = entry.twoDecimals()
        assertEquals(expected, tested)
    }

    @Test
    fun twoDecimalsTestDifferentValues() {
        val entry = "1"
        val expected = "10"
        val tested = entry.twoDecimals()
        assertNotEquals(expected, tested)
    }

    /**
     * String.formatMaturityDate() : String
     */

    @Test
    fun formatMaturityDateTestEqualValues() {
        val entry = "03/03/2023"
        val expected = "2023-03-03"
        val tested = entry.formatMaturityDate()
        assertEquals(expected, tested)
    }

    @Test
    fun formatMaturityDateTestDifferentValues() {
        val entry = "03-03-2023"
        val expected = "2023-03-03"
        val tested = entry.formatMaturityDate()
        assertEquals(expected, tested)
    }

    /**
     * Long.dateIsInFuture() : Boolean
     */

    @Test
    fun dateIsInFutureReturnFalse() {
        val current = (Date(System.currentTimeMillis())).time
        val entry = current.plus(10L)
        val expected = false
        val tested = entry.dateIsInFuture()
        assertEquals(expected, tested)
    }

    @Test
    fun dateIsInFutureReturnTrue() {
        val current = (Date(System.currentTimeMillis())).time
        val entry = current.plus(1000*60*60*24*2)
        val expected = true
        val tested = entry.dateIsInFuture()
        assertEquals(expected, tested)
    }

    /**
     * String.formatMaturityDateFromBackend() : String
     */

    @Test
    fun formatMaturityDateFromBackendCorrectValues() {
        val entry = "2023-03-03T00:00:00"
        val expected = "03/03/2023"
        val tested = entry.formatMaturityDateFromBackend()
        assertEquals(expected, tested)
    }

    @Test
    fun formatMaturityDateFromBackendDifferentValues() {
        val entry = "2023-03-03T00:00:00"
        val expected = "03-03-2023"
        val tested = entry.formatMaturityDateFromBackend()
        assertNotEquals(expected, tested)
    }

    /**
     * String.percentageToDouble() : Double
     */

    @Test
    fun percentageToDoubleCorrectValues() {
        val entry = "R$ 23.456,78"
        val expected = 23456.78
        val tested = entry.percentageToDouble()
        assertEquals(expected, tested, 0.01)
    }

    @Test
    fun percentageToDoubleDifferentValues() {
        val entry = "23.456,78%"
        val expected = 23456.70
        val tested = entry.percentageToDouble()
        assertNotEquals(expected, tested, 0.01)
    }

    /**
     * String.formatPercentage() : String
     */

    @Test
    fun formatPercentageCorrectValues() {
        val entry = "1.234.567,89"
        val expected = "1234567.89"
        val tested = entry.formatPercentage()
        assertEquals(expected, tested)
    }

    @Test
    fun formatPercentageDifferentValues() {
        val entry = "1.234.567,89"
        val expected = "1234567,89"
        val tested = entry.percentageToDouble()
        assertNotEquals(expected, tested, 0.01)
    }

    /**
     * Double.formatPercentage() : String
     */

    @Test
    fun formatPercentageDoubleCorrectValues() {
        val entry = 1234567.89
        val expected = "1.234.567,89"
        val tested = entry.formatPercentage()
        assertEquals(expected, tested)
    }

    @Test
    fun formatPercentageDoubleDifferentValues() {
        val entry = "1.234.567,89"
        val expected = "1234567,89"
        val tested: Double = entry.percentageToDouble()
        assertNotEquals(expected, tested, 0.01)
    }

    /**
     * Double.formatPercentageFromBackend() : String
     */

    @Test
    fun formatPercentageFromBackendCorrectValues() {
        val entry = 0.36
        val expected = "0,36%"
        val tested = entry.formatPercentageFromBackend()
        assertEquals(expected, tested)
    }

    @Test
    fun formatPercentageFromBackendDifferentValues() {
        val entry = 0.36
        val expected = "0,36"
        val tested = entry.formatPercentageFromBackend()
        assertNotEquals(expected, tested)
    }

    /**
     * Double.doubleToCurrency() : String
     */

    @Test
    fun doubleToCurrencyCorrectValues() {
        val entry = 234101.36
        val expected = "R$ 234.101,36"
        val tested = entry.doubleToCurrency()
        assertEquals(expected, tested)
    }

    @Test
    fun doubleToCurrencyDifferentValues() {
        val entry = 101.36
        val expected = "R$ 101.36"
        val tested = entry.doubleToCurrency()
        assertNotEquals(expected, tested)
    }

    /**
     * String.cleanCurrencyCharacters() : String
     */

    @Test
    fun cleanCurrencyCharactersCorrectValues() {
        val entry = "R$ 234.101,36"
        val expected = "234101.36"
        val tested = entry.cleanCurrencyCharacters()
        assertEquals(expected, tested)
    }

    @Test
    fun cleanCurrencyCharactersDifferentValues() {
        val entry = "R$ 234.101,36"
        val expected = "234101,36"
        val tested = entry.cleanCurrencyCharacters()
        assertNotEquals(expected, tested)
    }
}