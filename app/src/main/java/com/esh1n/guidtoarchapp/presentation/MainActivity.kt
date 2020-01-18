package com.esh1n.guidtoarchapp.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esh1n.guidtoarchapp.R
import com.esh1n.guidtoarchapp.data.Word
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    private var adapter: WordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = WordListAdapter(this)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
        observeData()
    }

    private fun observeData() {
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter?.setWords(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = Word(it.getStringExtra(NewWordActivity.EXTRA_REPLY), "human")
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        const val newWordActivityRequestCode = 1
    }
}
