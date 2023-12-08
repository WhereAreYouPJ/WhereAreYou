import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun test() {


    Button(
        onClick = { /* 버튼 클릭 이벤트 처리 */ },
        modifier = Modifier
            .size(width = 100.dp, height = 100.dp)
            .background(color=Color.White) // 버튼 배경 색상 설정
    ) {
            // PNG 이미지를 배경으로 설정


            // 버튼 텍스트 또는 다른 콘텐츠 추가

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Gray)
            .clickable {


            },
        contentAlignment = Alignment.Center
    ) {

    }


}