package br.com.zup.desafiorickandmorty.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import br.com.zup.desafiorickandmorty.data.model.Result
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.zup.desafiorickandmorty.databinding.ActivityMainBinding
import br.com.zup.desafiorickandmorty.ui.KEY_RICK_AND_MORTY
import br.com.zup.desafiorickandmorty.ui.RICK_AND_MORTY
import br.com.zup.desafiorickandmorty.ui.detail.view.DetailActivity
import br.com.zup.desafiorickandmorty.ui.favorite.view.FavoriteActivity
import br.com.zup.desafiorickandmorty.ui.main.viewmodel.MainViewModel
import br.com.zup.desafiorickandmorty.ui.viewstate.ViewState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val adapter: MainAdapter by lazy {
        MainAdapter(arrayListOf(), this::goToRickAndMortyDetail)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpRvPersonageList()

        displayNaAppBar()

        binding.fabfavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllRickAndMorty()
        initObserver()
    }

    private fun initObserver() {
        viewModel.rickAndMortyListState.observe(this) {
            when (it) {
                is ViewState.Success -> {
                    adapter.updateRickAndMortyList(it.data.toMutableList())
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

    private fun setUpRvPersonageList() {
        binding.rvRickAndMortyList.adapter = adapter
        binding.rvRickAndMortyList.layoutManager = GridLayoutManager(this,2)
    }

    private fun goToRickAndMortyDetail(result: Result) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_RICK_AND_MORTY, result)
        }
        startActivity(intent)
    }

    private fun displayNaAppBar() {
        supportActionBar?.setTitle(RICK_AND_MORTY)
    }
}