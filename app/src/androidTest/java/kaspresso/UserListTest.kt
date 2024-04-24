package kaspresso

import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testtask.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kaspresso.screen.UserListScreen
import kaspresso.tools.annotation.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.Request
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListTest : KTestCase() {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


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
    @TestCase(name = "Test-2", description = "Check if name, address and phone are displayed correctly")
    fun checkLoadAndDisplayedList() {
        run {
            checkUser(
                User("Ryan Gosling ", "123 Main St", "555-1234"),
                User("Andrey Shtrich ", "456 Elm St", "555-5678"),
                User("Kostya Tubic ", "789 Oak St", "555-9012"),
                User("Oleg Skuff ", "1 Usova", "555-9012"),
                User("Ann Altushka ", "789 Oak St", "555-9012"),

            )
        }

    }

    class User(val name: String, val address: String, val phoneNumber: String)

    private fun checkUser(vararg users: User) {
        users.forEachIndexed { index, user ->
            UserListScreen {
                rvUsers {
                    childAt<UserListScreen.UserItemScreen>(index) {
                        tvName {
                            isDisplayed()
                            hasText(user.name)
                        }
                        tvAddress {
                            isDisplayed()
                            hasText(user.address)
                        }
                        tvPhoneNumber {
                            isDisplayed()
                            hasText(user.phoneNumber)
                        }
                    }
                }
            }
        }
    }


}