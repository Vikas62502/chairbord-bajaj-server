package com.chairboard.database.entity

import com.chairboard.database.tables.FastagTable
import com.chairboard.dto.customer.fastag.FastagRow
import com.chairboard.dto.customer.fastag.FastagRowListItem
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID


class Fastag(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Fastag>(FastagTable)
    var request_id by FastagTable.request_id
    var session by FastagTable.session
    var user by User referencedOn FastagTable.user
    var vehicle_no by FastagTable.vehicle_no
    var tag_sr_no by FastagTable.tag_sr_no
    var status by FastagTable.status
    var chassis by FastagTable.chassis
    var engine by FastagTable.engine
    var vehicleManuf by FastagTable.vehicleManuf
    var model by FastagTable.model
    var vehicleColour by FastagTable.vehicleColour
    var type by FastagTable.type
    var npciStatus by FastagTable.npciStatus
    var isCommercial by FastagTable.isCommercial
    var tagVehicleClassId by FastagTable.tagVehicleClassId
    var npciVehicleClassId by FastagTable.npciVehicleClassId
    var vehicleType by FastagTable.vehicleType
    var rechargeAmount by FastagTable.rechargeAmount
    var securityDeposit by FastagTable.securityDeposit
    var tagCost by FastagTable.tagCost
    var debitAmt by FastagTable.debitAmt
    var name by FastagTable.name
    var mobileNo by FastagTable.mobileNo
    var walletId by FastagTable.walletId
    var tid by FastagTable.tid
    var rcImageFront by FastagTable.rcImageFront
    var rcImageBack by FastagTable.rcImageBack
    var vehicleImage by FastagTable.vehicleImage
    var udf1 by FastagTable.udf1
    var udf2 by FastagTable.udf2
    var udf3 by FastagTable.udf3
    var udf4 by FastagTable.udf4
    var udf5 by FastagTable.udf5
    var customer by Customer optionalReferencedOn FastagTable.customer
    var recordType by FastagTable.recordType
    var created_at by FastagTable.created_at
    fun toFastagListRow(ignoreNestedAgent: Boolean = true) = FastagRowListItem(
        id = id.value,
        session = session,
        recordType = recordType ?: "",
        request_id = request_id,
        vehicle_no = vehicle_no,
        vehicleType = vehicleType ?: "",
        tagCost = tagCost ?: 0.0,
        tag_sr_no = tag_sr_no ?: "",
        debitAmt = debitAmt ?: 0.0,
        securityDeposit = securityDeposit ?: 0.0,
        rechargeAmount = rechargeAmount ?: 0.0,
        npciStatus = npciStatus ?: "",
        name = name ?: "",
        walletId = walletId ?: "",
        status = status ?: "",
        mobileNo = mobileNo ?: "",
        udf1 = udf1 ?: "",
        udf2 = udf2 ?: "",
        udf3 = udf3 ?: "",
        udf4 = udf4 ?: "",
        udf5 = udf5 ?: "",
        tid = tid ?: "",
        agent = this.user.toResponse(),
        customer = customer?.toResponse(ignoreUser = ignoreNestedAgent),
        createdAt = created_at.toString(),
    )
    fun toFastagRow(ignoreNestedAgent: Boolean = true) = FastagRow(
        id = id.value,
        recordType = recordType ?: "",
        request_id = request_id,
        vehicleImage = vehicleImage ?: "",
        vehicle_no = vehicle_no,
        vehicleColour = vehicleColour ?: "",
        vehicleManuf = vehicleManuf ?: "",
        vehicleType = vehicleType ?: "",
        tagVehicleClassId = tagVehicleClassId ?: "",
        npciVehicleClassId = npciVehicleClassId ?: "",
        tagCost = tagCost ?: 0.0,
        tag_sr_no = tag_sr_no ?: "",
        debitAmt = debitAmt ?: 0.0,
        securityDeposit = securityDeposit ?: 0.0,
        rechargeAmount = rechargeAmount ?: 0.0,
        isCommercial = isCommercial ?: false,
        npciStatus = npciStatus ?: "",
        name = name ?: "",
        model = model ?: "",
        engine = engine ?: "",
        chassis = chassis ?: "",
        type = type ?: "",
        walletId = walletId ?: "",
        status = status ?: "",
        mobileNo = mobileNo ?: "",
        rcImageFront = rcImageFront ?: "",
        rcImageBack = rcImageBack ?: "",
        udf1 = udf1 ?: "",
        udf2 = udf2 ?: "",
        udf3 = udf3 ?: "",
        udf4 = udf4 ?: "",
        udf5 = udf5 ?: "",
        tid = tid ?: "",
        agent = this.user.toResponse(),
        customer = customer?.toResponse(ignoreUser = ignoreNestedAgent),
        createdAt = created_at.toString(),
        session = session
    )
}
