
package com.rpla.marsrovernavigator.navigator

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.rpla.marsrovernavigator.R
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.ui.grid.NavigatorUI
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorIntent
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorState
import com.rpla.marsrovernavigator.navigator.viewmodel.NavigatorViewModel
import com.rpla.marsrovernavigator.ui.theme.MarsRoverNavigatorTheme
import com.rpla.marsrovernavigator.utils.ComposeIdlingResource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigatorUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val navigatorViewModel = mockk<NavigatorViewModel>()

    private val composeIdlingResource = ComposeIdlingResource()

    @Before
    fun init() {
        composeTestRule.registerIdlingResource(composeIdlingResource)
        MockKAnnotations.init(this, true)

        every { navigatorViewModel.dispatchIntent(NavigatorIntent.BuildDefault) } returns Unit
        every { navigatorViewModel.createInitialState() } returns NavigatorState.Initial
        every { navigatorViewModel.state } returns MutableStateFlow(NavigatorState.Initial)

        every { navigatorViewModel.dispatchIntent(NavigatorIntent.BuildDefault) } answers {
            composeIdlingResource.isAppIdle(true)
        }
    }

    @After
    fun tearDown() {
        composeTestRule.unregisterIdlingResource(composeIdlingResource)
    }

    @Test
    fun grid_cells_and_commands_controller_should_be_shown() { 
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                NavigatorUI(
                    viewModel = navigatorViewModel,
                    innerPaddings = PaddingValues(56.dp)
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.navigator_grid_test_tag)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_process_commands_test_tag)).assertIsDisplayed()
    }

    @Test
    fun commands_controller_should_show_commands_introduced_by_user() {
        every { navigatorViewModel.state } returns MutableStateFlow(
            NavigatorState.UpdateRoverPosition(
                com.rpla.marsrovernavigator.navigator.ui.presentation.NavigatorUiState(
                    x = 2, y = 2, direction = "N"
                )
            )
        )

        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                NavigatorUI(
                    viewModel = navigatorViewModel,
                    innerPaddings = PaddingValues(56.dp)
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_move_position_test_tag)).performClick()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_rotate_right_test_tag)).performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_commands_text_box_test_tag)).assertIsDisplayed()
        composeTestRule.onNodeWithText("MR").assertIsDisplayed()
    }

    @Test
    fun commands_controller_should_delete_commands_introduced_when_reset_button_clicked() { 
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                NavigatorUI(
                    viewModel = navigatorViewModel,
                    innerPaddings = PaddingValues(56.dp)
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_move_position_test_tag)).performClick()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_reset_commands_test_tag)).performClick()
        composeTestRule.onNodeWithText("").assertIsDisplayed()
    }

    @Test
    fun commands_controller_should_process_command_when_done_button_clicked() {
        every { navigatorViewModel.state } returns MutableStateFlow(
            NavigatorState.UpdateRoverPosition(
                com.rpla.marsrovernavigator.navigator.ui.presentation.NavigatorUiState(
                    x = 2, y = 2, direction = "N"
                )
            )
        )

        every {
            navigatorViewModel.dispatchIntent(
                match { it is NavigatorIntent.ProcessCommands }
            )
        } answers {
            composeIdlingResource.isAppIdle(true)
        }

        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                NavigatorUI(
                    viewModel = navigatorViewModel,
                    innerPaddings = PaddingValues(56.dp)
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_move_position_test_tag)).performClick()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_process_commands_test_tag)).performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.loader_test_tag)).assertIsDisplayed()
    }


    @Test
    fun commands_controller_should_not_do_nothing_when_done_button_clicked_and_no_commands_introduced() { 
        composeTestRule.setContent {
            MarsRoverNavigatorTheme {
                NavigatorUI(
                    viewModel = navigatorViewModel,
                    innerPaddings = PaddingValues(56.dp)
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(context.getString(R.string.command_palette_process_commands_test_tag)).performClick()
        composeTestRule.onNodeWithTag(context.getString(R.string.loader_test_tag)).assertDoesNotExist()
    }
}
