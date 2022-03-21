package uz.coder.mvpexample.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.coder.mvpexample.R
import uz.coder.mvpexample.databinding.ActivityMain2Binding
import uz.coder.mvpexample.models.books.books.BookX
import uz.coder.mvpexample.room.db.AppDatabase


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var isChecked = false
    private lateinit var book: BookX

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        
        book = intent.getSerializableExtra("book") as BookX

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow)

        checkSaved()
        save()
        loadData()

        binding.imageView2.setOnClickListener { onBackPressed() }

    }
    
    @SuppressLint("SetTextI18n")
    fun loadData(){

        Picasso.get().load(book.book_image).resize(200, 200).into(binding.bookImage, object :
            Callback {
            override fun onSuccess() {

            }

            override fun onError(e: Exception?) {

            }
        })

        binding.tv.text = book.title
        binding.textView.text = "by " + book.author
        binding.description.text = book.description


        binding.btn1.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.amazon_product_url))
                startActivity(browserIntent)
            } catch (e: Exception) {
                Toast.makeText(binding.root.context, "Something went wrong :(", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun save() {
        val dataBase = AppDatabase.getInstants(binding.root.context).dao()
        binding.save.setOnClickListener {

            if (isChecked) {
                binding.save.speed = -2f
                binding.save.playAnimation()
                isChecked = false
                dataBase.delete(book.book_image).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()
            } else {
                binding.save.speed = 3f
                binding.save.playAnimation()
                isChecked = true
                dataBase.add(book).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()
            }


        }
    }

    @SuppressLint("CheckResult")
    fun checkSaved() {

        val dataBase = AppDatabase.getInstants(binding.root.context).dao()
        dataBase.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->

                it.forEach {

                    if (it.book_image == book.book_image) {
                        binding.save.speed = 3f
                        binding.save.playAnimation()
                        isChecked = true
                    } else {
                        binding.save.speed = -2f
                        binding.save.playAnimation()
                        isChecked = false
                    }

                }

            }) {

            }


    }


}