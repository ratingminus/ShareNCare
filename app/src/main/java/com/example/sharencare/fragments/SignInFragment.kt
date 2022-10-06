package com.example.sharencare.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.sharencare.MainActivity
import com.example.sharencare.R
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var signIn_btn_sign_in_fragment :AppCompatButton
    private lateinit var email_editText_signIn_fragment : EditText
    private lateinit var password_editText_signIn_fragment : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser!=null)
        {
            startActivity(Intent(context,MainActivity::class.java))
            this.activity?.finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        signIn_btn_sign_in_fragment = view.findViewById(R.id.signIn_btn_sign_in_fragment)
        email_editText_signIn_fragment = view.findViewById(R.id.email_editText_signIn_fragment)
        password_editText_signIn_fragment = view.findViewById(R.id.password_editText_signIn_fragment)

        signIn_btn_sign_in_fragment.setOnClickListener {
            val email = email_editText_signIn_fragment.text.toString()
            val password = password_editText_signIn_fragment.text.toString()
            when{
                TextUtils.isEmpty(email)-> Toast.makeText(context,"Email is required", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(password)-> Toast.makeText(context,"Password Name is required",
                    Toast.LENGTH_LONG).show()

                else->{
                    val userAuth : FirebaseAuth = FirebaseAuth.getInstance()
                    userAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task->
                        if(task.isSuccessful)
                        {
                            startActivity(Intent(context,MainActivity::class.java))
                            this.activity?.finish()
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(context,"Error : $message", Toast.LENGTH_LONG).show()
                            userAuth.signOut()
                        }
                    }
                }
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}