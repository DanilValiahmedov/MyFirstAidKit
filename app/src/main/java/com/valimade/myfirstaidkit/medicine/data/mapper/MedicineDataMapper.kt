package com.valimade.myfirstaidkit.medicine.data.mapper

import com.valimade.myfirstaidkit.medicine.data.db.entities.MedicineData
import com.valimade.myfirstaidkit.medicine.domain.model.Medicine
import kotlin.collections.joinToString

const val SEPARATOR = "\u001f"

object MedicineDataMapper {
    fun fromDataToDomain(medicine: MedicineData): Medicine {
        return Medicine(
            id = medicine.id,
            name = medicine.name,
            verificationName = medicine.verificationName,
            symptoms = medicine.symptoms.split(SEPARATOR),
            symptomsVerification = medicine.symptomsVerification.split(SEPARATOR),
            diseases = medicine.diseases.split(SEPARATOR),
            diseasesVerification = medicine.diseasesVerification.split(SEPARATOR),
            forms = medicine.forms.split(SEPARATOR),
            formsVerification = medicine.formsVerification.split(SEPARATOR),
            forWhoms = medicine.forWhoms.split(SEPARATOR),
            forWhomsVerification = medicine.forWhomsVerification.split(SEPARATOR),
            locations = medicine.locations.split(SEPARATOR),
            locationsVerification = medicine.locationsVerification.split(SEPARATOR),
            expirationDate = medicine.expirationDate,
            comment = medicine.comment,
        )
    }

    fun fromDomainToData(medicine: Medicine): MedicineData {
        return MedicineData(
            id = medicine.id,
            name = medicine.name,
            verificationName = medicine.verificationName,
            symptoms = medicine.symptoms.joinToString(SEPARATOR),
            symptomsVerification = medicine.symptomsVerification.joinToString(SEPARATOR),
            diseases = medicine.diseases.joinToString(SEPARATOR),
            diseasesVerification = medicine.diseasesVerification.joinToString(SEPARATOR),
            forms = medicine.forms.joinToString(SEPARATOR),
            formsVerification = medicine.formsVerification.joinToString(SEPARATOR),
            forWhoms = medicine.forWhoms.joinToString(SEPARATOR),
            forWhomsVerification = medicine.forWhomsVerification.joinToString(SEPARATOR),
            locations = medicine.locations.joinToString(SEPARATOR),
            locationsVerification = medicine.locationsVerification.joinToString(SEPARATOR),
            expirationDate = medicine.expirationDate,
            comment = medicine.comment,
        )
    }
}