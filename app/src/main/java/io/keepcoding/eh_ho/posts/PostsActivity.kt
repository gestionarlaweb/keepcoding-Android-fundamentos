package io.keepcoding.eh_ho.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.keepcoding.eh_ho.R
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

        val topicId: String = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        val topic: Topic? = TopicsRepo.getTopic(topicId)

        topic?.let {
            labelTitle.text = it.title
        }

    }
}