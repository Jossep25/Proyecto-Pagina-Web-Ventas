package model
data class users(val id: Int, 
                val username: String, 
                val password: String, 
                val email: String,
                val roleId: Int, 
                val firstName: String, 
                val lastName: String, 
                val dni: String
                //agregaremos mas dependiendo de la demanda de datos que necesitemos
                )