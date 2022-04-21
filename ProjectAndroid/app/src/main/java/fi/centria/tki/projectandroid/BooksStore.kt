package fi.centria.tki.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BooksStore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_store)

        val fragmentList = BooksList()

        this.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragmentList)
            .addToBackStack(null).commit()


    }
}