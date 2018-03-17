package com.app.bickup_user.payment



import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.app.bickup_user.R
import com.app.bickup_user.retrofit.APIService
import com.app.bickup_user.retrofit.ApiUtils
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {


    companion object {

        private val TAG = PaymentActivity::class.java.simpleName
        private val PATH_TO_SERVER = "PATH_TO_SERVER"
        private val BRAINTREE_REQUEST_CODE = 4949
    }

    private var clientToken: String? = null
    private var mAPIService: APIService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        mAPIService = ApiUtils.getAPIService()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
       // getClientTokenFromServer()
        try {
            initJsonParese()
        }catch (e:Exception){}
            val buyNowButton = findViewById<View>(R.id.buy_now) as Button
        buyNowButton.setOnClickListener {
            try {
            onBraintreeSubmit()
        }catch (e:Exception){}
        }
    }

    private fun onBraintreeSubmit() {
        val dropInRequest = DropInRequest().clientToken(clientToken)
        try {
            startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE)
        }catch (e:Exception){}
    }


    private fun initJsonParese() {
        val message = arrayOfNulls<String>(1)
       // circularProgressView.setVisibility(View.VISIBLE)
        val accesstoken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6Ijk1OTk4MzQ3MzUiLCJpYXQiOjE1MjEyODAzODksImV4cCI6MTUyMTI4Mzk4OX0.RwuUnNWdXTFTK5JgWzmf_ABBEpUvHfurZff9dWhsYZg"
        mAPIService!!.PostRideGetToken(accesstoken,"").enqueue(object : Callback<PaymentModel> {
            override fun onResponse(call: Call<PaymentModel>, response: Response<PaymentModel>) {
               // circularProgressView.setVisibility(View.GONE)
                val status = response.code()
                if (response.isSuccessful) {
                    clientToken = response.body()!!.response.clientToken
                    Log.d(TAG, "Client token: " + clientToken)
                }
                if (status != 200) {
                    when (status) {
                        422 -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                        400 -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                        500 -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                        201 -> Toast.makeText(this@PaymentActivity, resources.getString(R.string.txt_driver_201), Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<PaymentModel>, t: Throwable) {
              //  circularProgressView.setVisibility(View.GONE)
                Toast.makeText(this@PaymentActivity, resources.getString(R.string.txt_Netork_error), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Unable to submit post to API.")
                return
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == BRAINTREE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val result = data.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT)
                    val paymentNonce = result.paymentMethodNonce!!.nonce
                    //send to your server
                    Log.d(TAG, "Testing the app here :")
                    sendPaymentNonceToServer(paymentNonce)
                }
                Activity.RESULT_CANCELED -> Log.d(TAG, "User cancelled payment ")
                else -> {
                    val error = data.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                    Log.d(TAG, " error exception  ")
                }
            }
        }
    }

    private fun sendPaymentNonceToServer(paymentNonce: String) {
       /* val message = arrayOfNulls<String>(1)
        // circularProgressView.setVisibility(View.VISIBLE)
        val accesstoken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6Ijk1OTk4MzQ3MzUiLCJpYXQiOjE1MjEyODAzODksImV4cCI6MTUyMTI4Mzk4OX0.RwuUnNWdXTFTK5JgWzmf_ABBEpUvHfurZff9dWhsYZg"
        mAPIService!!.PostRideGetToken(accesstoken,"").enqueue(object : Callback<PaymentModel> {
            override fun onResponse(call: Call<PaymentModel>, response: Response<PaymentModel>) {
                // circularProgressView.setVisibility(View.GONE)
                val status = response.code()
                if (response.isSuccessful) {
                    clientToken = response.body()!!.response.clientToken
                    Log.d(TAG, "Client token: " + clientToken)
                }
                if (status != 200) {
                    when (status) {
                        422 -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                        400 -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                        500 -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                        201 -> Toast.makeText(this@PaymentActivity, resources.getString(R.string.txt_driver_201), Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@PaymentActivity, message[0], Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<PaymentModel>, t: Throwable) {
                //  circularProgressView.setVisibility(View.GONE)
                Toast.makeText(this@PaymentActivity, resources.getString(R.string.txt_Netork_error), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Unable to submit post to API.")
                return
            }
        })*/
    }


/* private fun sendPaymentNonceToServer(paymentNonce: String) {
        val params = RequestParams("NONCE", paymentNonce)
        val androidClient = AsyncHttpClient()
        androidClient.post(PATH_TO_SERVER, params, object : TextHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseString: String, throwable: Throwable) {
                Log.d(TAG, "Error: Failed to create a transaction")
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseString: String) {
                Log.d(TAG, "Output " + responseString)
            }
        })
    }
*/

}