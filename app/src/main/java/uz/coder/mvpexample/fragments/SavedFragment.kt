package uz.coder.mvpexample.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.coder.mvpexample.R
import uz.coder.mvpexample.activity.MainActivity2
import uz.coder.mvpexample.adapters.SavedAdapter
import uz.coder.mvpexample.databinding.FragmentSavedBinding
import uz.coder.mvpexample.models.books.books.BookX
import uz.coder.mvpexample.room.db.AppDatabase


class SavedFragment : Fragment() {
    lateinit var binding: FragmentSavedBinding

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater)

        val savedAdapter = SavedAdapter(object : SavedAdapter.OnItemMenuClick {
            override fun click(bookX: BookX) {

                startActivity(Intent(
                    binding.root.context,
                    MainActivity2::class.java
                ).putExtra("book", bookX))


            }
        })


        AppDatabase.getInstants(binding.root.context).dao().getAll().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({

                savedAdapter.submitList(it)
                if (it.isEmpty())
                    binding.tv.visibility = View.VISIBLE
                else
                    binding.tv.visibility = View.GONE

            }) {

            }

        binding.rv.adapter = savedAdapter

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Saved"
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.more_icon2)
    }


}