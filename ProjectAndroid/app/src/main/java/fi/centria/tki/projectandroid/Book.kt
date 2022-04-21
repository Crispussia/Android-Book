package fi.centria.tki.project_android

data class Book(
    var error:String,
    var total:String,
    var books:ArrayList<BookInfo>,
    var details:List <BookDetails>

)

data class BookInfo(
    var title:String,
    var subtitle:String,
    var isbn13:String,
    var price:String,
    var image:String,
    var url:String
)
data class BookDetails(
   var title:String,
   var authors: String,
   var pages:String,
   var year:String,
   var rating:String,
   var desc:String,
   var image: String,
   var url: String

)


