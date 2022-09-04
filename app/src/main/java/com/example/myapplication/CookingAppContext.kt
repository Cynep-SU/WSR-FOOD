package com.example.myapplication

/* Это статический, нестатический класс :)
Фактически избавляет нас от мороки с PutExtra,
и является неким контекстом
 */
class CookingAppContext {
    companion object{
        var isOffline = false
        lateinit var token: String
    }
}