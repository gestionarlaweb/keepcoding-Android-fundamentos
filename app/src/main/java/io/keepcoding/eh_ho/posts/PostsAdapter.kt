package io.keepcoding.eh_ho.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.topics.TopicsAdapter
import kotlinx.android.synthetic.main.item_posts.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostHolder>() {

   private val posts = mutableListOf<Post>()

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
            itemView.labelPost.text = field?.author
        }
    }


}