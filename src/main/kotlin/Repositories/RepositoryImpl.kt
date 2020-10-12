package Repositories

import models.*
import org.json.JSONArray
import org.json.JSONObject
import Util.convertStringToDateTime
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

// Stavljeno na it da moze biti null, tj. ? jer inace javlja "java.lang.IllegalStateException: it must not be null" jer neki redak moze biti prazan

class RepositoryImpl : Repository{

    override fun fillHNBData(json: String): ArrayList<Tecaj> {

        val arrayTecaj = arrayListOf<Tecaj>()

        val arrayJson = JSONArray(json)

        for(line in arrayJson ){

            var str : String = line.toString()
            val obJson = JSONObject(str)
            val broj_tecajnice = obJson.getInt("Broj tečajnice")
            val datum_primjene = obJson.getString("Datum primjene")
            val drzava = obJson.getString("Država")
            val sifra_valute = obJson.getString("Šifra valute")
            val valuta = obJson.getString("Valuta")
            val jedinica = obJson.getInt("Jedinica")

            val kupovni_zadevize = obJson.getString("Kupovni za devize").replace(',', '.').toFloat()
            val srednji_za_devize = obJson.getString("Srednji za devize").replace(',', '.').toFloat()
            val prodajni_za_devize = obJson.getString("Prodajni za devize").replace(',', '.').toFloat()

            val tecaj = Tecaj(
                broj_tecajnice = broj_tecajnice,
                datum_primjene = convertStringToDateTime(datum_primjene),
                drzava = drzava,
                sifra_valute = sifra_valute,
                valuta = valuta,
                jedinica = jedinica,
                kupovni_zadevize = kupovni_zadevize,
                srednji_za_devize = srednji_za_devize,
                prodajni_za_devize = prodajni_za_devize
            )
            arrayTecaj.add(tecaj)
        }

        return arrayTecaj
    }

    override fun fillArtiklData(): ArrayList<Artikl> {

        val artikli = arrayListOf<Artikl>()
        val cjenici =  fillCjenikData()

        try {
            BufferedReader(InputStreamReader(FileInputStream("D:\\zadatak_3_kotlin\\dokumenti\\artikli.txt"), "Windows-1250")).use { `in` ->
                var str: String?

                while (`in`.readLine().also { str = it } != null) {
                    val tempStr = str?.split("|")?.toTypedArray()


                    for (i in cjenici){
                        if (tempStr?.get(0)?.toInt()?.equals(i.sifra)!!){
                            val artikl = Artikl(
                                sifra = tempStr[0].toInt(),
                                naziv = tempStr[1],
                                jedinicaMjere = tempStr[2],
                                cijena = i.cijena
                            )

                            artikli.add(artikl)
                        }
                    }

                }
            }
        } catch (e: IOException) {
            println("File Read Error artikli")
        }

        return artikli
    }

    override fun fillCjenikData(): ArrayList<Cjenik> {

            val cjenik = arrayListOf<Cjenik>()

            try {
                BufferedReader(
                    InputStreamReader(
                        FileInputStream("D:\\zadatak_3_kotlin\\dokumenti\\cjenik.txt"),
                        "Windows-1250"
                    )
                ).use { `in` ->
                    var strCijena: String?
                    while (`in`.readLine().also {strCijena = it} != null) {

                        val tempStrCijena = strCijena?.split("|")?.toTypedArray()
                        val cjena = Cjenik(
                            sifra = tempStrCijena?.get(0)?.toInt(),
                            cijena = tempStrCijena?.get(1)?.replace(",", ".")?.toFloat()
                        )
                        cjenik.add(cjena)
                    }
                }
            }catch (e: IOException) {
                println("File Read Error cjenik")
            }
            return cjenik

        }

    override fun fillProdajnaMjesta(): ArrayList<ProdajnoMjesto> {

        val prodajnaMjesta =  ArrayList<ProdajnoMjesto>()

        try {
            BufferedReader(
                InputStreamReader(
                    FileInputStream("D:\\zadatak_3_kotlin\\dokumenti\\pm.txt"),
                    "Windows-1250"
                )
            ).use { `in` ->
                var strProdajnoMjesto: String?
                while (`in`.readLine().also { strProdajnoMjesto = it } != null){
                    val tempStrProdajnoMjesto = strProdajnoMjesto?.split("|")?.toTypedArray()
                    prodajnaMjesta.add(
                        ProdajnoMjesto(
                        sifra = tempStrProdajnoMjesto?.get(0),
                            naziv = tempStrProdajnoMjesto?.get(1)
                    ))
                }
            }
        }
        catch (e: IOException) {
            println("File Read Error pm")
        }

        return prodajnaMjesta
    }

    override fun fillStanja(): ArrayList<Stanja> {
        val stanja = ArrayList<Stanja>()
        try {
            BufferedReader(InputStreamReader(FileInputStream("D:\\zadatak_3_kotlin\\dokumenti\\stanja.txt"), "Windows-1250")).use { `in` ->
                var stanjeStr: String?
                while (`in`.readLine().also { stanjeStr = it } != null){
                    val tempStrStanja = stanjeStr?.split("|")?.toTypedArray()
                    stanja.add(Stanja(
                        sifra_Artikla = tempStrStanja?.get(0)?.toInt(),
                        prodajnoMjestoSifra = tempStrStanja?.get(1),
                         kolicina =  tempStrStanja?.get(2)?.replace(",", "")?.toInt()
                    ))
                }
            }
        }catch (e: IOException) {
            println("File Read Error stanja")
        }
    return stanja

    }

    override fun fillProdajnaMjestaStanja(prodajnaMjesta: ArrayList<ProdajnoMjesto>, stanja: ArrayList<Stanja>): Map<ProdajnoMjesto, ArrayList<Stanja>> {

        val stanjaProdajnaMjestaMap = HashMap<ProdajnoMjesto, ArrayList<Stanja>>()

        for(prodajnoMjesto in prodajnaMjesta){
            val stanjeLista = ArrayList<Stanja>()
            for(stanje in stanja){

               /* if (stanjaProdajnaMjestaMap.containsKey(prodajnoMjesto)){
                    stanjaProdajnaMjestaMap.put(prodajnoMjesto, stanje)
                }*/
                 if(stanje.prodajnoMjestoSifra?.equals(prodajnoMjesto.sifra)!!){
                     stanjeLista.add(stanje)
                    stanjaProdajnaMjestaMap.put(prodajnoMjesto, stanjeLista)

                }
            }
        }

        return stanjaProdajnaMjestaMap
    }


}


