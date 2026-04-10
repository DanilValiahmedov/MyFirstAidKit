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
            symptoms = medicine.symptoms?.split(SEPARATOR) ?: emptyList(),
            symptomsVerification = medicine.symptomsVerification?.split(SEPARATOR_FOR_FTS4) ?: emptyList(),
            diseases = medicine.diseases?.split(SEPARATOR)  ?: emptyList(),
            diseasesVerification = medicine.diseasesVerification?.split(SEPARATOR_FOR_FTS4) ?: emptyList(),
            forms = medicine.forms?.split(SEPARATOR)  ?: emptyList(),
            formsVerification = medicine.formsVerification?.split(SEPARATOR_FOR_FTS4) ?: emptyList(),
            whoms = medicine.whoms?.split(SEPARATOR) ?: emptyList(),
            whomsVerification = medicine.whomsVerification?.split(SEPARATOR_FOR_FTS4) ?: emptyList(),
            locations = medicine.locations?.split(SEPARATOR) ?: emptyList(),
            locationsVerification = medicine.locationsVerification?.split(SEPARATOR_FOR_FTS4)?: emptyList(),
            expirationDateVisually = medicine.expirationDateVisually,
            expirationDate = medicine.expirationDate,
            comment = medicine.comment,
        )
    }

    fun fromDomainToData(medicine: Medicine): MedicineData {
        return MedicineData(
            name = medicine.name,
            verificationName = medicine.verificationName,
            symptoms = medicine.symptoms.joinToStringOrNull(SEPARATOR),
            symptomsVerification = medicine.symptomsVerification.joinToStringOrNull(SEPARATOR_FOR_FTS4),
            diseases = medicine.diseases.joinToStringOrNull(SEPARATOR),
            diseasesVerification = medicine.diseasesVerification.joinToStringOrNull(SEPARATOR_FOR_FTS4),
            forms = medicine.forms.joinToStringOrNull(SEPARATOR),
            formsVerification = medicine.formsVerification.joinToStringOrNull(SEPARATOR_FOR_FTS4),
            whoms = medicine.whoms.joinToStringOrNull(SEPARATOR),
            whomsVerification = medicine.whomsVerification.joinToStringOrNull(SEPARATOR_FOR_FTS4),
            locations = medicine.locations.joinToStringOrNull(SEPARATOR),
            locationsVerification = medicine.locationsVerification.joinToStringOrNull(SEPARATOR_FOR_FTS4),
            expirationDateVisually = medicine.expirationDateVisually,
            expirationDate = medicine.expirationDate,
            comment = medicine.comment,
        )
    }
    private fun List<String>.joinToStringOrNull(separator: String): String? {
        return if(isEmpty()) null
            else joinToString(separator)
    }
}