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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BooksList.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
 //   lateinit var items: Book
    lateinit var recyclerView: RecyclerView
    lateinit var bookAdapter: BooksAdapter
    lateinit var book_search: SearchView
    lateinit var imageBack: ImageView
    //lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        mActionBar?.title = "Books Lists"

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
        //initiate the service
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
                    /*imageSearch.setOnClickListener(){
                        Log.d("Response", "yes :")
                        val search=editText.text.toString()
                        bookAdapter.filter.filter(search)
                    }*/
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BooksList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() : BooksList {
            return BooksList()
        }
    }
}