package com.example.carinfo.api

data class CarInfoResponse(
    val success: Boolean,
    val result: Result
)

data class Result(
    val records: List<CarInfoRecord>
)

data class CarInfoRecord(
    val mispar_rechev: String?, // Car Number
    val degem_nm: String?, // Model Name
    val tozeret_nm: String?, // Manufacturer
    val shnat_yitzur: String?, // Year of Manufacture
    val sug_delek_nm: String?, // Fuel Type
    val tzeva_rechev: String?, // Vehicle Color
    val degem_manoa: String?, // Engine Model
    val mivchan_acharon_dt: String?, // Last Inspection Date
    val tokef_dt: String?, // Expiration Date
    val baalut: String?, // Ownership
    val misgeret: String?, // Chassis Number
    val zmig_kidmi: String?, // Front Tire
    val zmig_ahori: String?, // Rear Tire
    val kvutzat_zihum: String?, // Pollution Group
    val ramat_eivzur_betihuty: String?, // Safety Rating
    val kinuy_mishari: String?, // Usage Name
    val rank: String? // Rank
)
