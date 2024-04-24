package kaspresso.screen

import android.view.View
import com.example.testtask.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object UserListScreen : KScreen<UserListScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val rvUsers = KRecyclerView(
        builder = {withId(R.id.rvUsers)},
        itemTypeBuilder = {itemType(::UserItemScreen)}
    )

    val imgProfile = KImageView {withId(R.id.imgInfoProfile)}
    val tvName = KTextView {withId(R.id.tvInfoName)}
    val tvDateBirthDay = KTextView{withId(R.id.tvDateBirthday)}
    val tvSex = KTextView{withId(R.id.tvSex)}

    class UserItemScreen(parent: Matcher<View>) : KRecyclerItem<UserItemScreen>(parent) {
        val tvName = KTextView {withId(R.id.tvName)}
        val tvAddress = KTextView {withId(R.id.tvAddress)}
        val tvPhoneNumber = KTextView {withId(R.id.tvPhoneNumber)}
    }



}