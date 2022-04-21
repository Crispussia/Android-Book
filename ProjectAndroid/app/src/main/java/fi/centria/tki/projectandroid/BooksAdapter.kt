package fi.centria.tki.project_android

import android.annotation.SuppressLint
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fi.centria.tki.projectandroid.BooksDetails
import fi.centria.tki.projectandroid.BooksStore
import fi.centria.tki.projectandroid.R
import java.util.*
import kotlin.collections.ArrayList


class BooksAdapter(var items: Book): RecyclerView.Adapter<BooksAdapter.BooksViewHolder>(),Filterable{
    var booksListFilter: ArrayList<BookInfo>
    init {
        booksListFilter= items.books
    }

    override fun getFilter(): Filter {
       return  object :Filter(){
           override fun performFiltering(constraint: CharSequence?): FilterResults {
               val charSearch = constraint.toString()
               if (charSearch.isEmpty()) {
                   booksListFilter = items.books
               } else {

                   val filteredList = ArrayList<BookInfo>()
                   for (book in items.books){
                       if (book.title.lowercase().contains(charSearch.lowercase())){
                           filteredList.add(book)
                       }
                   }
                   booksListFilter=filteredList
               }
               val filterResults = FilterResults()
               filterResults.values = booksListFilter
               return filterResults
           }

           @SuppressLint("NotifyDataSetChanged")
           override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               booksListFilter = results?.values as ArrayList<BookInfo>
               notifyDataSetChanged()
           }

       }
    }
  /*  override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    booksListFilter = items
                } else {
                    val filteredList = ArrayList<BookInfo>()
                    for (book in items.books){
                        if (book.title.lowercase().contains(charSearch.lowercase())){
                            filteredList.add(book)
                        }
                    }
                    booksListFilter.books=filteredList
                }
                return FilterResults().apply { values = booksListFilter}
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                booksListFilter.books= results?.values as ArrayList<BookInfo>
                notifyDataSetChanged()
            }

        }
    }
*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_books,parent,false)
        return BooksViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {

        Log.d("Response", "List Count :${items.books.size} ")
        return holder.bind(booksListFilter[position])
    }

    override fun getItemCount()=  booksListFilter.size

    /*Creation of the View Container*/

    class BooksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
         var tvTile: TextView
         var tvSubtitle: TextView
         var tvIdBook: TextView
         var imageBook: ImageView
         var tvPrice:TextView
        var view:View=itemView
        var book:BookInfo?=null
        /*Construction*/
        init {
            tvTile=itemView.findViewById(R.id.tvTitle)
            tvSubtitle=itemView.findViewById(R.id.tvSubtitle)
            tvIdBook=itemView.findViewById(R.id.tvReference)
            imageBook=itemView.findViewById(R.id.imageBook)
            tvPrice=itemView.findViewById(R.id.tvPrice)
          /* itemView.setOnClickListener{ v ->
                val intent = Intent(v.context, DetailsBooks::class.java)
                v.context.startActivity(intent)
            }*/


            itemView.setOnClickListener { v ->
              /*  val activity = v!!.context as AppCompatActivity
                val bookFragment = FragmentB()

                activity.supportFragmentManager.beginTransaction().replace(R.id.rec, bookFragment)
                    .addToBackStack(null).commit()
*/              val activity = v!!.context as AppCompatActivity
                val fr: Fragment = BooksDetails()
                val trans= activity.supportFragmentManager.beginTransaction()
                val args = Bundle()
                args.putString("CID", tvIdBook.text.toString())
                fr.arguments = args
                trans.replace(R.id.fragmentContainer, fr).addToBackStack(null).commit()

            }

        }

        fun bind(book:BookInfo){
            tvTile.text=book.title
            tvSubtitle.text=book.subtitle
            tvIdBook.text=book.isbn13
            tvPrice.text=book.price
            Picasso.get().load(book.image).into(imageBook)


        }

        override fun onClick(p0: View?) {
            Log.d("Click","yes")
           // var toast=Toast.makeText(view.context,"ok",Toast.LENGTH_LONG).show()
            val intent = Intent(p0?.context, BooksStore::class.java)

                p0?.context?.startActivity(intent)

        }

    }




}