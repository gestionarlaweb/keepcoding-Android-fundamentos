package io.keepcoding.eh_ho.topics

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.*
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.login.LoginActivity
import io.keepcoding.eh_ho.posts.EXTRA_TOPIC_ID
import io.keepcoding.eh_ho.posts.PostsActivity

const val TRANSACTION_CREATE_TOPIC = "create_topic"

class TopicsActivity : AppCompatActivity(),
    TopicsFragment.TopicsInteractionListener,
    CreateTopicFragment.CreateTopicInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        Toast.makeText(this, "Estoy en la carga de Topics !", Toast.LENGTH_SHORT).show()

        if (isFirsTimeCreated(savedInstanceState))
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, TopicsFragment())
                .commit()
    }

    // Al pulsar en un topic de la lista
    private fun goToPosts(topic: Topic) {

        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(EXTRA_TOPIC_ID, topic.id)
        startActivity(intent)
    }

    // Boton crear topic
    override fun onCreateTopic() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CreateTopicFragment())
            .addToBackStack(TRANSACTION_CREATE_TOPIC)
            .commit()
    }

    override fun onShowPosts(topic: Topic) {
        // Al pulsar en un topic de la lista
        goToPosts(topic)
    }

    override fun onTopicCreated() {
        supportFragmentManager.popBackStack()

    }

    override fun onLogout() {
        //Borrar datos
        UserRepo.logout(this.applicationContext)

        //Ir a actividad inicial
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}