package com.xczcdjx.word
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
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
import com.xczcdjx.word.constants.BottomTabs
import com.xczcdjx.word.router.Routes
import com.xczcdjx.word.screen.Home
import com.xczcdjx.word.screen.MinePage
import com.xczcdjx.word.screen.PostPage
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
            val botItems: List<BottomTabs> = listOf(
                BottomTabs("学习", Icons.Filled.Home, "study"),
                BottomTabs("打卡", Icons.Filled.DateRange, "post"),
                BottomTabs("我的", Icons.Filled.Person, "mine"),
            )
            var actBotKey by remember { mutableStateOf(botItems[0].key) }
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
                        Scaffold(bottomBar = {
                            BottomAppBar() {
                                Row(
                                    Modifier
                                        .fillMaxSize()
                                        .wrapContentHeight(Alignment.CenterVertically),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    botItems.forEach { b ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.clickable {
                                                actBotKey = b.key
                                            }) {
                                            val c =
                                                if (b.key == actBotKey) Color.Red else Color.Unspecified
                                            Icon(b.icon, null, tint = c)
                                            Text(b.title, fontSize = 15.sp, color = c)
                                        }
                                    }
                                }
                            }
                        }) { pad ->
                            val goLogin = {
                                controller.navigate(Routes.Test.route)
                            }
                            when (actBotKey) {
                                "study" -> Home(pad, goLogin = goLogin)
                                "post" -> PostPage(pad, goLogin = goLogin)
                                else -> {
                                    MinePage(pad, goLogin = goLogin)
                                }
                            }
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