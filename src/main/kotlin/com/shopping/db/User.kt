package com.shopping.db

class User {
    private var id = 0
    private var username: String? = null
    private var name: String? = null
    private var age = 0
    private var gender: String? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getAge(): Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
    }

    fun getGender(): String? {
        return gender
    }

    fun setGender(gender: String?) {
        this.gender = gender
    }
}