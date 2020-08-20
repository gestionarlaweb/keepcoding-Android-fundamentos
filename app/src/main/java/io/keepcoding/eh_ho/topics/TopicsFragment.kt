package io.keepcoding.eh_ho.topics

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_topics.*
import kotlinx.android.synthetic.main.fragment_topics.viewLoading

class TopicsFragment : Fragment() {

    var topicsInteractionListener: TopicsInteractionListener? = null

    private val topicsAdapter: TopicsAdapter by lazy {
        val adapter = TopicsAdapter {
            this.topicsInteractionListener?.onShowPosts(it)
        }
        adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TopicsInteractionListener)
            topicsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${TopicsInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_topics)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCreate.setOnClickListener {
            this.topicsInteractionListener?.onCreateTopic()
        }

        topicsAdapter.setTopics(TopicsRepo.topics)

        listTopics.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listTopics.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listTopics.adapter = topicsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_topics, menu)
        super.onCreateOptionsMenu(menu, inflater)

        enableLoading(true)

    }

    override fun onResume() {
        super.onResume()
        loadTopics()

    }

    private fun loadTopics() {

        context?.let {
            TopicsRepo
                .getTopics(it.applicationContext,
                    {
                        topicsAdapter.setTopics(it)
                        // Loading parado
                         enableLoading(false)
                    },
                    {
                       // TODO: Manejo de errores
                        // Al llegar aquí te vas a la vista de error
                    }
                )
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> this.topicsInteractionListener?.onLogout()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        topicsInteractionListener = null
    }

    interface TopicsInteractionListener {
        fun onCreateTopic()
        fun onLogout()
        fun onShowPosts(topic: Topic)
    }

    // Para el ProgressBar
    private fun enableLoading(enabled: Boolean = true) {
        if (enabled) {
            //fragmentContainer.visibility = View.INVISIBLE
            fragmentContainerTopics.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainerTopics.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }

}