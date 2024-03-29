package com.app.bickup_user.retrofit;

import com.app.bickup_user.controller.WebAPIManager;
import com.app.bickup_user.payment.PaymentModel;
import com.app.bickup_user.retrofit.model.OnGoing;
import com.app.bickup_user.utility.ConstantValues;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.app.bickup_user.controller.WebAPIManager.get_url_RideGetToken;
import static com.app.bickup_user.controller.WebAPIManager.get_url_historyRide;
import static com.app.bickup_user.controller.WebAPIManager.get_url_ongoingRide;
import static com.app.bickup_user.controller.WebAPIManager.get_url_scheduledRide;
import static com.app.bickup_user.controller.WebAPIManager.get_url_statusUpdated;

public interface APIService {

    //https://code.tutsplus.com/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845

    @GET(get_url_ongoingRide)
    Call<OnGoing> getOngoingRide(@Header(ConstantValues.USER_ACCESS_TOKEN) String accessToken);

    @GET(get_url_historyRide)
    Call<OnGoing> getHistoryRide(@Header(ConstantValues.USER_ACCESS_TOKEN) String accessToken);

    @GET(get_url_scheduledRide)
    Call<OnGoing> getSchedul(@Header(ConstantValues.USER_ACCESS_TOKEN) String accessToken);

    //Status updated
    @GET(get_url_statusUpdated)
    Call<OnGoing> getStatusUpdated(@Header(ConstantValues.USER_ACCESS_TOKEN) String accessToken,
                                     @Path("ride_id") String rideId);


    @POST(get_url_RideGetToken)
    @FormUrlEncoded
    Call<PaymentModel> PostRideGetToken(
            @Header(ConstantValues.USER_ACCESS_TOKEN) String accessToken,
            @Field("bickup") String bickup);


   /* @POST(WebAPIManager.get_url_rateDriver)
    @FormUrlEncoded
    Call<OnGoing> PostDriverRating(@Header(ConstantValues.USER_ACCESS_TOKEN) String accessToken,
                                      @Field("ride_id") String ride_id,
                                      @Field("driver_id") String driver_id,
                                      @Field("rating") String rating,
                                      @Field("comment") String comment);

*/



}