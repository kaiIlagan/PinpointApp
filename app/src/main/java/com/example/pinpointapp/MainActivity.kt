package com.example.pinpointapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.pinpointapp.domain.model.Person
import com.example.pinpointapp.keys.Keys.API_KEY
import com.example.pinpointapp.keys.Keys.APP_ID
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Backendless.initApp(this, APP_ID, API_KEY)

        //saveAPerson((floor(Math.random() * 100)).toInt(), "Amber");
        //retrieveAPerson();
        updateAPerson()

    }
}

 fun saveAPerson(age: Int, name: String) {
    val objToSave = Person(age, name)

    Backendless.Data.of(Person::class.java).save(objToSave, object: AsyncCallback<Person>{
        override fun handleResponse(person: Person )
        {
            // new Person instance has been saved
        }

        override fun handleFault(fault: BackendlessFault )
        {
            // an error has occurred, the error code can be retrieved with fault.getCode()
        }
    });
}

fun retrieveAPerson() {
    var objectRetrieved: Person
    val tag = "RetrievalPerson"

    Backendless.Data.of(Person::class.java).findFirst(object: AsyncCallback<Person>{
        override fun handleResponse(response: Person?) {
            if (response != null) {
                objectRetrieved = response
                Log.d(tag, "Successfully received Person from Backendless")
                objectRetrieved.name?.let { Log.d(tag, it) }
                objectRetrieved.age?.let { Log.d(tag, it.toString())}
            } else {
                Log.e(tag, "Request went through, did not receive a response")
            }
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }

    });
}

fun updateAPerson() {
    var person: Person;
    val tag = "UpdatePerson";

    Backendless.Data.of(Person::class.java).findFirst(object: AsyncCallback<Person>{
        override fun handleResponse(response: Person?) {
            if (response != null) {
                person = response
                person.age = (Math.random() * 100).roundToInt()

                //Save Person
                Backendless.Data.of(Person::class.java).save(person, object: AsyncCallback<Person>{
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
            };
        }

        override fun handleFault(fault: BackendlessFault?) {
            Log.e(tag, fault.toString())
        }

    });
}