package com.valimade.myfirstaidkit.data.assets

import com.valimade.myfirstaidkit.data.db.entities.Disease
import com.valimade.myfirstaidkit.data.db.entities.Form
import com.valimade.myfirstaidkit.data.db.entities.Location
import com.valimade.myfirstaidkit.data.db.entities.Symptom
import com.valimade.myfirstaidkit.data.db.entities.Whom
import com.valimade.myfirstaidkit.domain.utils.StringNormalizer

class InitialData(
    private val normalizer: StringNormalizer
) {
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
            verificationName = normalizer.normalizeVerificationName(it)
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
            verificationName = normalizer.normalizeVerificationName(it)
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
            verificationName = normalizer.normalizeVerificationName(it)
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
            verificationName = normalizer.normalizeVerificationName(it)
        )
    }

    private val locationsName = listOf(
        "Дом",
        "Машина",
        "Дача"
    )

    val locations = locationsName.map {
        Location(
            name = it,
            verificationName = normalizer.normalizeVerificationName(it)
        )
    }
}