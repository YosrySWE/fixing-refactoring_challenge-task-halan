package com.example.halanchallenge

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.halanchallenge.features.MainActivity
import com.example.halanchallenge.features.list.ProductsAdapter
import com.example.halanchallenge.features.list.ProductsListFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
//    @get:Rule
//    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigate_to_details() {
//        activity.scenario.moveToState(Lifecycle.State.CREATED)
        val scenario = launchFragmentInContainer<ProductsListFragment>(
            initialState = Lifecycle.State.INITIALIZED
        )
        // EventFragment has gone through onAttach(), but not onCreate().
        // Verify the initial state.
        scenario.moveToState(Lifecycle.State.RESUMED)
        // EventFragment moves to CREATED -> STARTED -> RESUMED.
        //
        // scenario.moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.products_list_rv))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<ProductsAdapter.ViewHolder>(1, click()))
        onView(withId(R.id.product_details_title_tv)).check(matches(isDisplayed()))
    }
}