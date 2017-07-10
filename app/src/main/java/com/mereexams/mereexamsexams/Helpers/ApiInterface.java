package com.mereexams.mereexamsexams.Helpers;

import com.mereexams.mereexamsexams.Models.ExamCompressed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * Created by Bilal on 10-Jul-17.
 */

public interface ApiInterface {

    // Api endpoint to get exam list
    @GET
    Call<ExamCompressed.Response> getExamList(
            @Url /* Your api endpoint URL */ String url,
            @Header(GlobalVars.API_ARG_TOKEN) String token
    );
}
