package br.cericatto.easynvest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.cericatto.easynvest.network.EasyApi
import br.cericatto.easynvest.network.EasyProperty
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        EasyApi.retrofitService.getTestProperties().enqueue(object: Callback<EasyProperty> {
            override fun onFailure(call: Call<EasyProperty>, t: Throwable) {
                retrofit_result.text = getString(R.string.retrofit_error)
            }
            override fun onResponse(call: Call<EasyProperty>, response: Response<EasyProperty>) {
                if (response.isSuccessful) {
                    val result = response.body()?.investmentParameter?.maturityDate
                    retrofit_result.text = result
                } else {
                    retrofit_result.text = getString(R.string.retrofit_error)
                }
            }
        })
    }
}
