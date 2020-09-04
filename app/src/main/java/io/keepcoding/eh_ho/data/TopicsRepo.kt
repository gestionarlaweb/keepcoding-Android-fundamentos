package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R
import org.json.JSONObject
import java.nio.charset.Charset

object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()

    fun getTopics(
        context: Context,
        onSuccess: (List<Topic>) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getTopics(),
            null,
            {
                val list = Topic.parseTopicsList(it)
                onSuccess(list)
            },
            {
                it.printStackTrace()
                val requestError =
                    if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_no_internet)
                    else
                        RequestError(it)
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

    fun addTopic(
        context: Context,
        model: CreateTopicModel,
        onSuccess: (CreateTopicModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val username = UserRepo.getUsername(context)
        val request = PostRequest(
            Request.Method.POST,
            ApiRoutes.createTopic(),
            model.toJson(),
            {
                onSuccess(model)
            },
            {
                it.printStackTrace()

                val requestError =
                    if (it is ServerError && it.networkResponse.statusCode == 422) {
                        val body = String(it.networkResponse.data, Charsets.UTF_8)
                        val jsonError = JSONObject(body)
                        val errors = jsonError.getJSONArray("errors")
                        var errorMessage = ""

                        for (i in 0 until errors.length()) {
                            errorMessage += "${errors[i]} "
                        }

                        RequestError(it, message = errorMessage)

                    } else if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_no_internet)
                    else
                        RequestError(it)

                onError(requestError)
            },
            username
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

    fun getTopic(id: String): Topic? = topics.find { it.id == id }

}


// POSTS
object PostsRepo {
    val posts: MutableList<Post> = mutableListOf()

    fun getPosts(
        context: Context,
        onSuccess: (List<Post>) -> Unit,
        onError: (RequestError) -> Unit,
        postId: String = "1"
    ) {
        val request = JsonObjectRequest(  // En caso de Objetos
        //val request = JsonArrayRequest(     // En caso de Array
            Request.Method.GET,
            ApiRoutes.getPosts(postId),
            null,
            {
                // Exitoso y le mando la lista de POSTS
                val list = Post.parsePostsList(it)
                onSuccess(list)
            },
            {
                // Error
                it.printStackTrace()
                val requestError =
                    if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_no_internet)
                else
                        RequestError(it)
                onError(requestError)
            }
        )
        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)
    }

    // Obtener los datos
    fun getPost(id: String): Post? = posts.find { it.id == id }

    // Enviar Post
    fun addPost(title: String){
        posts.add(
            Post(
               // title = title
            )
        )
    }


}
