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
 *  Created by danielsp on 9/15/21, 2:28 PM
 */

package it.ministerodellasalute.verificaC19sdk.model

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import it.ministerodellasalute.verificaC19sdk.VerificaApplication
import it.ministerodellasalute.verificaC19sdk.data.VerifierRepository
import it.ministerodellasalute.verificaC19sdk.data.VerifierRepositoryImpl
import it.ministerodellasalute.verificaC19sdk.data.local.Preferences
import it.ministerodellasalute.verificaC19sdk.data.remote.model.Rule
import it.ministerodellasalute.verificaC19sdk.model.ValidationRulesEnum
import it.ministerodellasalute.verificaC19sdk.worker.LoadKeysWorker
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    verifierRepository: VerifierRepository,
    private val preferences: Preferences
) : ViewModel(){

    val fetchStatus: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        fetchStatus.addSource(verifierRepository.getCertificateFetchStatus()) {
            fetchStatus.value = it
        }
    }

    fun getResumeToken() = preferences.resumeToken
    fun getDateLastSync() = preferences.dateLastFetch

    fun getSizeSingleChunkInByte() = preferences.sizeSingleChunkInByte
    fun getLastChunk() = preferences.lastChunk //total number of chunks in a specific version
    fun getLastDownloadedChunk() = preferences.lastDownloadedChunk
    fun getnumDiAdd() = preferences.numDiAdd
    fun getNmDiDelete() = preferences.numDiDelete
    fun getauthorizedToDownload() = preferences.authorizedToDownload
    fun setauthorizedToDownload() =
        run { preferences.authorizedToDownload = 1L }
    fun getAuthResume() = preferences.authToResume
    fun setAuthResume() =
        run { preferences.authToResume = 1L }
    fun setUnAuthResume() =
        run { preferences.authToResume = 0L }

    fun getIsPendingDownload(): Boolean {
            return preferences.currentVersion != preferences.requestedVersion
        }

    /*suspend fun startSync() =
        run {
            val verifierRepository: VerifierRepository
            val res = verifierRepository.syncData(VerificaApplication.applicationContext())
        }*/

    private fun getValidationRules():Array<Rule>{
        val jsonString = preferences.validationRulesJson
        return Gson().fromJson(jsonString, Array<Rule>::class.java)?: kotlin.run { emptyArray() }
    }

    fun getAppMinVersion(): String{
        return getValidationRules().find { it.name == ValidationRulesEnum.APP_MIN_VERSION.value}?.let {
            it.value
        } ?: run {
            ""
        }
    }

}