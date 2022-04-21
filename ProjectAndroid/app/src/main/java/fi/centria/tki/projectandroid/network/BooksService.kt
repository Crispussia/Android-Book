package fi.centria.tki.project_android.network

import fi.centria.tki.project_android.Book
import fi.centria.tki.project_android.BookDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BooksService {

    @GET("new")
    fun  getBooks(): Call<Book>

    @GET("books/{isbn13}")
    fun  getBooksDetails(@Path("isbn13") bookIs:String): Call<BookDetails>
}