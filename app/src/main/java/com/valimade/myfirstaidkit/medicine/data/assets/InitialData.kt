package com.valimade.myfirstaidkit.medicine.data.assets

import com.valimade.myfirstaidkit.medicine.data.db.entities.Disease
import com.valimade.myfirstaidkit.medicine.data.db.entities.Form
import com.valimade.myfirstaidkit.medicine.data.db.entities.Symptom
import com.valimade.myfirstaidkit.medicine.data.db.entities.Whom

object InitialData {
    private val symptomsName = listOf(
        "Кашель",
        "Насморк",
        "Головная боль",
        "Температура",
        "Боль в горле",
        "Диарея",
        "Тошнота",
        "Боли в мышцах и суставах",
        "Зубная боль",
        "Аллергические реакции",
        "Профилактика простуды",
        "Укрепление иммунитета",
        "Отравление",
        "Сонливость"
    )

    val symptoms = symptomsName.map {
        Symptom(
            name = it,
            verificationName = it.uppercase().replace(" ", "")
        )
    }

    private val diseasesName = listOf(
        "Простуда",
        "ОРВИ",
        "Грипп",
        "Аллергия",
        "Похмелье",
        "Гастрит",
        "Мигрень"
    )


    val diseases = diseasesName.map {
        Disease(
            name = it,
            verificationName = it.uppercase().replace(" ", "")
        )
    }

    private val formsName = listOf(
        "Таблетки",
        "Капсулы",
        "Сироп",
        "Капли",
        "Мазь",
        "Свечи",
        "Порошок",
        "Гель",
        "Ампула"
    )

    val forms = formsName.map {
        Form(
            name = it,
            verificationName = it.uppercase().replace(" ", "")
        )
    }

    private val whomsName = listOf(
        "Дети",
        "Взрослые",
        "Мужчины",
        "Женщины"
    )

    val whoms = whomsName.map {
        Whom(
            name = it,
            verificationName = it.uppercase().replace(" ", "")
        )
    }
}