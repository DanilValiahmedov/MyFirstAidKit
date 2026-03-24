package com.valimade.myfirstaidkit.medicine.domain.model

data class Medicine (
    val name: String, //Наименование лекарства
    val verificationName: String, //Название без пробелов и большом регистре (для поиска)
    val symptoms: List<String>, //Список симптомов (для показа)
    val symptomsVerification: List<String>, //Список симптомов без пробелов и большом регистре (для поиска)
    val diseases: List<String>, //Список болезней для которого походит лекарство (для показа)
    val diseasesVerification: List<String>, //Список болезней для которого походит лекарство без пробелов и большом регистре (для поиска)
    val form: List<String>, //Форма лекарства (для показа)
    val formVerification: List<String>, //Форма лекарства без пробелов и большом регистре (для поиска)
    val forWhom: List<String>, //Для кого лекарство (для показа)
    val forWhomVerification: List<String>, //Для кого лекарство без пробелов и большом регистре (для поиска)
    val expirationDate: Int, //Дата срока годности
    val comment: String, //Комментарий от пользователя
)
