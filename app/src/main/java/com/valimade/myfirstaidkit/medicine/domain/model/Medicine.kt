package com.valimade.myfirstaidkit.medicine.domain.model

data class Medicine (
    val id: Int? = null,
    val name: String, //Наименование лекарства
    val verificationName: String, //Название без пробелов и большом регистре (для поиска)
    val symptoms: List<String>? = null, //Список симптомов (для показа)
    val symptomsVerification: List<String>? = null, //Список симптомов без пробелов и большом регистре (для поиска)
    val diseases: List<String>? = null, //Список болезней для которого походит лекарство (для показа)
    val diseasesVerification: List<String>? = null, //Список болезней для которого походит лекарство без пробелов и большом регистре (для поиска)
    val forms: List<String>? = null, //Форма лекарства (для показа)
    val formsVerification: List<String>? = null, //Форма лекарства без пробелов и большом регистре (для поиска)
    val forWhoms: List<String>? = null, //Для кого лекарство (для показа)
    val forWhomsVerification: List<String>? = null, //Для кого лекарство без пробелов и большом регистре (для поиска)
    val locations: List<String>? = null, //Где хранится лекарство (для показа)
    val locationsVerification: List<String>? = null, //Где хранится лекарство без пробелов и большом регистре (для поиска)
    val expirationDate: Int? = null, //Дата срока годности
    val comment: String? = null, //Комментарий от пользователя
)
