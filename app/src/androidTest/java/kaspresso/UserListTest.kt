package kaspresso

import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kaspresso.screen.UserListScreen
import kaspresso.tools.annotation.TestCase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListTest : KTestCase() {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val server = MockWebServer()
    private val fakeServerResponse = """
{
  "users": [
    {
      "name": "Ryan Gosling",
      "address": "123 Main St",
      "phoneNumber": "555-1234"
    },
    {
      "name": "Andrey Shtrich",
      "address": "456 Elm St",
      "phoneNumber": "555-5678"
    },
    {
      "name": "Kostya Tubic",
      "address": "789 Oak St",
      "phoneNumber": "555-9012"
    },
    {
      "name": "Oleg Skuff",
      "address": "1 Usova",
      "phoneNumber": "555-9012"
    },
    {
      "name": "Ann Altushka",
      "address": "789 Oak St",
      "phoneNumber": "555-9012"
    }
  ]
}
"""


    @Test
    @TestCase(name = "Test-1", description = "")
    fun checkClickableRecyclerItem() {
        run {

            step("Check if RecyclerItem clickable") {
                UserListScreen {
                    rvUsers.firstChild<UserListScreen.UserItemScreen> {
                        click()
                    }
                }
            }
            step("Check if name, image, date, sex are displayed") {

                UserListScreen {
                    imgProfile.isVisible()
                    tvName.isVisible()
                    tvDateBirthDay.isVisible()
                    tvSex.isVisible()
                }
            }
        }
    }

    @Test
    @TestCase(name = "Test-2", description = "")
    fun checkLoadAndDisplayedList() {
        before {
            server.start()
            enqueueFakeData()

        }.after {
            server.shutdown()
        }.run {

            step("") {
                val request = server.takeRequest()
            }

        }
    }

    data class User(val name: String, val address: String, val phoneNumber: String)

    private fun enqueueFakeData() {
        val fakeResponse = MockResponse()
            .setResponseCode(200)
            .setBody(fakeServerResponse) // Здесь fakeServerResponse - это ваш фэйковый ответ с данными
        server.enqueue(fakeResponse)
    }



}