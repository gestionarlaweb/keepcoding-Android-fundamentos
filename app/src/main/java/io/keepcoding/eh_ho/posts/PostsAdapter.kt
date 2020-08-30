package io.keepcoding.eh_ho.posts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.topics.TopicsAdapter
import kotlinx.android.synthetic.main.activity_posts_detail.view.*
import kotlinx.android.synthetic.main.item_posts.view.*

class PostsAdapter(val postClickListener: ((Post) -> Unit) ? = null) : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

   private val posts = mutableListOf<Post>()

    // Para la escucha del Click
    private val listener : ((View) -> Unit) = {
        val tag: Post = it.tag as Post
        postClickListener?.invoke(tag)
    }

    // Cuantos elementos va a pintar
    override fun getItemCount(): Int {
        return  posts.size
    }

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): PostHolder {
        val view =
            LayoutInflater.from(list.context).inflate(R.layout.item_posts, list, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.post = post
        // Al darle click
        holder.itemView.setOnClickListener(listener)
        /*{
            Log.d(this::class.java.canonicalName, "Has dado al click")
        }
         */

    }

fun setPosts(posts: List<Post>){
    this.posts.clear()
    this.posts.addAll(posts)
    notifyDataSetChanged()
}

    inner class PostHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var post: Post? = null
        set(value) {
            field = value
            itemView.tag = field
            field?.let {
                itemView.labelPost.text = it.author
                itemView.inputContent.text = it.contenido // contenido del POST "cooked" !!!
                itemView.inputDate.text = it.fecha // "created_at"
            }

        }
    }


}