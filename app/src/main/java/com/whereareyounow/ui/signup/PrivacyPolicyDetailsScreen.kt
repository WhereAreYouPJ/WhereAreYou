package com.whereareyounow.ui.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.ui.component.CustomSurface
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme
import com.whereareyounow.util.CustomPreview

@Composable
fun PrivacyPolicyDetailsScreen(
    moveToPolicyAgreeScreen: () -> Unit
) {
    BackHandler {
        moveToPolicyAgreeScreen()
    }
    CustomSurface {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            item {
                CustomTopBar(
                    title = "개인정보 처리방침",
                    onBackButtonClicked = moveToPolicyAgreeScreen
                )
            }
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp,
                    text = "1. 개인정보의 처리 목적\n" +
                            "CodingHarang은(는) 다음의 목적을 위하여 개인정보를 처리하고 있으며, 다음 목적 이외의 용도로는 이용하지 않습니다.\n" +
                            "\n" +
                            "- android.permission.INTERNET: 네트워크 연결\n" +
                            "- android.permission.ACCESS_NETWORK_STATE: 네트워크 연결 상태 확인\n" +
                            "- android.permission.ACCESS_COARSE_LOCATION: 사용자 위치 확인\n" +
                            "- android.permission.ACCESS_FINE_LOCATION: 사용자 위치 확인\n" +
                            "- android.permission.ACCESS_BACKGROUND_LOCATION: 위치 서비스 상태 확인\n" +
                            "- android.permission.WAKE_LOCK: 친구 요청 알림\n" +
                            "- android.permission.READ_MEDIA_IMAGES: 프로필 이미지 변경\n" +
                            "\n" +
                            "2. 개인정보의 처리 및 보유 기간\n" +
                            "1) CodingHarang은(는) 정보주체로부터 개인정보를 수집할 때 동의받은 개인정보 보유.이용기간 또는 법령에 따른 개인정보 보유.이용기간 내에서 개인정보를 처리.보유합니다.\n" +
                            "2) 구체적인 개인정보 처리 및 보유 기간은 다음과 같습니다.\n" +
                            "- 고객가입 및 관리: 서비스 이용 계약 또는 회원가입 해지시까지\n" +
                            "\n" +
                            "3. 개인정보의 제3자 제공에 관한 사항\n" +
                            "1) CodingHarang은(는) 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제 17조 및 18조에 해당하는 경우에만 개인정보를 제 3자에게 제공합니다.\n" +
                            "2) CodingHrarng은(는) 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.\n" +
                            "- 개인정보를 제공받는 자: 구글(Google)\n" +
                            "- 제공받는 자의 개인정보 이용목적: 다운받은 앱 사용시 수명주기와 발생 이벤트 등의 분석 및 통계용(구글 애널리틱스), 정보주체별 맞춤광고 제공용(구글 애드몹)\n" +
                            "- 제공받는 자의 제품별 개인정보처리방침: 구글 개인정보 처리방침에 따름(https://policies.google.com/privacy?hl=ko)\n" +
                            "\n" +
                            "4. 개인정보처리 위탁\n" +
                            "1) CodingHarang은(는) 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.\n" +
                            "(1) 위탁없음\n" +
                            "- 위탁받는 자(수탁자): 없음\n" +
                            "- 위탁하는 업무의 내용: 없음\n" +
                            "- 위탁기간: 없음\n" +
                            "2)CodingHarang은(는) 위탁계약 체결시 개인정보 보호법 제 25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적.관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리.감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.\n" +
                            "3)위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.\n" +
                            "\n" +
                            "5. 정보주체와 법정대리인의 권리.의무 및 그 행사방법 이용자는 개인정보주체로써 다음과 같은 권리를 행사할 수 있습니다.\n" +
                            "1) 정보주체는 CodingHarang에 대해 언제든지 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.\n" +
                            "(1) 개인정보 열람요구\n" +
                            "(2) 오류 등이 있을 경우 정정 요구\n" +
                            "(3) 삭제요구\n" +
                            "(4) 처리정지 요구\n" +
                            "\n" +
                            "6. 처리하는 개인정보의 항목 작성\n" +
                            "1) CodingHarang은(는) 다음의 개인정보 항목을 처리하고 있습니다.\n" +
                            "(1) 기본 필수 정보\n" +
                            "- 필수항목: 이메일(email)\n" +
                            "- 선택항목: 없음\n" +
                            "\n" +
                            "7. 개인정보의 파기: CodingHarang은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.\n" +
                            "- 파기절차\n" +
                            "이용자가 입력한 정보는 목적 달서 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.\n" +
                            "- 파기기한\n" +
                            "이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.\n" +
                            "\n" +
                            "8. 개인정보 자동 수집 장치의 설치.운영 및 거부에 관한 사항\n" +
                            "CodingHarang은(는) 정보주체의 이용정보를 저장하고 수시로 불러오는 '쿠키'를 사용하지 않습니다. 정보 주체가 제3자인 구글에게 제공하는 정보는 CodingHarang와(과) 상관없이 정보 주체 기기상의 구글 설정에 따릅니다.\n" +
                            "\n" +
                            "9. 개인정보 보호책임자 작성\n" +
                            "1) CodingHarnag은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.\n" +
                            "(1) 개인정보 보호책임자\n" +
                            "- CodingHarang(txepahs@gmail.com)\n" +
                            "\n" +
                            "10. 개인정보 처리방침 변경\n" +
                            "1) 이 개인정보 처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.\n" +
                            "\n" +
                            "2023.01.03 최종 업데이트됨"
                )
            }
        }
    }
}


@CustomPreview
@Composable
private fun PrivacyPolicyDetailsScreenPreview() {
    WhereAreYouTheme {
        PrivacyPolicyDetailsScreen(
            moveToPolicyAgreeScreen = {  }
        )
    }
}