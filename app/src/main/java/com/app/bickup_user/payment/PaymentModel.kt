package com.app.bickup_user.payment
import com.google.gson.annotations.SerializedName



data class  PaymentModel(
		@SerializedName("response") val response: Response,
		@SerializedName("message") val message: String,
		@SerializedName("flag") val flag: Int
)

data class Response(
		@SerializedName("clientToken") val clientToken: String
)