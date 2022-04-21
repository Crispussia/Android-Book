package fi.centria.tki.projectandroid

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fi.centria.tki.project_android.BookDetails
import fi.centria.tki.project_android.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BooksDetails : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var imageBack: ImageView
    lateinit var idBook: TextView
    lateinit var imageBook: ImageView
    lateinit var title: TextView
    lateinit var author: TextView
    lateinit var book_pages: TextView
    lateinit var detailsbook: TextView
    lateinit var year: TextView
    lateinit var ratingBar: RatingBar
    lateinit var url: TextView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  val mActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        mActionBar?.title = "Book Details"*/


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books_details, container, false)


        imageBack = view.findViewById(R.id.imageBack)
        idBook = view.findViewById(R.id.year)
        imageBook=view.findViewById(R.id.imageBook)
        title=view.findViewById(R.id.title)
        author=view.findViewById(R.id.author)
        book_pages=view.findViewById(R.id.book_pages)
        detailsbook=view.findViewById(R.id.details_book)
        year=view.findViewById(R.id.year)
        ratingBar=view.findViewById(R.id.ratingBar)
        url=view.findViewById(R.id.url)
        toolbar=view.findViewById(R.id.toolbar)

        val intent = requireArguments().getString("CID")

        //initiate the service
        val destinationService = RetrofitInstance.buildBooksService()
        val requestCall = destinationService.getBooksDetails("$intent")
        Log.d("IdBook", "${intent}")
        lateinit var bookDetails: BookDetails


      imageBack.setOnClickListener{

          val fr: Fragment = BooksList()
          val trans= activity?.supportFragmentManager?.beginTransaction()
          trans?.replace(R.id.fragmentContainer, fr)?.addToBackStack(null)?.commit()
           /* activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, bookFragment)
                .addToBackStack(null).commit()*/
         /* Toast.makeText(
              activity,
              "yes",
              Toast.LENGTH_SHORT
          ).show()*/
        }
        //make network call asynchronously
       requestCall.enqueue(object : Callback<BookDetails> {
            override fun onResponse(call: Call<BookDetails>, response: Response<BookDetails>) {
                Log.d("Response", "onResponse: ${response.body()}")

                if (response.isSuccessful) {
                   bookDetails = response.body()!!
                     Log.d("Response", "bookList")
                    /*Toast.makeText(
                         activity,
                         "yes",
                         Toast.LENGTH_SHORT
                     ).show()*/
                   title.text=bookDetails.title
                   author.text= bookDetails.authors
                    book_pages.text="${bookDetails.pages} pages"
                    ratingBar.rating= bookDetails.rating.toFloat()
                    Picasso.get().load(bookDetails.image).into(imageBook)

                    year.text= bookDetails.year
                    detailsbook.text=bookDetails.desc


                    url.paintFlags =url.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                    url.text= bookDetails.url

                    url.setOnClickListener{
                        val intent = Intent(
                            ACTION_VIEW,
                            Uri.parse(url.text.toString())
                        )
                        startActivity(intent)
                    }

                } else {
                    Log.d("error", "error: ${response.message()}")
                      Toast.makeText(
                          activity,
                          "Something went wrong here ${response.message()}",
                          Toast.LENGTH_SHORT
                      ).show()
                }
            }

            override fun onFailure(call: Call<BookDetails>, t: Throwable) {
                Log.d("Response fail", "${t}")
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
         * @return A new instance of fragment BooksDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() : BooksDetails{
            return BooksDetails()
        }

    }
}