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
const val EXTRA_TOPIC_TITLE = "TOPIC_TITLE"
const val TRANSACTION_CREATE_POST = "create_post"

class PostsActivity : AppCompatActivity(), PostsFragment.PostsInteractionListener, CreatePostFragment.CreatePostInteractionListener {

    var topicID: String = ""
    var topicTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        this.topicID = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        this.topicTitle = intent.getStringExtra(EXTRA_TOPIC_TITLE) ?: ""

        // Asociar el Fragmento activity_post.xml
        if (isFirsTimeCreated(savedInstanceState))
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_posts, PostsFragment(this.topicID))
                .commit()

    }

    override fun onCreatePost() {
        // Que hacer cuando se cree el nuevo Post
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_posts, CreatePostFragment(topicID, topicTitle))
            .addToBackStack(TRANSACTION_CREATE_POST)
            .commit()
    }

    override fun onPostCreated() {
        supportFragmentManager.popBackStack()
    }


     // Ir a la actividad PostActivityDetail.kt
    private fun goToPostsDetail(post: Post) {
        val intent = Intent(this, PostsActivityDetail::class.java)

       intent.putExtra(EXTRA_POST_ID, post.id)
        startActivity(intent)
    }

    override fun onDetailPost(post: Post) {
        // Que hacer cuando se muestre el Post seleccionado
      //  goToPostsDetail(post)
    }


}




