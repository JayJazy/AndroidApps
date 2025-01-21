package com.example.domain.usecase.main.setting

import com.example.domain.model.User

interface GetUserInfoUseCase  {

    suspend operator fun invoke() : Result<User>
}