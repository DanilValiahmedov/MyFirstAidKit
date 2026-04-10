package com.valimade.myfirstaidkit.domain.model

data class Medicine (
    val id: Int? = null,
    val name: String, //Наименование лекарства
    val verificationName: String, //Название без пробелов и большом регистре (для поиска)
    val symptoms: List<String> = emptyList(), //Список симптомов (для показа)
    val symptomsVerification: List<String> = emptyList(), //Список симптомов без пробелов и большом регистре (для поиска)
    val diseases: List<String> = emptyList(), //Список болезней для которого походит лекарство (для показа)
    val diseasesVerification: List<String> = emptyList(), //Список болезней для которого походит лекарство без пробелов и большом регистре (для поиска)
    val forms: List<String> = emptyList(), //Форма лекарства (для показа)
    val formsVerification: List<String> = emptyList(), //Форма лекарства без пробелов и большом регистре (для поиска)
    val whoms: List<String> = emptyList(), //Для кого лекарство (для показа)
    val whomsVerification: List<String> = emptyList(), //Для кого лекарство без пробелов и большом регистре (для поиска)
    val locations: List<String> = emptyList(), //Где хранится лекарство (для показа)
    val locationsVerification: List<String> = emptyList(), //Где хранится лекарство без пробелов и большом регистре (для поиска)
    val expirationDateVisually: String? = null, //Дата срока годности (для показа)
    val expirationDate: Long? = null, //Дата срока годности (Unix-время)
    val comment: String? = null, //Комментарий от пользователя
)
