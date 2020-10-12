package models

data class ArticleSumStores(
    val article: Artikl,
    val sumInAllStores: Int?,
    val sumPriceinAllStores: Float?,
    val sumPriceinAllStoresInDiffrentRate: Float?,
    val numberOfStoresOfArticle: Int?
)