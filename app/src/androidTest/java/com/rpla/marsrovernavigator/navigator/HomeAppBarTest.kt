package com.rpla.marsrovernavigator.navigator

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.ui.common.HomeAppBar
import com.rpla.marsrovernavigator.ui.theme.MarsRoverNavigatorTheme
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test

class HomeAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun given_title_is_shown_in_home_app_bar() {
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                HomeAppBar(
                    slotComposable = {
                        Text(
                            text = stringResource(R.string.app_name),
                        )
                    },
                    modifier = Modifier,
                    contextualAction = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(context.getString(R.string.home_app_bar_test_tag)).assertIsDisplayed()
    }

    @Test
    fun given_filter_icon_is_shown_in_home_app_bar() {
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                HomeAppBar(
                    slotComposable = { Text(text = context.getString(R.string.app_name)) },
                    modifier = Modifier,
                    contextualAction = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(context.getString(R.string.home_app_bar_json_data_test_tag)).assertIsDisplayed()
    }

    @Test
    fun given_filter_button_is_clicked_toast_message_appears() {
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                HomeAppBar(
                    slotComposable = { Text(text = stringResource(R.string.app_name)) },
                    modifier = Modifier,
                    contextualAction = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(context.getString(R.string.home_app_bar_json_data_test_tag)).performClick()
    }

    @Test
    fun given_filter_button_is_clicked_lambda_function_is_called() {
        var contextualActionClicked = false

        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                HomeAppBar(
                    slotComposable = { Text(text = stringResource(R.string.app_name)) },
                    modifier = Modifier,
                    contextualAction = { contextualActionClicked = true },
                )
            }
        }

        composeTestRule.onNodeWithTag(context.getString(R.string.home_app_bar_json_data_test_tag)).performClick()

        assertThat(contextualActionClicked, `is`(true))
    }

    @Test
    fun top_app_bar_is_displayed_with_correct_title_text() {
        val title = context.getString(R.string.app_name)

        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                HomeAppBar(
                    slotComposable = { Text(text = stringResource(R.string.app_name)) },
                    modifier = Modifier,
                    contextualAction = {},
                )
            }
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }

    @Test
    fun top_app_bar_contains_title_and_filter_button_using_test_tags() {
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                HomeAppBar(
                    slotComposable = {
                        Text(
                            text = stringResource(R.string.app_name),
                            modifier = Modifier.testTag("HomeAppBarTitle"),
                        )
                    },
                    modifier = Modifier,
                    contextualAction = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(context.getString(R.string.home_app_bar_test_tag)).assertIsDisplayed()
        composeTestRule.onNodeWithTag("HomeAppBarTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag(context.getString(R.string.home_app_bar_json_data_test_tag)).assertIsDisplayed()
    }
}
