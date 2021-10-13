package com.example.github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.github.models.GithubDetailViewModel
import com.squareup.picasso.Picasso
import android.content.Intent
import android.net.Uri
import android.widget.Button
import com.example.github.models.RepositoryEntity


class DetailActivity : AppCompatActivity() {

    lateinit var avatar: ImageView
    lateinit var author: TextView
    lateinit var description: TextView
    lateinit var language: TextView
    lateinit var createdAt: TextView
    lateinit var updatedAt: TextView
    lateinit var btnBrowser: Button

    private lateinit var model: GithubDetailViewModel
    private lateinit var data: RepositoryEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this).get(GithubDetailViewModel::class.java)

        setContentView(R.layout.activity_detail)

        avatar = findViewById(R.id.detailAvatar)
        author = findViewById(R.id.detailAuthor)
        description = findViewById(R.id.detailDescription)
        language = findViewById(R.id.detailLanguage)
        createdAt = findViewById(R.id.detailCreatedAt)
        updatedAt = findViewById(R.id.detailUpdatedAt)
        btnBrowser = findViewById(R.id.btnBrowser)

        btnBrowser.setOnClickListener {
            val url = data.htmlUrl
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        updateUi()
    }

    private fun updateUi() {
        if (intent.hasExtra("owner") && intent.hasExtra("repo")) {
            val owner = intent.getStringExtra("owner")
            val repo = intent.getStringExtra("repo")
            model.getDetail(owner!!, repo!!)

            model.liveDataDetail.observe(this, { item ->
                data = item
                Log.d("data", item.toString())
                Picasso.get().load(item.owner?.avatarUrl).into(avatar)
                description.text = item.description
                author.text = item.owner?.login
                language.text = item.language
                createdAt.text = item.createdAt
                updatedAt.text = item.updatedAt
            })

        }
    }
}