package com.valimade.myfirstaidkit.data.mapper

import com.valimade.myfirstaidkit.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.domain.model.Medicine
import kotlin.collections.joinToString

const val SEPARATOR = "\u001f"
const val SEPARATOR_FOR_FTS4 = " "

object MedicineDataMapper {
    fun fromDataToDomain(medicine: MedicineData): Medicine {
        return Medicine(
            id = medicine.id,
            name = medicine.name,
            verificationName = medicine.verificationName,
            symptoms = medicine.symptoms?.split(SEPARATOR),
            symptomsVerification = medicine.symptomsVerification?.split(SEPARATOR_FOR_FTS4),
            diseases = medicine.diseases?.split(SEPARATOR),
            diseasesVerification = medicine.diseasesVerification?.split(SEPARATOR_FOR_FTS4),
            forms = medicine.forms?.split(SEPARATOR),
            formsVerification = medicine.formsVerification?.split(SEPARATOR_FOR_FTS4),
            forWhoms = medicine.forWhoms?.split(SEPARATOR),
            forWhomsVerification = medicine.forWhomsVerification?.split(SEPARATOR_FOR_FTS4),
            locations = medicine.locations?.split(SEPARATOR),
            locationsVerification = medicine.locationsVerification?.split(SEPARATOR_FOR_FTS4),
            expirationDate = medicine.expirationDate,
            comment = medicine.comment,
        )
    }

    fun fromDomainToData(medicine: Medicine): MedicineData {
        return MedicineData(
            name = medicine.name,
            verificationName = medicine.verificationName,
            symptoms = medicine.symptoms?.joinToString(SEPARATOR),
            symptomsVerification = medicine.symptomsVerification?.joinToString(SEPARATOR_FOR_FTS4),
            diseases = medicine.diseases?.joinToString(SEPARATOR),
            diseasesVerification = medicine.diseasesVerification?.joinToString(SEPARATOR_FOR_FTS4),
            forms = medicine.forms?.joinToString(SEPARATOR),
            formsVerification = medicine.formsVerification?.joinToString(SEPARATOR_FOR_FTS4),
            forWhoms = medicine.forWhoms?.joinToString(SEPARATOR),
            forWhomsVerification = medicine.forWhomsVerification?.joinToString(SEPARATOR_FOR_FTS4),
            locations = medicine.locations?.joinToString(SEPARATOR),
            locationsVerification = medicine.locationsVerification?.joinToString(SEPARATOR_FOR_FTS4),
            expirationDate = medicine.expirationDate,
            comment = medicine.comment,
        )
    }
}