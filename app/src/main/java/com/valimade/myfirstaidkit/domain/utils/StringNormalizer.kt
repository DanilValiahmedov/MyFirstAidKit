package com.valimade.myfirstaidkit.domain.utils

object StringNormalizer {
    fun normalizeVerificationName(name: String): String {
        return name
            .trim()
            .uppercase()
            .replace("Ё","Е")
            .replace("Ъ","Ь")
    }
}