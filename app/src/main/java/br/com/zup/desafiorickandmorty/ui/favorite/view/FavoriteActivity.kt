package br.com.zup.desafiorickandmorty.ui.favorite.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.zup.desafiorickandmorty.data.model.Result
import br.com.zup.desafiorickandmorty.databinding.ActivityFavoriteBinding
import br.com.zup.desafiorickandmorty.ui.FAVORITOS
import br.com.zup.desafiorickandmorty.ui.KEY_RICK_AND_MORTY
import br.com.zup.desafiorickandmorty.ui.detail.view.DetailActivity
import br.com.zup.desafiorickandmorty.ui.favorite.viewmodel.FavoriteViewModel
import br.com.zup.desafiorickandmorty.ui.viewstate.ViewState

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(arrayListOf(), this::goToRickAndMortyDetail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayNaAppBar()
        setUpRvPersonageList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPersonageFavorited()
        initObserver()
    }

    private fun initObserver() {
        viewModel.personageListFavoriteState.observe(this) {
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

    private fun goToRickAndMortyDetail(result: Result) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_RICK_AND_MORTY, result)
        }
        startActivity(intent)
    }

    private fun setUpRvPersonageList() {
        binding.rvFavoriteList.adapter = adapter
        binding.rvFavoriteList.layoutManager = GridLayoutManager(this, 2)
    }

    private fun displayNaAppBar() {
        supportActionBar?.title = FAVORITOS
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}