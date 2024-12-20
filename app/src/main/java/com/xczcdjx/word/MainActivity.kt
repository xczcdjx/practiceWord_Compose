package com.xczcdjx.word
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import coil3.util.DebugLogger
import com.xczcdjx.word.router.Routes
import com.xczcdjx.word.screen.Home
import com.xczcdjx.word.screen.SplashScreen
import com.xczcdjx.word.screen.Test
import com.xczcdjx.word.ui.theme.PracticeWordTheme
import dagger.hilt.android.AndroidEntryPoint
import okio.FileSystem

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeWordTheme {
                // 注册图片加载优化
                setSingletonImageLoaderFactory { context ->
                    getAsyncImageLoader(context)
                }
                val controller = rememberNavController()
                NavHost(navController = controller,
                    startDestination = Routes.Home.route,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left,
                            // 修改过渡时间
//                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    },
                    popEnterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    },
                    popExitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    }) {
                    composable(Routes.Splash.route) {
                        SplashScreen() {
                            controller.navigate(Routes.Home.route){
                                popUpTo(Routes.Splash.route) { inclusive = true }
                            }
                        }
                    }
                    composable(Routes.Home.route) {
                        Home() {
                            controller.navigate(Routes.Test.route)
                        }
                    }
                    composable(Routes.Test.route) {
                        Test() {
                            controller.popBackStack()
                        }
                    }
                }
            }
        }
    }
}

// 配置网络图,svg支持，磁盘缓存等
fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.components {
        add(SvgDecoder.Factory())
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 100) // 100MB
        .build()
}