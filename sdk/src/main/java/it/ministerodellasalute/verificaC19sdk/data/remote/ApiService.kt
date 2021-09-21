/*
 *  ---license-start
 *  eu-digital-green-certificates / dgca-verifier-app-android
 *  ---
 *  Copyright (C) 2021 T-Systems International GmbH and all other contributors
 *  ---
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ---license-end
 *
 *  Created by mykhailo.nester on 4/24/21 2:50 PM
 */

package it.ministerodellasalute.verificaC19sdk.data.remote

import it.ministerodellasalute.verificaC19sdk.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("signercertificate/update")
    suspend fun getCertUpdate(
        @Header("x-resume-token") contentRange: String
    ): Response<ResponseBody>

    @GET("signercertificate/status")
    suspend fun getCertStatus(): Response<List<String>>

    @GET("settings")
    suspend fun getValidationRules(): Response<ResponseBody>

    //@GET("getstatus")
    @GET(BuildConfig.BASE_URL_CRL) //TODO add the correct value
    //(@Url String url);
    suspend fun getCRLStatus(@Query("version") version: Long?): Response<ResponseBody>

}

