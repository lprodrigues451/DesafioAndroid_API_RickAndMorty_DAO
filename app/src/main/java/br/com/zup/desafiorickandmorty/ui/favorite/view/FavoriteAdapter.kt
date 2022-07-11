package br.com.zup.desafiorickandmorty.ui.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.desafiorickandmorty.data.model.Result
import br.com.zup.desafiorickandmorty.databinding.RickAndMortyItemBinding
import com.squareup.picasso.Picasso

class FavoriteAdapter(
    private var rickAndMortyList: MutableList<Result>,
    private val clickCharacter: (result: Result) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RickAndMortyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rickAndMorty = rickAndMortyList[position]
        holder.showRickAndMortyInfo(rickAndMorty)
        holder.binding.cvItemLista.setOnClickListener {
            clickCharacter(rickAndMorty)
        }
    }

    override fun getItemCount() = rickAndMortyList.size

    class ViewHolder(val binding: RickAndMortyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun showRickAndMortyInfo(result: Result) {
            binding.tvNameCharacter.text = result.name
            Picasso.get().load("https://rickandmortyapi.com/api/character/avatar/${result.id}.jpeg")
                .into(binding.imageView)
        }
    }

    fun updateRickAndMortyList(newList: MutableList<Result>) {
        rickAndMortyList = newList
        notifyDataSetChanged()

    }
}