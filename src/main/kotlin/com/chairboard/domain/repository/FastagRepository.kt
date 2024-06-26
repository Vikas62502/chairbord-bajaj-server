package com.chairboard.domain.repository

import com.chairboard.database.entity.Customer
import com.chairboard.database.entity.Fastag
import com.chairboard.database.entity.User
import com.chairboard.database.tables.FastagTable
import com.chairboard.dto.customer.fastag.FastagRow
import com.chairboard.dto.customer.fastag.FastagRowListItem
import com.chairboard.plugins.Modules.customerRepository
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class FastagRepository : Repository {

    fun insert(sessionId: String, input: FastagRow, agent: User): Fastag {
        return transaction {
            val cust = customerRepository.getCustomerBySession(sessionId)
            return@transaction Fastag.new(UUID.randomUUID().toString()) {
                name = input.name
                session = input.session
                request_id = input.request_id
                user = agent
                vehicle_no = input.vehicle_no
                tag_sr_no = input.tag_sr_no
                status = input.status
                chassis = input.chassis
                engine = input.engine
                vehicleManuf = input.vehicleManuf
                model = input.model
                vehicleColour = input.vehicleColour
                type = input.type
                npciStatus = input.npciStatus
                isCommercial = input.isCommercial
                tagVehicleClassId = input.tagVehicleClassId
                npciVehicleClassId = input.npciVehicleClassId
                vehicleType = input.vehicleType
                rechargeAmount = input.rechargeAmount
                securityDeposit = input.securityDeposit
                tagCost = input.tagCost
                debitAmt = input.debitAmt
                name = input.name
                mobileNo = input.mobileNo
                walletId = input.walletId
                tid = input.tid
                rcImageFront = input.rcImageFront
                rcImageBack = input.rcImageBack
                vehicleImage = input.vehicleImage
                udf1 = input.udf1
                udf2 = input.udf2
                udf3 = input.udf3
                udf4 = input.udf4
                udf5 = input.udf5
                customer =  cust
                recordType = input.recordType
            }
        }
    }

    fun updateStatus(fastTagId:String, status: String) {
        transaction {
            Fastag.findById(fastTagId).let {
                it?.status = status
            }
        }
    }

    fun getAllFastagsForAgent(agentId: String, status: String): List<FastagRowListItem> {
        if (status == "ALL" || status.isBlank()) {
            return transaction {
                return@transaction Fastag.find { FastagTable.user eq agentId }.orderBy(FastagTable.created_at to SortOrder.DESC).map {
                    it.toFastagListRow()
                }
            }
        } else {
            return transaction {
                return@transaction Fastag.find { FastagTable.user eq agentId and(FastagTable.status eq status)}.orderBy(FastagTable.created_at to SortOrder.DESC).map {
                    it.toFastagListRow()
                }
            }
        }
    }

    fun getAllFastags(status: String): List<FastagRowListItem> {
        if (status == "ALL" || status.isBlank()) {
            return transaction {
                return@transaction Fastag.all().orderBy(FastagTable.created_at to SortOrder.DESC).map {
                    it.toFastagListRow()
                }
            }
        } else {
            return transaction {
                return@transaction Fastag.find { (FastagTable.status eq status)}.orderBy(FastagTable.created_at to SortOrder.DESC).map {
                    it.toFastagListRow(ignoreNestedAgent = true)
                }
            }
        }
    }

    fun getFastagDetail(id: String) : FastagRow? {
        return transaction {
            return@transaction Fastag.findById(id)?.toFastagRow(ignoreNestedAgent = true)
        }
    }
}