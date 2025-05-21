package com.rpla.marsrovernavigator.domain

import com.rpla.marsrovernavigator.navigator.data.model.Coordinates
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorConfig
import com.rpla.marsrovernavigator.navigator.data.model.NavigatorDirection
import com.rpla.marsrovernavigator.navigator.domain.entity.ProcessedCoordinatesEntity
import com.rpla.marsrovernavigator.navigator.domain.repository.CoordinatesRepository
import com.rpla.marsrovernavigator.navigator.domain.usecase.GetNavigatorCoordinatesUseCase
import com.rpla.marvelherosrepo.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetNavigatorCoordinatesUseCaseTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun init() {
        MockKAnnotations.init(this, true)
    }

    @Test
    fun `Given a NavigatorConfig when UseCase called then it should return a ProcessedCoordinatesEntity`() =
        runTest {
            val navigatorConfig =
                NavigatorConfig(
                    topRightCorner = Coordinates(5, 5),
                    roverPosition = Coordinates(3, 2),
                    roverNavigatorDirection = NavigatorDirection.N,
                    movements = "LRMLLM",
                )

            val expectedEntity = ProcessedCoordinatesEntity(rawCoordinates = "3 2 S")

            val mockRepository = mockk<CoordinatesRepository>()
            coEvery { mockRepository.getNewCoordinates(navigatorConfig) } returns expectedEntity

            val useCase = GetNavigatorCoordinatesUseCase(mockRepository)

            var result: ProcessedCoordinatesEntity? = null
            useCase.invoke(
                coroutinesTestRule.testCoroutineScope,
                coroutinesTestRule.testDispatcher,
                GetNavigatorCoordinatesUseCase.RequestValue(navigatorConfig),
            ) {
                result = it
            }

            coVerify(exactly = 1) { mockRepository.getNewCoordinates(navigatorConfig) }
            confirmVerified(mockRepository)

            Assert.assertEquals(expectedEntity.rawCoordinates, result?.rawCoordinates)
        }

    @Test
    fun `Given only rotation commands sequence When UseCase called Then returns the same coordinates`() =
        runTest {
            val navigatorConfig =
                NavigatorConfig(
                    topRightCorner = Coordinates(5, 5),
                    roverPosition = Coordinates(3, 2),
                    roverNavigatorDirection = NavigatorDirection.N,
                    movements = "LRLL",
                )

            val expectedEntity = ProcessedCoordinatesEntity(rawCoordinates = "3 2 S")

            val mockRepository = mockk<CoordinatesRepository>()
            coEvery { mockRepository.getNewCoordinates(navigatorConfig) } returns expectedEntity

            val useCase = GetNavigatorCoordinatesUseCase(mockRepository)

            var result: ProcessedCoordinatesEntity? = null
            useCase.invoke(
                coroutinesTestRule.testCoroutineScope,
                coroutinesTestRule.testDispatcher,
                GetNavigatorCoordinatesUseCase.RequestValue(navigatorConfig),
            ) {
                result = it
            }

            coVerify { mockRepository.getNewCoordinates(navigatorConfig) }
            confirmVerified(mockRepository)
            Assert.assertEquals("3 2 S", result?.rawCoordinates)
        }

    @Test
    fun `Given commands sequence reaches limits when UseCase called then should get coordinates within the limits`() =
        runTest {
            val navigatorConfig =
                NavigatorConfig(
                    topRightCorner = Coordinates(5, 5),
                    roverPosition = Coordinates(5, 5),
                    roverNavigatorDirection = NavigatorDirection.N,
                    movements = "MMMM", // Intenta salir del grid hacia arriba
                )

            val expectedEntity = ProcessedCoordinatesEntity(rawCoordinates = "5 5 N") // No debería moverse

            val mockRepository = mockk<CoordinatesRepository>()
            coEvery { mockRepository.getNewCoordinates(navigatorConfig) } returns expectedEntity

            val useCase = GetNavigatorCoordinatesUseCase(mockRepository)

            var result: ProcessedCoordinatesEntity? = null
            useCase.invoke(
                coroutinesTestRule.testCoroutineScope,
                coroutinesTestRule.testDispatcher,
                GetNavigatorCoordinatesUseCase.RequestValue(navigatorConfig),
            ) { result = it }

            coVerify { mockRepository.getNewCoordinates(navigatorConfig) }
            confirmVerified(mockRepository)
            Assert.assertEquals("5 5 N", result?.rawCoordinates)
        }

    @Test
    fun `Given consecutive commands sequence when UseCase called then it should return correct coordinates`() =
        runTest {
            val navigatorConfig =
                NavigatorConfig(
                    topRightCorner = Coordinates(5, 5),
                    roverPosition = Coordinates(1, 2),
                    roverNavigatorDirection = NavigatorDirection.N,
                    movements = "LMLMLMLMM",
                )

            val expectedEntity = ProcessedCoordinatesEntity(rawCoordinates = "1 3 N")

            val mockRepository = mockk<CoordinatesRepository>()
            coEvery { mockRepository.getNewCoordinates(navigatorConfig) } returns expectedEntity

            val useCase = GetNavigatorCoordinatesUseCase(mockRepository)

            var result: ProcessedCoordinatesEntity? = null
            useCase.invoke(
                coroutinesTestRule.testCoroutineScope,
                coroutinesTestRule.testDispatcher,
                GetNavigatorCoordinatesUseCase.RequestValue(navigatorConfig),
            ) { result = it }

            coVerify { mockRepository.getNewCoordinates(navigatorConfig) }
            confirmVerified(mockRepository)
            Assert.assertEquals("1 3 N", result?.rawCoordinates)
        }
}
