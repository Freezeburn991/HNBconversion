import Repositories.RepositoryImpl
import models.Artikl
import models.Cjenik
import models.ProdajnoMjesto
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
    val prodajnaMjesta  = repo.fillProdajnaMjesta()

    val stanja = repo.fillStanja()

    val stanjaProdajnaMjesta = repo.fillProdajnaMjestaStanja(prodajnaMjesta, stanja)











}












