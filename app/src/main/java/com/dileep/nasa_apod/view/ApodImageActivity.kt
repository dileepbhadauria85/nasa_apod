package com.dileep.nasa_apod.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dileep.nasa_apod.R
import com.dileep.nasa_apod.core.ApodImageApplication
import com.dileep.nasa_apod.databinding.ActivityMainBinding
import com.dileep.nasa_apod.db.ApodImageData
import com.dileep.nasa_apod.repository.ApodImageRepository
import com.dileep.nasa_apod.viewmodel.ApodImageViewModel


class ApodImageActivity : AppCompatActivity() {
    private val viewModel: ApodImageViewModel by viewModels {
        ApodImageViewModel.ApodImageViewModelFactory((application as ApodImageApplication).repository)
    }
    private lateinit var context: Context
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        viewModel.getApodData()
        viewModel.responseData.observe(this, {
            when (it.responseStatus) {
                ApodImageRepository.ResponseStatus.LAST_AVAILABLE_DATA -> {
                    showAlert(context.getString(R.string.last_available_data_error))
                    populateUi(it.response)
                }
                ApodImageRepository.ResponseStatus.NONE -> populateUi(it.response)
                ApodImageRepository.ResponseStatus.OFFLINE_DATA_NA -> {
                    binding.apodTitleTextView.text =
                        context.getString(R.string.no_available_data_error_title)
                    binding.apodExpTextView.text =
                        context.getString(R.string.no_available_data_error_msg)
                    binding.apodImageView.adjustViewBounds = false
                    binding.apodImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_connection_error
                        )
                    )
                }
                ApodImageRepository.ResponseStatus.API_ERROR -> {
                    binding.apodTitleTextView.text = context.getString(R.string.api_error_title)
                    binding.apodExpTextView.text = context.getString(R.string.api_error_msg)
                    binding.apodImageView.adjustViewBounds = false
                    binding.apodImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_broken_image
                        )
                    )
                }
            }
        })

        viewModel.showLoading.observe(this, {
            if (it) {
                binding.uiProgressBar.visibility = View.VISIBLE
            } else {
                binding.uiProgressBar.visibility = View.GONE
            }
        })
    }

    private fun populateUi(apodImageData: ApodImageData) {
        binding.apodTitleTextView.text = apodImageData.title
        binding.apodExpTextView.text = apodImageData.explanation
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(apodImageData.imageUrl)
            .dontAnimate()
            .error(R.drawable.ic_broken_image)
            .placeholder(R.drawable.loading_animation)
            .apply(requestOptions)
            .into(binding.apodImageView)
        binding.apodImageView.adjustViewBounds = true
    }

    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(context.getString(R.string.app_name))
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton(context.getString(R.string.alert_ok)) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}