package Repositories

import models.*

interface Repository {

     fun fillHNBData(text: String): ArrayList<Tecaj>

     fun fillArtiklData(): ArrayList<Artikl>

     fun fillCjenikData(): ArrayList<Cjenik>

     fun fillProdajnaMjesta(): ArrayList<ProdajnoMjesto>

     fun fillStanja(): ArrayList<Stanja>

     fun fillProdajnaMjestaStanja(prodajnoMjesto: ArrayList<ProdajnoMjesto>, stanja: ArrayList<Stanja>): Map<ProdajnoMjesto, ArrayList<Stanja>>




}