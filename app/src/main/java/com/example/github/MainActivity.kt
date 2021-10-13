package com.example.github

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.models.GithubViewModel
import com.example.github.models.RepositoryEntity
import java.lang.Exception


class MainActivity : AppCompatActivity(), GithubListAdapter.OnRepoClickListener {

    private lateinit var model: GithubViewModel

    lateinit var input: EditText

    private lateinit var repoListView: RecyclerView

    private lateinit var sortBy: Spinner

    private var data: List<RepositoryEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this).get(GithubViewModel::class.java)
        setContentView(R.layout.activity_main)
        initListView()
        initSearchInput()
        initSortBy()
        updateUi()
    }

    private fun updateUi() {
        model.data.observe(this, { item ->
            Log.d("data", item.toString())
            repoListView.adapter = GithubListAdapter(item, this)
        })
    }

    private fun initSearchInput() {
        input = findViewById(R.id.inputText)
        input.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                model.getGithubRepository(input.text.toString(), null)
                try {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    Log.d("Error", "Error hiding keyboard")
                }
                return@OnKeyListener true
            }
            false
        })
    }

    private fun initListView() {
        repoListView = findViewById(R.id.repoList)
        repoListView.layoutManager = LinearLayoutManager(this)
        repoListView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        model.data.observe(this, { item ->
            data = item
            Log.d("data", item.toString())
            val adapter = GithubListAdapter(item, this)
            repoListView.adapter = adapter
        })
    }

    private val SORT_BY = arrayOf(
        "-- Sort by --", "Stars", "Forks", "Updated at"
    )

    private fun initSortBy() {
        val convertMap = mapOf<String, String>("Stars" to "stars", "Forks" to "forks", "Updated at" to "updated_at")
        // Spinner
        val adapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, SORT_BY
        )
        sortBy = findViewById(R.id.sortBy)
        sortBy.adapter = adapter
        sortBy.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    model.getGithubRepository(input.text.toString(), convertMap[parent?.getItemAtPosition(position).toString()])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    override fun onItemClick(position: Int) {
        val selectedData = data[position]
        Log.d("selected data", selectedData.fullName!!)
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("owner", selectedData.owner?.login)
        intent.putExtra("repo", selectedData.name)
        startActivity(intent)
    }


}