package fi.centria.tki.projectandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fi.centria.tki.project_android.Book
import fi.centria.tki.project_android.BooksAdapter
import fi.centria.tki.project_android.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BooksList : Fragment() {

    //Initialise variable
    lateinit var recyclerView: RecyclerView
    lateinit var bookAdapter: BooksAdapter
    lateinit var book_search: SearchView
    lateinit var imageBack: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* val mActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        mActionBar?.title = "Books Lists"*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_books_list, container,
            false )

        book_search=view.findViewById(R.id.book_search)
        recyclerView=view.findViewById(R.id.recview);
        imageBack = view.findViewById(R.id.imageBack)
        imageBack.setOnClickListener{v->
            val activity = v!!.context as AppCompatActivity
            val intentBooks = Intent(activity, MainActivity::class.java)
            intentBooks.apply {
                startActivity(this)
            }
        }

        //Initiate the service
        val destinationService  = RetrofitInstance.buildBooksService()
        val requestCall =destinationService.getBooks()

        //make network call asynchronously
        requestCall.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val bookList  = response.body()

                    Log.d("Response", "bookList  size : ${bookList?.books?.size}")
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = response.body()?.let { BooksAdapter(it) }
                    }
                    bookAdapter= bookList?.let { BooksAdapter(it) }!!

                    //Search for books by clicking on the search button

                    book_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            bookAdapter.filter.filter(newText)
                            return true
                        }
                    })

                }else{
                    Toast.makeText(activity, "Something went wrong here ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Book>, t: Throwable) {
                Log.d("Response fail","${t}")
                Toast.makeText(activity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() : BooksList {
            return BooksList()
        }
    }
}