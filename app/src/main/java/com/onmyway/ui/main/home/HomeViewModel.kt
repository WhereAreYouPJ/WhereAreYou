package com.onmyway.ui.main.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _string = MutableStateFlow("a")
    val string = _string.asStateFlow()

    private val _firstImage = MutableStateFlow("a")
    val firstImage = _firstImage.asStateFlow()

    data class SecondDataModel(
        var image: String
    )

//            private val _secondImage = MutableStateFlow<List<SecondDataModel>>(emptyList())
    private val _secondImage = MutableStateFlow(
        listOf(
            SecondDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg"
            ),
            SecondDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg"
            ),
            SecondDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg"
            ),
            SecondDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg"
            ),
            SecondDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg"
            )
        )
    )
    val secondImage = _secondImage.asStateFlow()


    data class ThirdDataModel(
        var day: String,
        var dayContent: String
    )

//            private val _thirdData = MutableStateFlow<List<ThirdDataModel>>(emptyList())
    private val _thirdData = MutableStateFlow(listOf(ThirdDataModel("D - 369", "96즈 여의도 한강공원 모임")))
    val thirdData = _thirdData.asStateFlow()

    data class FourthDataModel(
        var image1: String,
        var title: String,
        var subTitle: String,
        var content: String
    )

    private val _fourthData = MutableStateFlow(
        listOf(
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하"
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            ),
            FourthDataModel(
                "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
                "여의도한강공원", "96즈 네번째 피크닉 다녀온 날", "1정말 간만에 다녀온 96즈끼리 다녀온 여의도 한강공원! 너무 간만이라 치킨 피자 어디에서 가져오는지도 헷갈리고 돗자리 깔 타이밍에 뭔 바람이 그렇게 부는지도 모르겠고 치킨과 맥주를 먹었는데 정말 맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다.맛있었다."
            )
        )
    )
//    private val _fourthData = MutableStateFlow<List<FourthDataModel>>(emptyList())

    val fourthData = _fourthData.asStateFlow()


    data class SevenDataModel(
        val day : String,
        val title1: String,
        val subTitle1: String,
        val title2: String,
        val subTitle2: String,
        val title3: String,
        val subTitle3: String,
        val title4: String,
        val subTitle4: String,
        val title5: String,
        val subTitle5: String,
        val title6: String,
        val subTitle6: String,
    )

    //    private val _bottomSheet = MutableStateFlow<List<SevenDataModel>>(emptyList())
    private val _sevenData = MutableStateFlow(
        listOf(
            SevenDataModel("4월 5일" , "한강공원", "여의도 한강공원" , "독서모임" , "교보문고 광화문점" , "기획 미팅" , "회사" , "운동 클럽" , "반포 한강공원" , "넷플릭스" , "집" , "새벽영화보기" , "메가박스 센트럴점")
        )
    )
    val sevenData = _sevenData.asStateFlow()

}