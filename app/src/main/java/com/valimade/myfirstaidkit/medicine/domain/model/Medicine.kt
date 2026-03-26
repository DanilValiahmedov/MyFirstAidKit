package com.valimade.myfirstaidkit.medicine.domain.model

data class Medicine (
    val id: Int,
    val name: String, //Наименование лекарства
    val verificationName: String, //Название без пробелов и большом регистре (для поиска)
    val symptoms: List<String>, //Список симптомов (для показа)
    val symptomsVerification: List<String>, //Список симптомов без пробелов и большом регистре (для поиска)
    val diseases: List<String>, //Список болезней для которого походит лекарство (для показа)
    val diseasesVerification: List<String>, //Список болезней для которого походит лекарство без пробелов и большом регистре (для поиска)
    val forms: List<String>, //Форма лекарства (для показа)
    val formsVerification: List<String>, //Форма лекарства без пробелов и большом регистре (для поиска)
    val forWhoms: List<String>, //Для кого лекарство (для показа)
    val forWhomsVerification: List<String>, //Для кого лекарство без пробелов и большом регистре (для поиска)
    val expirationDate: Int, //Дата срока годности
    val comment: String, //Комментарий от пользователя
)
