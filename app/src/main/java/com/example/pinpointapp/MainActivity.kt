package com.example.pinpointapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.pinpointapp.domain.model.Person
import com.example.pinpointapp.keys.Keys.API_KEY
import com.example.pinpointapp.keys.Keys.APP_ID
import kotlin.math.floor
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Backendless.initApp(this, APP_ID, API_KEY)

        //saveAPerson((floor(Math.random() * 100)).toInt(), "Kai")
        //retrieveAPerson()
        //updateAPerson()
        //deleteFirstPerson()
        //registerUser("dummyuser@backendless.com", "supe3rs3cre3t")
        //loginUser("james.bond@mi6.co.uk","supe3rs3cre3t" )

    }
}


fun loginUser(email: String, password: String) {
    val tag = "UserLogin"

    Backendless.UserService.login(email, password, object : AsyncCallback<BackendlessUser> {
        override fun handleResponse(response: BackendlessUser?) {
            Log.d(tag, "Successfully logged in User")
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }

    })

}

fun registerUser(email: String, password: String) {
    val tag = "UserRegistration"
    val user = BackendlessUser()
    user.email = email
    user.password = password


    Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser> {
        override fun handleResponse(response: BackendlessUser?) {
            Log.d(tag, "Successfully registered User")
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }

    })
}


fun saveAPerson(age: Int, name: String) {
    val objToSave = Person(age, name)

    Backendless.Data.of(Person::class.java).save(objToSave, object : AsyncCallback<Person> {
        override fun handleResponse(person: Person) {
            // new Person instance has been saved
        }

        override fun handleFault(fault: BackendlessFault) {
            // an error has occurred, the error code can be retrieved with fault.getCode()
        }
    })
}

fun retrieveAPerson() {
    var objectRetrieved: Person
    val tag = "RetrievalPerson"

    Backendless.Data.of(Person::class.java).findFirst(object : AsyncCallback<Person> {
        override fun handleResponse(response: Person?) {
            if (response != null) {
                objectRetrieved = response
                Log.d(tag, "Successfully received Person from Backendless")
                objectRetrieved.name?.let { Log.d(tag, it) }
                objectRetrieved.age?.let { Log.d(tag, it.toString()) }
            } else {
                Log.e(tag, "Request went through, did not receive a response")
            }
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }

    })
}

fun updateAPerson() {
    var person: Person
    val tag = "UpdatePerson"

    Backendless.Data.of(Person::class.java).findFirst(object : AsyncCallback<Person> {
        override fun handleResponse(response: Person?) {
            if (response != null) {
                person = response
                person.age = (Math.random() * 100).roundToInt()

                //Save Person
                Backendless.Data.of(Person::class.java)
                    .save(person, object : AsyncCallback<Person> {
                        override fun handleResponse(response: Person?) {
                            Log.d(tag, "Person has been saved and updated!")
                            person.name?.let { Log.d(tag, it) }
                            person.age?.let { Log.d(tag, it.toString()) }
                        }

                        override fun handleFault(fault: BackendlessFault?) {
                            Log.e(tag, fault.toString())
                        }

                    })
            } else {
                Log.e(tag, "Received a response, but was null")
            }
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }

    })
}

fun deleteFirstPerson() {
    var person: Person
    val tag = "DeleteFirstPerson"

    Backendless.Data.of(Person::class.java).findFirst(object : AsyncCallback<Person> {
        override fun handleResponse(response: Person?) {
            if (response != null) {
                person = response
                Backendless.Data.of(Person::class.java)
                    .remove(person, object : AsyncCallback<Long> {
                        override fun handleResponse(response: Long?) {
                            Log.d(tag, "Successfully removed " + person.name + " " + person.age)
                        }

                        override fun handleFault(fault: BackendlessFault?) {
                            Log.e(tag, fault.toString())
                        }

                    })
            } else {
                Log.d(tag, "Got a response, however it was null, database may be empty")
            }
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }
    })
}
