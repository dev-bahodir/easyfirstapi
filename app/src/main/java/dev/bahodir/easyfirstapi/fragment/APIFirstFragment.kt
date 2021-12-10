package dev.bahodir.easyfirstapi.fragment

import android.content.ContentValues
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dev.bahodir.easyfirstapi.EasyAPI
import dev.bahodir.easyfirstapi.R
import dev.bahodir.easyfirstapi.adapter.RV
import dev.bahodir.easyfirstapi.databinding.FragmentAPIFirstBinding
import dev.bahodir.easyfirstapi.network.NetworkHelper
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [APIFirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class APIFirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentAPIFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var rv: RV
    private lateinit var list: MutableList<EasyAPI>
    private lateinit var requestQueue: RequestQueue
    private lateinit var networkHelper: NetworkHelper
    private val TAG = "tag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAPIFirstBinding.inflate(inflater, container, false)

        requestQueue = Volley.newRequestQueue(requireContext())
        networkHelper = NetworkHelper(requireContext())
        rv = RV()
        binding.rv.adapter = rv

        if (networkHelper.isNetworkConnected()) {

            val jsonArrayRequest =
                JsonArrayRequest(Request.Method.GET,
                    "https://api.github.com/users",
                    null,
                    { response ->
                        list = Gson().fromJson(
                            response.toString(),
                            object : TypeToken<List<EasyAPI>>() {}.type
                        )
                        rv.submitList(list)
                    }
                ) { error ->
                    Log.d(ContentValues.TAG,
                        "onErrorResponse: ${error?.message}")
                }

            requestQueue.add(jsonArrayRequest)
        }
        else {
            Toast.makeText(requireContext(), "Not connected", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment APIFirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            APIFirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}