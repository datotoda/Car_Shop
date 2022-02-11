package com.datotoda.carshop.model.enums

enum class Marks (val displayName: String) {
    AUDI("Audi"),
    BMW("BMW"),
    MERCEDES("Mercedes"),
    TOYOTA("Toyota"),
    MITSUBISHI("Mitsubishi"),
    OTHER("Other");
    companion object {
        fun getMarkByDisplayName(displayName: String): Marks{
            values().forEach {
                if (it.displayName == displayName) return it
            }
            return OTHER
        }
        fun displayNames() = values().map { it.displayName}
    }

}




