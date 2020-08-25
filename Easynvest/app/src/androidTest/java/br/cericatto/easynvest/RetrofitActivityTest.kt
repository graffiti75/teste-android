package br.cericatto.easynvest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.cericatto.easynvest.network.MATURITY_DATE
import br.cericatto.easynvest.rules.OkHttpIdlingResourceRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RetrofitActivityTest {

    @get:Rule
    var rule = OkHttpIdlingResourceRule()

    private val mockWebServer = MockWebServer()
    private val portNumber = 8080

    /*
    private val responseBody = "{ \"investmentParameter\": { \"investedAmount\": 1000.0, " +
        "\"yearlyInterestRate\": 1.9834, \"maturityTotalDays\": 128, \"maturityBusinessDays\": 86, " +
        "\"maturityDate\": \"2020-12-31T00:00:00\", \"rate\": 123.0, \"isTaxFree\": false }, " +
        "\"grossAmount\": 1008.28, \"taxesAmount\": 1.86, \"netAmount\": 1006.42, " +
        "\"grossAmountProfit\": 8.28, \"netAmountProfit\": 6.42, \"annualGrossRateProfit\": 0.83, " +
        "\"monthlyGrossRateProfit\": 0.16, \"dailyGrossRateProfit\": 0.0000958650007495576, " +
        "\"taxesRate\": 22.5, \"rateProfit\": 1.9834, \"annualNetRateProfit\": 0.64 }"
     */
    /*
    private val responseBody = "{ \"grossAmount\": 1008.28 }"
     */
    private val responseBody = "{ \"investmentParameter\": { \"investedAmount\": 1000.0, " +
            "\"yearlyInterestRate\": 1.9834, \"maturityTotalDays\": 128, \"maturityBusinessDays\": 86, " +
            "\"maturityDate\": \"2020-12-31T00:00:00\", \"rate\": 123.0, \"isTaxFree\": false }"

    @Before
    @Throws
    fun setUp() {
        mockWebServer.start(portNumber)
        BaseUrlProvider.baseUrl = mockWebServer.url("/").toString()
        ActivityScenario.launch(RetrofitActivity::class.java)
    }

    @After
    @Throws
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldShowMaturityDateCorrectly() {
        val response = MockResponse()
            .setBody(responseBody)
            .setBodyDelay(1, TimeUnit.SECONDS)
        mockWebServer.enqueue(response)

        onView(withId(R.id.retrofit_result))
            .check(matches(withText(MATURITY_DATE)))
    }

    @Test
    fun shouldShowError() {
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(response)

        onView(withId(R.id.retrofit_result))
            .check(matches(withText("Erro ao carregar dados da API da Easynvest.")))
    }
}
