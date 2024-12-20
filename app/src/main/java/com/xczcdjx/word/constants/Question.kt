package com.xczcdjx.word.constants
import kotlin.random.Random

// 定义数据类
data class Question(
    val word: String,       // 单词
    val sentence: String,   // 例句
    val options: List<String>, // 选项
    val answer: String      // 答案
)

// 题库
val questionData: List<Question> = listOf(
    Question(
        word = "book",
        options = listOf("书籍", "笔", "橡⽪", "背包"),
        answer = "书籍",
        sentence = "I love to read a good book every night."
    ),
    Question(
        word = "computer",
        options = listOf("电视", "电脑", "⼿机", "相机"),
        answer = "电脑",
        sentence = "I use the computer for work and entertainment."
    ),
    Question(
        word = "apple",
        options = listOf("⾹蕉", "桃⼦", "梨", "苹果"),
        answer = "苹果",
        sentence = "She enjoys eating a crisp apple in the afternoon."
    ),
    Question(
        word = "sun",
        options = listOf("⽉亮", "太阳", "星星", "地球"),
        answer = "太阳",
        sentence = "The sun provides warmth and light to our planet."
    ),
    Question(
        word = "water",
        options = listOf("⽕", "⼟地", "⻛", "⽔"),
        answer = "⽔",
        sentence = "I always carry a bottle of water with me."
    ),
    Question(
        word = "mountain",
        options = listOf("沙漠", "海洋", "平原", "⼭"),
        answer = "⼭",
        sentence = "The mountain range is covered in snow during winter."
    ),
    Question(
        word = "flower",
        options = listOf("树⽊", "草地", "花", "灌⽊"),
        answer = "花",
        sentence = "The garden is filled with colorful flowers."
    ),
    Question(
        word = "car",
        options = listOf("⾃⾏⻋", "⻜机", "船", "汽⻋"),
        answer = "汽⻋",
        sentence = "I drive my car to work every day."
    ),
    Question(
        word = "time",
        options = listOf("空间", "时钟", "⽇历", "时间"),
        answer = "时间",
        sentence = "Time flies when you're having fun."
    ),
    Question(
        word = "music",
        options = listOf("画", "舞蹈", "⾳乐", "戏剧"),
        answer = "⾳乐",
        sentence = "Listening to music helps me relax."
    ),
    Question(
        word = "rain",
        options = listOf("雪", "雷电", "阳光", "⾬"),
        answer = "⾬",
        sentence = "I enjoy the sound of rain tapping on the window."
    ),
    Question(
        word = "fire",
        options = listOf("冰", "⽕焰", "烟雾", "闪电"),
        answer = "⽕焰",
        sentence = "The campfire warmed us on a chilly evening."
    ),
    Question(
        word = "friend",
        options = listOf("陌⽣⼈", "邻居", "家⼈", "朋友"),
        answer = "朋友",
        sentence = "A true friend is always there for you."
    ),
    Question(
        word = "food",
        options = listOf("⽔果", "蔬菜", "⾁", "⻝物"),
        answer = "⻝物",
        sentence = "Healthy food is essential for a balanced diet."
    ),
    Question(
        word = "color",
        options = listOf("⿊⾊", "⽩⾊", "红⾊", "颜⾊"),
        answer = "颜⾊",
        sentence = "The artist used a vibrant color palette."
    ),
    Question(
        word = "bookshelf",
        options = listOf("椅⼦", "桌⼦", "书架", "床"),
        answer = "书架",
        sentence = "The bookshelf is filled with novels and reference books."
    ),
    Question(
        word = "moon",
        options = listOf("太阳", "星星", "⽉亮", "地球"),
        answer = "⽉亮",
        sentence = "The moonlight illuminated the night sky."
    ),
    Question(
        word = "school",
        options = listOf("公园", "商店", "医院", "学校"),
        answer = "学校",
        sentence = "Students go to school to learn and grow."
    ),
    Question(
        word = "shoes",
        options = listOf("帽⼦", "⾐服", "裤⼦", "鞋⼦"),
        answer = "鞋⼦",
        sentence = "She bought a new pair of stylish shoes."
    ),
    Question(
        word = "camera",
        options = listOf("电视", "电脑", "相机", "⼿机"),
        answer = "相机",
        sentence = "The photographer captured the moment with his camera."
    )
)


// 随机抽取 n 个题目
fun getRandomQuestions(count: Int): List<Question> {
    if (count > questionData.size) {
        throw IllegalArgumentException("题目数量超过题库的总量")
    }
    val indexes = mutableSetOf<Int>()
    while (indexes.size < count) {
        val index = Random.nextInt(questionData.size)
        indexes.add(index)
    }
    return indexes.map { questionData[it] }
}
