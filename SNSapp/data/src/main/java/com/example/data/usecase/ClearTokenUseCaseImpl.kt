package com.example.data.usecase

import com.example.data.UserDataStore
import com.example.domain.usecase.login.ClearTokenUseCase
import javax.inject.Inject

class ClearTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : ClearTokenUseCase {

    override suspend fun invoke() {
        userDataStore.clearToken()
    }
}