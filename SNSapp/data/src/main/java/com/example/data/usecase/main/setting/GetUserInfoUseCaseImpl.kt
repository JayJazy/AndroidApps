package com.example.data.usecase.main.setting

import com.example.data.model.toDomainModel
import com.example.data.retrofit.UserService
import com.example.domain.model.User
import com.example.domain.usecase.main.setting.GetUserInfoUseCase
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userService: UserService
) : GetUserInfoUseCase {

    override suspend fun invoke(): Result<User> = runCatching{
        userService.getUserInfo().data.toDomainModel()
    }
}

