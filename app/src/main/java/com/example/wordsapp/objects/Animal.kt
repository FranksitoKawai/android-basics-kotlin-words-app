package com.example.wordsapp.objects

class Animal {
    val id: Int
    val nombre: String
    val descripcion: String
    val imagen: String
    val idTipoAnimal: Int

    constructor(_id: Int, _nombre : String, _descripcion: String, _imagen: String, _idTipoAnimal: Int){
        this.nombre = _nombre
        this.descripcion = _descripcion
        this.id = _id
        this.imagen = _imagen
        this.idTipoAnimal = _idTipoAnimal
    }
}