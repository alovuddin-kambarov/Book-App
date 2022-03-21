package uz.coder.mvpexample.adaptersimport android.view.LayoutInflaterimport android.view.ViewGroupimport android.view.animation.AnimationUtilsimport androidx.recyclerview.widget.RecyclerViewimport uz.coder.mvpexample.Rimport uz.coder.mvpexample.databinding.CategoriesItemBindingimport uz.coder.mvpexample.models.books.categories.Resultimport kotlin.collections.ArrayListclass MyAdapter2(private val arraylist: ArrayList<Result>, var onClick: OnClick) :    RecyclerView.Adapter<MyAdapter2.ViewH>() {    inner class ViewH(private var binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {        fun onBind(myClass: Result) {            itemView.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.recy_anim)            binding.tv.text = myClass.list_name            itemView.setOnClickListener { onClick.click(myClass) }        }    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewH {        return ViewH(CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))    }    override fun getItemCount(): Int {        return arraylist.size    }    override fun onBindViewHolder(holder: ViewH, position: Int) {        holder.onBind(arraylist[position])    }    interface OnClick{        fun click(result: Result)    }}