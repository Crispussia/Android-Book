package fi.centria.tki.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var books_Button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        books_Button=findViewById(R.id.button_book)
        books_Button.setOnClickListener{view->

            //Start Boostore Activity to see the book List
            view_books()
        }
    }

    fun view_books(){
        val intentBooks = Intent(this, BooksStore::class.java)
        intentBooks.apply {
            startActivity(this)
        }
        /*  Toast.makeText(this,txtAnimals, Toast.LENGTH_LONG).show()*/
    }
}