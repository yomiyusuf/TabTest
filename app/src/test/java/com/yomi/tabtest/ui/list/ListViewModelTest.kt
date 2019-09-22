package com.yomi.tabtest.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yomi.tabtest.model.ListResponse
import com.yomi.tabtest.network.ListApi
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import net.gahfy.mvvmposts.utils.ApiUtils
import net.gahfy.mvvmposts.utils.POST_MOCK_PATH
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testRetrofitListApi = object : ListApi {
        override fun getCities(bbox: String, appId: String): Observable<ListResponse> {
            return Observable.fromCallable { ApiUtils.getUrl<ListResponse>(POST_MOCK_PATH) }
        }
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun loadList_success() {
        POST_MOCK_PATH = "listResponse.json"
        val viewModel = ListViewModel("")
        assertEquals("Check that adapter has correct number of rows", 15, viewModel.cityListAdapter.itemCount)
    }

}