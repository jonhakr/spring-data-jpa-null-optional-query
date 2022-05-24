package com.example.demo

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Jedi(
    @Id
    val id: Long,
    val name: String,
    val description: String?
) : Serializable