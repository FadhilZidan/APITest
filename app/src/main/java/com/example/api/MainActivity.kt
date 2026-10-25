package com.example.api

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.api.data.model.User
import com.example.api.data.network.RetrofitClient
import com.example.api.data.repository.UserRepository
import com.example.api.databinding.ActivityMainBinding
import com.example.api.ui.UserAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userRepository by lazy { UserRepository(RetrofitClient.apiService) }
    private val userAdapter by lazy { UserAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvUsers.adapter = userAdapter
        binding.btnRetry.setOnClickListener {
            fetchUserData()
        }

        fetchUserData()
    }

    private fun fetchUserData() {
        showLoadingState()

        lifecycleScope.launch {
            try {
                val response = userRepository.getUsers()
                if (response.isSuccessful) {
                    val userList = response.body() ?: emptyList()
                    showSuccessState(userList)
                } else {
                    val errorMessage = getString(R.string.status_http_error, response.code())
                    showErrorState(errorMessage)
                }
            } catch (_: HttpException) {
                val errorMessage = getString(R.string.status_http_error, 500)
                showErrorState(errorMessage)
            } catch (_: IOException) {
                showErrorState(getString(R.string.status_net_error))
            } catch (e: Exception) {
                showErrorState(getString(R.string.status_unknown_error, e.message ?: "Unknown"))
            }
        }
    }

    private fun showLoadingState() {
        binding.tvStatusBanner.visibility = View.VISIBLE
        binding.tvStatusBanner.text = getString(R.string.status_loading)
        binding.layoutLoading.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
    }

    private fun showSuccessState(userList: List<User>) {
        binding.tvStatusBanner.visibility = View.VISIBLE
        binding.tvStatusBanner.text = getString(R.string.status_success, userList.size)
        binding.layoutLoading.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
        binding.layoutError.visibility = View.GONE
        userAdapter.submitList(userList)
    }

    private fun showErrorState(message: String) {
        binding.tvStatusBanner.visibility = View.GONE
        binding.layoutLoading.visibility = View.GONE
        binding.rvUsers.visibility = View.GONE
        binding.layoutError.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message
    }
}
