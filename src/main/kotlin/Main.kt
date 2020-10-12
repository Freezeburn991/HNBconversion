import Repositories.RepositoryImpl
import models.*
import java.io.*
import java.lang.IllegalStateException
import java.net.URL



    fun main(args: Array<String>) {

        val text = URL("http://api.hnb.hr/tecajn/v1").readText()

        val repo = RepositoryImpl()

        // Svaki tecaj je u ovom array-u i lagano dohvatljiv
        val arrayTecaj = repo.fillHNBData(text)

        // Svi artikli sa cjenama
        val artikli = repo.fillArtiklData()

        // Sva prodajna mjesta
        val prodajnaMjesta = repo.fillProdajnaMjesta()

        // Napunjeno sa listom stanja artikala u razlicitim prodavaonicama
        val stanja = repo.fillStanja()

        // Stanje artikala u prodavaoniocama
        val stanjaProdajnaMjesta = repo.fillProdajnaMjestaStanja(prodajnaMjesta, stanja)

        val articleSumInStores : ArrayList<ArticleSumStores> = getAricleRatesInAllStores(arrayTecaj.get(2).prodajni_za_devize, artikli, stanjaProdajnaMjesta)

        val storeSum = getStoreArticlesData(arrayTecaj.get(2).prodajni_za_devize, stanjaProdajnaMjesta, artikli)

        println("sadsad")

    }

    // Priprema za prvu datoteku, slaganje podataka i ispis u json
    private fun getAricleRatesInAllStores(choosenRateHNB : Float, articles: ArrayList<Artikl>, storesAndSum:  Map<ProdajnoMjesto, ArrayList<Stanja>>): ArrayList<ArticleSumStores>{

        val articleSumInStores =  ArrayList<ArticleSumStores>()

        for(article in articles){
            var numberOfStores = 0
            var sumOfArticleInAllStores: Int? = 0
            for(store in storesAndSum){
                for(value in store.value){
                    if (sumOfArticleInAllStores != null) {
                        if((article.sifra).equals(value.sifra_Artikla)){
                            numberOfStores++
                            if(value.kolicina == null){
                                sumOfArticleInAllStores += 0
                            }else{
                                sumOfArticleInAllStores += value.kolicina!!
                            }
                        }
                    }
                }
            }
            val sumPriceInAllStores: Float? = sumOfArticleInAllStores?.times( article.cijena!!)
            val sumPriceDifferentRate = sumPriceInAllStores?.times(choosenRateHNB)

            articleSumInStores.add(ArticleSumStores(
                article,
                sumInAllStores = sumOfArticleInAllStores,
                sumPriceinAllStores = sumPriceInAllStores,
                sumPriceinAllStoresInDiffrentRate = sumPriceDifferentRate,
                numberOfStoresOfArticle = numberOfStores
            ))
        }
    return articleSumInStores
    }

    // Priprema za drugu datoteku
    private fun getStoreArticlesData(choosenRateHNB: Float, storesAndSum: Map<ProdajnoMjesto, ArrayList<Stanja>>, articles: ArrayList<Artikl>): Map<ProdajnoMjesto, TotalSum>{

        val arrayOfTotalStoreSum = HashMap<ProdajnoMjesto, TotalSum>()

        for(store in storesAndSum){
            var totalValueHRK: Float? = 0.0f
            var numberOfArticles: Int = store.value.size

            for(value in store.value){
                for(article in articles){
                    if (totalValueHRK != null) {
                        if(value.sifra_Artikla!! == article.sifra){

                            var totalSumOfArticlePrice = value.kolicina?.times(article.cijena!!)


                            if (totalSumOfArticlePrice != null) {
                                totalValueHRK += totalSumOfArticlePrice
                            }
                        }
                    }
                }

            }
            if (totalValueHRK != null) {
                arrayOfTotalStoreSum.put(store.key, TotalSum(
                    totalValueHRK,
                    totalValueHRK*choosenRateHNB,
                    numberOfArticles
                    )
                )
            }
        }
        return arrayOfTotalStoreSum
    }

















