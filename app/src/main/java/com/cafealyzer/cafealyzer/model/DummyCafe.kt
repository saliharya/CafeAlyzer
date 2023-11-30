package com.cafealyzer.cafealyzer.model

data class Cafe(
    val image: String,
    val title: String,
    val rating: String,
)

val dummyCafeSumedang = listOf(
    Cafe(
        "https://lh5.googleusercontent.com/p/AF1QipM0FN8hKn2NO_dqqYdo62ic_2xVMT-_pisApuyf=w408-h408-k-no",
        "Kedai Kopi Roemah 151",
        "4.7 (210)"
    ), Cafe(
        "https://lh5.googleusercontent.com/p/AF1QipPHW0compS5x1lgW8J3fh5y886-FRNJiHhfJFz_=w408-h306-k-no",
        "Manualism Coffee",
        "4.7 (186)"
    ), Cafe(
        "https://lh5.googleusercontent.com/p/AF1QipNRqJg6M_abp3T1BUWJpX5pl2wTnnSuBn_9jmbu=w408-h510-k-no",
        "Hej House",
        "4.5 (462)"
    )
)

val dummyCafeJakarta = listOf(
    Cafe(
        "https://lh5.googleusercontent.com/p/AF1QipNd6jJLXJ3YZ6xzFuJF3YYpjzLD4ZeJngIi9eRk=w408-h261-k-no",
        "The Café",
        "4,7 (3.371)"
    ), Cafe(
        "https://lh5.googleusercontent.com/p/AF1QipPJGVLH1zh76NU2UBL4JCFW4GB5eW3zkM285GJC=w408-h408-k-no",
        "Cecemuwe Cafe and Space - Senayan",
        "4,7 (1.036)"
    ), Cafe(
        "https://lh5.googleusercontent.com/p/AF1QipNAKbxY9RRSSeGEBsWV86s1zj9wGxA8OE9aRega=w426-h240-k-no",
        "Monolog Coffee Company Plaza Senayan",
        "4,6 (3.097)"
    )
)