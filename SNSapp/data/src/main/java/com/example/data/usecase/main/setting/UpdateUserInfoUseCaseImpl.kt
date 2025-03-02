package com.example.data.usecase.main.setting

import com.example.data.model.UpdateUserInfoParam
import com.example.data.retrofit.UserService
import com.example.domain.usecase.main.setting.GetUserInfoUseCase
import com.example.domain.usecase.main.setting.UpdateUserNameUseCase
import javax.inject.Inject

class UpdateUserInfoUseCaseImpl @Inject constructor(
    private val userService: UserService,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : UpdateUserNameUseCase {
    override suspend fun invoke(userName: String): Result<Unit> = runCatching {
        val userInfo = getUserInfoUseCase().getOrThrow()
        UpdateUserInfoParam(
            userName = userName,
            profileFilePath = userInfo.profileImageUrl.orEmpty(),
            extraUserInfo = ""
        )
    }


}