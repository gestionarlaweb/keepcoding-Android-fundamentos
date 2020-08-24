package io.keepcoding.eh_ho.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.isFirsTimeCreated
import kotlinx.android.synthetic.main.activity_posts.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        Toast.makeText(this, "Al pulsar en un topìc de la lista estoy aquí, en PostActivity !", Toast.LENGTH_SHORT).show()

       // val topicId: String = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
       // val topic: Topic? = TopicsRepo.getTopic(topicId)

        /*
         topic?.let {
            labelTitle.text = it.title
        }
         */


        // Imprimir en la consola
        //Log.d(PostsActivity::class.simpleName, PostsRepo.posts.toString())

        // Gracias a    import kotlinx.android.synthetic.main.activity_posts.*
        // llamamos directamente al ID de la vista list_posts
        //val list: RecyclerView = findViewById(R.id.list_posts)

        // Crear el Adaptador, Avísame cuando le hayas dado click a algo en el PostAdapter()
        val adapter = PostsAdapter {
          //  Log.d(PostsActivity::class.java.canonicalName, it.author)
            goToPostsDetail(it)
        }
        adapter.setPosts(PostsRepo.posts)

        listPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listPosts.adapter = adapter
    }

    // Ir a la actividad PostActivityDetail.kt
    private fun goToPostsDetail(post: Post) {
        val intent = Intent(this, PostsActivityDetail::class.java)

       intent.putExtra(EXTRA_POST_ID, post.id)
        startActivity(intent)
    }
}