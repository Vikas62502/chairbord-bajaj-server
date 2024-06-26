package com.chairboard.domain.service

import com.chairboard.dto.customer.CustomerResponse
import com.chairboard.dto.customer.CustomerRow
import com.chairboard.dto.customer.wallet.CreateWalletClientRequest
import com.chairboard.dto.customer.wallet.CreateWalletResponse
import com.chairboard.plugins.Modules.customerRepository
import com.chairboard.plugins.Modules.userRepository

class CustomerService : Service {

    fun insertRow(agentId: String,  input: CreateWalletClientRequest, responseData: CreateWalletResponse?): String {
        val agent = userRepository.getById(agentId)
        val row = CustomerRow(
            session = input.sessionId,
            name = input.name,
            lastName = input.lastName,
            mobile = input.mobileNo,
            dob = input.dob,
            walletId = responseData?.custDetails?.walletId ?: "NA",
            walletStatus = responseData?.custDetails?.walletStatus ?: "NA",
            kycStatus = responseData?.custDetails?.kycStatus ?: "NA",
        )
        return customerRepository.insert(input = row, agent = agent!!)
    }


    fun getCustomerBySession(session: String): CustomerResponse? {
        return customerRepository.getCustomerBySession(session)?.toResponse()
    }

    fun getCustomerById(id: String): CustomerResponse? {
            return customerRepository.getById(id)?.toResponse()
    }


}