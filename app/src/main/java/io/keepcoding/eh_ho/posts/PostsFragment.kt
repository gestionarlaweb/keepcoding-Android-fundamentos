package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.login.SignInFragment
import kotlinx.android.synthetic.main.fragment_posts.*


class PostsFragment(postId: String = "") : Fragment() {

    var id = postId

    var postsInteractionListener: PostsInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is PostsInteractionListener)
            postsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${PostsInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return container?.inflate(R.layout.fragment_posts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crear el Adaptador, Avísame cuando le hayas dado click a algo en el PostAdapter()
        val adapter = PostsAdapter {
            //  Log.d(PostsActivity::class.java.canonicalName, it.author)
            // Al darle a un elemento de la lista
           // goToPostsDetail(it)
            this.postsInteractionListener?.onDetailPost(it)

        }
        // Al darle al boton de creación de POST   NO ME FUNCIONA !!!!
        buttonCreatePost.setOnClickListener {
            this.postsInteractionListener?.onCreatePost()
        }

        adapter.setPosts(PostsRepo.posts)

        listPosts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listPosts.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            PostsRepo
                .getPosts(it.applicationContext,
                    {
                        (listPosts.adapter as PostsAdapter).setPosts(it)
                    },
                    {}
                )
        }

    }

    override fun onDetach() {
        super.onDetach()
        postsInteractionListener = null
    }

    interface PostsInteractionListener {
        // Click a un elemento de la lista
        fun onDetailPost(post: Post)
        // Click al boton +
        fun onCreatePost()
    }

}