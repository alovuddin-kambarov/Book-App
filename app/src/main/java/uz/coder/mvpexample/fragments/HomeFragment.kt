package uz.coder.mvpexample.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import uz.coder.mvpexample.R
import uz.coder.mvpexample.activity.MainActivity2
import uz.coder.mvpexample.adapters.MyAdapter
import uz.coder.mvpexample.adapters.MyAdapter2
import uz.coder.mvpexample.adapters.MyAdapter3
import uz.coder.mvpexample.adapters.WritersAdapter
import uz.coder.mvpexample.databinding.FragmentHomeBinding
import uz.coder.mvpexample.models.books.books.BookX
import uz.coder.mvpexample.models.books.categories.Result
import uz.coder.mvpexample.retrofit.resource.Status
import uz.coder.mvpexample.retrofit.view_model.ViewModel
import uz.coder.mvpexample.utils.MyData

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog: AlertDialog
    private lateinit var arrayList: ArrayList<BookX>
    private lateinit var arrayList2: ArrayList<Result>
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)


        setProgress()

        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        binding.see.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("key", "Travel")
            findNavController().navigate(
                R.id.categoryFragment, bundle,
                setAnimation().build()
            )

        }

        getBooks()

        getCategoryName()

        binding.rvWriters.adapter = WritersAdapter(MyData.getAllWriters())



        return binding.root
    }

    private fun setProgress() {
        dialog = AlertDialog.Builder(binding.root.context).create()
        val view = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.custom_progress, null, false)
        dialog.setView(view)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun getBooks() {

        val aaa = object : MyAdapter.OnClick {
            override fun click(bookX: BookX) {
                startActivity(
                    Intent(
                        binding.root.context,
                        MainActivity2::class.java
                    ).putExtra("book", bookX)
                )
            }
        }

        viewModel.getData(binding.root.context, "hardcover-fiction").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.cancel()
                    arrayList = ArrayList()
                    arrayList.addAll(it.data!!.results.books)
                    val myAdapter = MyAdapter(arrayList, aaa)
                    binding.rvBook.adapter = myAdapter

                    search()


                }
                Status.LOADING -> {}
                Status.ERROR -> {

                    dialog.cancel()
                    AlertDialog.Builder(binding.root.context).setTitle("Connection error")
                        .setMessage("Something went wrong :(. Please, try again")
                        .setPositiveButton(
                            "Ok"
                        ) { p0, _ ->
                            p0.cancel()
                            activity?.finish()
                        }.show()

                }
            }


        }
    }

    private fun getCategoryName() {

        viewModel.getCategoryName(binding.root.context).observe(viewLifecycleOwner) {

            when (it.status) {
                Status.SUCCESS -> {

                    dialog.cancel()
                    arrayList2 = ArrayList()
                    arrayList2.addAll(it.data!!.body.results)
                    val myAdapter = MyAdapter2(arrayList2, object : MyAdapter2.OnClick {
                        override fun click(result: Result) {
                            val bundle = Bundle()
                            bundle.putString("key", result.list_name_encoded)
                            findNavController().navigate(
                                R.id.categoryFragment, bundle,
                                setAnimation().build()
                            )


                        }
                    })

                    binding.rv.adapter = myAdapter
                }
                Status.ERROR -> {

                    dialog.cancel()
                    AlertDialog.Builder(binding.root.context).setTitle("Connection error")
                        .setMessage("Something went wrong :(. Please, try again")
                        .setPositiveButton(
                            "Ok"
                        ) { p0, _ ->
                            p0.cancel()
                            activity?.finish()
                        }.show()


                }
                Status.LOADING -> {
                }
            }


        }
    }

    private fun search() {

        binding.search.addTextChangedListener {

            val myAdapter3 = MyAdapter3(arrayList, object : MyAdapter3.OnClick {
                override fun click(bookX: BookX) {
                    startActivity(
                        Intent(
                            binding.root.context,
                            MainActivity2::class.java
                        ).putExtra("book", bookX)
                    )
                }
            })


            if (it!!.isEmpty()) {
                binding.close.visibility = View.GONE
                binding.recyclerViewBouncy.visibility = View.GONE
                closeKerBoard()
            } else {
                binding.recyclerViewBouncy.visibility = View.VISIBLE
                binding.close.visibility = View.VISIBLE
                myAdapter3.filter(it.toString())
            }
            binding.recyclerViewBouncy.adapter = myAdapter3
        }

        binding.close.setOnClickListener {
            binding.recyclerViewBouncy.visibility = View.GONE
            binding.search.setText("")
            closeKerBoard()
        }


    }

    private fun closeKerBoard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun setAnimation(): NavOptions.Builder {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.exit_anim)
            .setExitAnim(R.anim.pop_enter_anim)
            .setPopEnterAnim(R.anim.enter_anim)
            .setPopExitAnim(R.anim.pop_exit_anim)
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Home"
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setHomeAsUpIndicator(R.drawable.more_icon2)
    }


}