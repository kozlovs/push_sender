package ru.netology.pusher

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream


fun main() {
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()

    FirebaseApp.initializeApp(options)

    val massageType = MassageType.NEW_POST

    val message = when (massageType) {
        MassageType.LIKE -> makeLikeMassage()
        MassageType.NEW_POST -> makeNewPostMassage()
    }

    FirebaseMessaging.getInstance().send(message)
}

fun makeLikeMassage(): Message =
    Message.builder()
        .putData("action", "LIKE")
        .putData(
            "content", """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 2,
          "postAuthor": "Netology"
        }""".trimIndent()
        )
        .setToken(token)
        .build()

fun makeNewPostMassage(): Message =
    Message.builder()
        .putData("action", "NEW_POST")
        .putData(
            "content", """{
          "id": 1,
          "author": "Netology",
          "content": "Привет! Это новая нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен"
        }""".trimIndent()
        )
        .setToken(token)
        .build()