package com.valimade.myfirstaidkit.medicine.data.model

data class Medicine (
    val name: String, //Наименование лекарства
    val symptoms: List<Symptoms>, //Список симптомов
    val diseases: List<Diseases>, //Список болезней для которого походит лекарство
    val form: Form, //Форма лекарства
    val forWhom: List<Whom>, //Для кого лекарство
    val expirationDate: Int, //Дата срока годности
    val comment: String, //Комментарий от пользователя
)