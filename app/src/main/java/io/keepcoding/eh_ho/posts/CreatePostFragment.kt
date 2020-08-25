package io.keepcoding.eh_ho.posts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_create_post.*

class CreatePostFragment : Fragment() {

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

    fun createPost() {
        // Validar el campo inputPost del formulario
        if (isFormValid()){
            // Si es valido crear el Post
        }else {
            // Error
            showErrors()
        }
    }

    private fun showErrors() {
        if (inputPost.text.isEmpty())
            inputPost.error = getString(R.string.error_empty)
    }

    private fun isFormValid() = inputPost.text.isNotEmpty()
}