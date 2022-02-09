package com.example.wordsapp.objects

class TipoAnimal {
    val id: Int
    val nombre: String
    val image: String

    constructor(_id: Int, _nombre: String, _image: String){
        this.id = _id
        this.nombre = _nombre
        this.image = _image
    }
}