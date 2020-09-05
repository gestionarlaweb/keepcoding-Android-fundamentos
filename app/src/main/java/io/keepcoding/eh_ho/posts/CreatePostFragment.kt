package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.LoadingDialogFragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.*
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.topics.TAG_LOADING_DIALOG
import kotlinx.android.synthetic.main.fragment_create_post.*
import kotlinx.android.synthetic.main.fragment_create_topic.*
import kotlinx.android.synthetic.main.item_topic.*
import java.lang.IllegalArgumentException

class CreatePostFragment(topicId: String = "", title: String = "") : Fragment() {

    var interactionListener: CreatePostInteractionListener? = null

    val loadingDialogFragment: LoadingDialogFragment by lazy {
        val message = "Creating new post"
        LoadingDialogFragment.newInstance(message)
    }

    var topicId = topicId
    var topicTitle = title

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CreatePostInteractionListener)
            this.interactionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${CreatePostInteractionListener::class.java.canonicalName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_create_post)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Si se pulsa el boton enviar Post
        //if(item.itemId == R.id.action_send_post)
        //o  temabién así
        when (item.itemId){
            R.id.action_send_post -> createPost()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        labelTitle.text = topicTitle
    }

    private fun createPost() {
        // Validar el campo inputPost del formulario
        if (isFormValid()){
            // Si es valido crear el Post
            newPost()
            } else {
            // Error
            showErrors()
        }
    }

    private fun newPost() {
        enableLoadingDialog()
        val model = CreatePostModel(
            topicId.toInt(),
            inputPost.text.toString()
        )

        context?.let {
            PostsRepo.addPost(  //Posts
                it.applicationContext,
                model,
                {
                    enableLoadingDialog(false)
                    // Informar a la actividad
                    interactionListener?.onPostCreated()
                },
                {
                    enableLoadingDialog(false)
                    handleError(it)
                }
            )
        }
    }

    private fun enableLoadingDialog(enabled: Boolean = true) {
        if (enabled)
           loadingDialogFragment.show(childFragmentManager, TAG_LOADING_DIALOG)
        else
           loadingDialogFragment.dismiss()
    }

    private fun handleError(error: RequestError) {
        val message =
            if (error.messageResId != null)
                getString(error.messageResId)
            else error.message ?: getString(R.string.error_default)

        Snackbar.make(container, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showErrors() {
        if (inputPost.text.isEmpty())
            inputPost.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputPost.text.isNotEmpty()

    interface CreatePostInteractionListener {
        fun onPostCreated()
    }
}