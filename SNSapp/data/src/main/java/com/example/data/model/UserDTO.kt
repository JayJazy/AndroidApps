package com.example.data.model

import com.example.domain.model.User
import kotlinx.serialization.Serializable


@Serializable
data class UserDTO(
    val id: Long,
    val loginId: String,
    val userName: String,
    val extraUserInfo: String,
    val profileFilePath: String
)

fun UserDTO.toDomainModel() : User {
    return User(
        id = id,
        loginId = loginId,
        userName = userName,
        profileImageUrl = profileFilePath
    )
}