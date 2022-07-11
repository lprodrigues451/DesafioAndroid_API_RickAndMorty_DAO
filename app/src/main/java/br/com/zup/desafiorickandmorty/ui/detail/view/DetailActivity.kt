package br.com.zup.desafiorickandmorty.ui.detail.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import br.com.zup.desafiorickandmorty.R
import br.com.zup.desafiorickandmorty.data.model.Result
import br.com.zup.desafiorickandmorty.databinding.ActivityDetailBinding
import br.com.zup.desafiorickandmorty.ui.*
import br.com.zup.desafiorickandmorty.ui.detail.viewmodel.DetalheViewModel
import br.com.zup.desafiorickandmorty.ui.viewstate.ViewState
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetalheViewModel by lazy {
        ViewModelProvider(this)[DetalheViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPassedData()
        initObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun getPassedData() {
        val rickAndMorty = intent.getParcelableExtra<Result>(KEY_RICK_AND_MORTY)

        rickAndMorty?.let {
            Picasso.get().load("https://rickandmortyapi.com/api/character/avatar/${it.id}.jpeg")
                .into(binding.ivPersonage)
            binding.tvName.text = "$NAME ${it.name}"
            binding.tvStatus.text = "$STATUS ${it.status}"
            binding.tvSpecies.text = "$ESPECIE ${it.species}"
            binding.tvGenre.text = "$GENERO ${it.gender}"
            updateColorWhiteAndYellow(it)
            displayNaAppBar(it.name)
            clickButton(it)
        }
    }

    private fun favoritedPersonage(result: Result) {
        viewModel.updatePersonageFavorited(result)
    }

    private fun initObserver() {
        viewModel.personageFavoriteState.observe(this) {
            when (it) {
                is ViewState.Success -> {
                    updateColorWhiteAndYellow(it.data)
                }
                is ViewState.Error -> {
                    Toast.makeText(
                        this,
                        "${it.throwable.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

    private fun clickButton(result: Result) {
        binding.ivFavorite.setOnClickListener {
            result.isFavorite = !result.isFavorite
            favoritedPersonage(result)
            MessageFavoriteAndUnFavorite(result)
        }
    }

    private fun updateColorWhiteAndYellow(result: Result) {
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                if (result.isFavorite)
                    R.drawable.ic_launcher_favorite
                else
                    R.drawable.ic_launcher_disfavor
            )
        )
    }

    private fun displayNaAppBar(name: String) {
        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun MessageFavoriteAndUnFavorite(result: Result) {
        if (result.isFavorite) {
            Toast.makeText(this, FAVORITADO, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, DESFAVORITADO, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}