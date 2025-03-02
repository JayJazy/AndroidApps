package com.example.domain.usecase.main.setting

interface UpdateUserNameUseCase  {

    suspend operator fun invoke(userName: String) : Result<Unit>
}