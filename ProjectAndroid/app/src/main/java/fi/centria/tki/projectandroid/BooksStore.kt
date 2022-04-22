package fi.centria.tki.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BooksStore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_store)

        val fragmentList = BooksList()
        /*
        Starting the book list fragment at launch of Activity
         */
        this.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragmentList)
            .addToBackStack(null).commit()


    }
}