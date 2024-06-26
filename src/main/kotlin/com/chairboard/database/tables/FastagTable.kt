package com.chairboard.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object FastagTable : IdTable<String>(name = "fastags") {
    override val id: Column<EntityID<String>> = varchar("id", 128).entityId()
    val request_id = varchar(name = "request_id", length = 128)
    val session = varchar(name = "session", length = 128)
    val user = reference("user", UsersTable)
    val vehicle_no = varchar(name = "vehicle_no", length = 20)
    val tag_sr_no = varchar(name = "tag_sr_no", length = 56).nullable().default(null)
    val status = varchar(name = "status", length = 56).nullable().default(null)
    val chassis = varchar(name = "chassis", length = 56).nullable().default(null)
    val engine = varchar(name = "engine", length = 56).nullable().default(null)
    val vehicleManuf = varchar(name = "vehicle_manuf", length = 56).nullable().default(null)
    val model = varchar(name = "model", length = 56).nullable().default(null)
    val vehicleColour = varchar(name = "vehicle_colour", length = 56).nullable().default(null)
    val type = varchar(name = "type", length = 56).nullable().default(null)
    val npciStatus = varchar(name = "npci_status", length = 56).nullable().default(null)
    val isCommercial = bool(name = "is_commercial").nullable().default(null)
    val tagVehicleClassId = varchar(name = "tag_vehicle_class_id", length = 56).nullable().default(null)
    val npciVehicleClassId = varchar(name = "npci_vehicle_class_id", length = 56).nullable().default(null)
    val vehicleType = varchar(name = "vehicle_type", length = 56).nullable().default(null)
    val rechargeAmount = double(name = "recharge_amount").nullable().default(null)
    val securityDeposit = double(name = "security_deposit").nullable().default(null)
    val tagCost = double(name = "tag_cost").nullable().default(null)
    val debitAmt = double(name = "debit_amt").nullable().default(null)
    val name = varchar(name = "name", length = 56).nullable().default(null)
    val mobileNo = varchar(name = "mobile_no", length = 56).nullable().default(null)
    val walletId = varchar(name = "wallet_id", length = 56).nullable().default(null)
    val tid = varchar(name = "tid", length = 56).nullable().default(null)
    val rcImageFront = largeText(name = "rc_image_front").nullable().default(null)
    val rcImageBack = largeText(name = "rc_image_back").nullable().default(null)
    val vehicleImage = largeText(name = "vehicle_image").nullable().default(null)
    val udf1 = varchar(name = "udf1", length = 56).nullable().default(null)
    val udf2 = varchar(name = "udf2", length = 56).nullable().default(null)
    val udf3 = varchar(name = "udf3", length = 56).nullable().default(null)
    val udf4 = varchar(name = "udf4", length = 56).nullable().default(null)
    val udf5 = varchar(name = "udf5", length = 56).nullable().default(null)
    val customer = optReference("customer", CustomersTable)
    val recordType = varchar(name = "record_type", length = 56).nullable().default(null)
    val created_at = datetime(name = "created_at").defaultExpression(CurrentDateTime)
}