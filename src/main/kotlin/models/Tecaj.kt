package models

import org.joda.time.DateTime
import java.util.*

data class Tecaj(
    val broj_tecajnice: Int,
    val datum_primjene: DateTime,
    val drzava: String,
    val sifra_valute: String,
    val valuta: String,
    val jedinica: Int,
    val kupovni_zadevize: Float,
    val srednji_za_devize: Float,
    val prodajni_za_devize: Float
)