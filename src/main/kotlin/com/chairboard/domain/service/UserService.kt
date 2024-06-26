package com.chairboard.domain.service

import com.chairboard.domain.exceptions.UserAlreadyExistException
import com.chairboard.domain.repository.UserRepository
import com.chairboard.dto.ChangePassRequest
import com.chairboard.dto.ToggleActiveStatus
import com.chairboard.dto.UserResponse
import com.chairboard.dto.UserRow
import com.chairboard.utils.Cipher
import java.util.*

class UserService(private val userRepository: UserRepository) : Service {
    private val base64Encoder = Base64.getEncoder()

    fun changePass(changePassRequest: ChangePassRequest): Boolean {
        val encPass = String(
            base64Encoder.encode(
                Cipher.encrypt(changePassRequest.password)
            )
        )
        return userRepository.changePass(changePassRequest.userId, encPass)
    }


    fun toggleActiveStatus(toggleActiveStatus: ToggleActiveStatus): Boolean {
        return userRepository.toggleActive(toggleActiveStatus.userId, toggleActiveStatus.active > 0)
    }

    fun getAllAgents() = userRepository.getAll()
}