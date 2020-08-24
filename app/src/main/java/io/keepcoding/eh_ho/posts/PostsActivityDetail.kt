package io.keepcoding.eh_ho.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.data.PostsRepo
import kotlinx.android.synthetic.main.activity_posts_detail.*

const val EXTRA_POST_ID = "POST_ID"
class PostsActivityDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_detail)

        // Log.d(this::class.java.canonicalName, intent.getStringExtra("POST_ID"))

        val postId: String = intent.getStringExtra(EXTRA_POST_ID) ?: ""
        val post: Post? = PostsRepo.getPost(postId)

        // labelAuthor.text = post?.author
        // Mejor así
        post?.let {
            labelAuthor.text = it.author
        }


    }
}