package uz.coder.mvpexample.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.coder.mvpexample.R
import uz.coder.mvpexample.activity.MainActivity2
import uz.coder.mvpexample.adapters.MyAdapter3
import uz.coder.mvpexample.databinding.FragmentCategoryBinding
import uz.coder.mvpexample.models.books.books.BookX
import uz.coder.mvpexample.retrofit.resource.Status
import uz.coder.mvpexample.retrofit.view_model.ViewModel

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)


        binding.progress.playAnimation()

        val key = arguments?.getString("key")

        val viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.getData(binding.root.context, key!!).observe(viewLifecycleOwner) {
            val aaa = object : MyAdapter3.OnClick {
                override fun click(bookX: BookX) {
                    startActivity(
                        Intent(
                            binding.root.context,
                            MainActivity2::class.java
                        ).putExtra("book", bookX)
                    )
                }
            }
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    val arrayList = ArrayList<BookX>()
                    arrayList.addAll(it.data!!.results.books)
                    val myAdapter = MyAdapter3(arrayList, aaa)
                    binding.rvBook.adapter = myAdapter

                }
                Status.LOADING -> {}
                Status.ERROR -> {

                    AlertDialog.Builder(binding.root.context).setTitle("Connection error")
                        .setMessage("${it.message}")
                        .setPositiveButton(
                            "Ok"
                        ) { p0, _ -> p0.cancel() }.show()

                }
            }


        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Categories"
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.more_icon2)
    }


}