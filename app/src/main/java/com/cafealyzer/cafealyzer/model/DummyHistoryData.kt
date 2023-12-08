package com.cafealyzer.cafealyzer.model

data class CafeReview(
    val id: Int,
    val cafeAnda: String,
    val cafeKompetitor: String,
    val date: String,
    val positiveFeedbackAnda: List<String>,
    val negativeFeedbackAnda: List<String>,
    val positiveFeedbackKompetitor: List<String>,
    val negativeFeedbackKompetitor: List<String>,
    val suggestionsAnda: List<String>,
)

fun generateDummyReviews(): List<CafeReview> {
    return listOf(
        CafeReview(
            id = 1,
            cafeAnda = "Amala Coffee",
            cafeKompetitor = "Kedai Kopi Roemah 151",
            date = "2023-12-08",
            positiveFeedbackAnda = listOf(
                "Tempat nyaman dan luas meskipun terlihat kecil dari luar.",
                "Pelayanan cepat dan ramah, staf helpful dan sabar.",
                "Menu minuman variatif dan rasanya enak.",
                "Perbaikan yang signifikan dari pengalaman sebelumnya.",
                "Mendapatkan diskon yang cukup besar."
            ),
            negativeFeedbackAnda = listOf(
                "Pernah mengalami trauma dengan pelayanan sebelumnya di tempat ini.",
                "Ada review yang menyebutkan pelayanan jelak dan karyawan tidak sopan.",
                "Keluhan terkait kopi yang tidak enak."
            ),
            positiveFeedbackKompetitor = listOf(
                "Ekstraksi kopi terbaik, barista terampil.",
                "Kopi enak, tempat nyaman meskipun kecil.",
                "Pelayanan cepat dan ramah dengan harga standar."
            ),
            negativeFeedbackKompetitor = listOf(
                "Tidak ada kritik yang signifikan."
            ),
            suggestionsAnda = listOf(
                "Customer Recovery: Berikan respon yang proaktif terhadap review negatif, tawarkan kompensasi atau diskon sebagai langkah rekonsiliasi.",
                "Peningkatan Kualitas Kopi: Tingkatkan kualitas kopi untuk mengatasi keluhan pelanggan terkait rasa.",
                "Peningkatan Pelayanan: Berikan pelatihan kepada karyawan untuk meningkatkan kesopanan dan kualitas pelayanan.",
                "Menggunakan Review Positif: Manfaatkan review positif terutama terkait perbaikan pelayanan dan variasi menu sebagai poin penjualan."
            )
        ),
        CafeReview(
            id = 2,
            cafeAnda = "Coffee Haven",
            cafeKompetitor = "Espresso Delight",
            date = "2023-12-10",
            positiveFeedbackAnda = listOf(
                "Atmosfer yang menyenangkan dan nyaman.",
                "Pilihan menu kopi yang unik dan berkualitas.",
                "Pelayanan ramah dan informatif."
            ),
            negativeFeedbackAnda = listOf(
                "Harga menu terbilang cukup mahal.",
                "Waktu tunggu pesanan kadang-kadang agak lama."
            ),
            positiveFeedbackKompetitor = listOf(
                "Kualitas kopi sangat baik, rasa yang khas.",
                "Interior yang modern dan menyenangkan.",
                "Promo diskon yang menarik."
            ),
            negativeFeedbackKompetitor = listOf(
                "Menu terlalu umum, kurang variasi.",
                "Pelayanan kurang ramah dan tidak informatif."
            ),
            suggestionsAnda = listOf(
                "Perlu meningkatkan variasi menu untuk menarik lebih banyak pelanggan.",
                "Beri perhatian khusus pada pelatihan karyawan untuk pelayanan yang lebih baik."
            )
        )
    )
}