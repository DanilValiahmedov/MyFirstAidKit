package com.valimade.myfirstaidkit.data.mapper

import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom
import com.valimade.myfirstaidkit.domain.model.Characteristic
import com.valimade.myfirstaidkit.domain.model.CharacteristicMedicine

object CharacteristicDataMapper {
    fun symptomFromDataToDomain(symptom: Symptom): CharacteristicMedicine {
        return CharacteristicMedicine(
            id = symptom.id,
            characteristic = Characteristic.SYMPTOM,
            name = symptom.name,
            verificationName = symptom.verificationName
        )
    }

    fun diseaseFromDataToDomain(disease: Disease): CharacteristicMedicine {
        return CharacteristicMedicine(
            id = disease.id,
            characteristic = Characteristic.DISEASES,
            name = disease.name,
            verificationName = disease.verificationName
        )
    }

    fun formFromDataToDomain(form: Form): CharacteristicMedicine {
        return CharacteristicMedicine(
            id = form.id,
            characteristic = Characteristic.FORM,
            name = form.name,
            verificationName = form.verificationName
        )
    }

    fun whomFromDataToDomain(whom: Whom): CharacteristicMedicine {
        return CharacteristicMedicine(
            id = whom.id,
            characteristic = Characteristic.WHOM,
            name = whom.name,
            verificationName = whom.verificationName
        )
    }

    fun locationFromDataToDomain(location: Location): CharacteristicMedicine {
        return CharacteristicMedicine(
            id = location.id,
            characteristic = Characteristic.LOCATION,
            name = location.name,
            verificationName = location.verificationName
        )
    }

    fun symptomFromDomainToData(characteristic: CharacteristicMedicine): Symptom {
        return Symptom(
            id = characteristic.id,
            name = characteristic.name,
            verificationName = characteristic.verificationName
        )
    }

    fun diseaseFromDomainToData(characteristic: CharacteristicMedicine): Disease {
        return Disease(
            id = characteristic.id,
            name = characteristic.name,
            verificationName = characteristic.verificationName
        )
    }

    fun formFromDomainToData(characteristic: CharacteristicMedicine): Form {
        return Form(
            id = characteristic.id,
            name = characteristic.name,
            verificationName = characteristic.verificationName
        )
    }

    fun whomFromDomainToData(characteristic: CharacteristicMedicine): Whom {
        return Whom(
            id = characteristic.id,
            name = characteristic.name,
            verificationName = characteristic.verificationName
        )
    }

    fun locationFromDomainToData(characteristic: CharacteristicMedicine): Location {
        return Location(
            id = characteristic.id,
            name = characteristic.name,
            verificationName = characteristic.verificationName
        )
    }
}