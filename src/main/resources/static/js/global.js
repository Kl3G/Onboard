$(document).ready(function() {
    // 슬릭 슬라이더 초기화
    $('.slick-class').slick({
        infinite: false,
        slidesToShow: 1,
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000
    });
});


let isLoggedIn = false; // 전역 변수 선언
let loginForm = "";
let emptySpace = "";
let join = "";
let find = "";
let logoutBtn = "";

// 페이지 로드 시 숨겨야 할 요소들 설정
if (!isLoggedIn) {
    $('#logoutBtn').hide(); // 로그인하지 않은 경우 로그아웃 링크 숨기기
}

// 로그인 폼 제출 핸들러 등록
$('#loginForm').on('submit', handleLoginSubmit);
// 일반적인 함수 호출이 아니라, 이벤트 리스너라는 특별한 양식에 맞춰 작성된 실행 코드
// loginForm이 submit 될 때 "handleLoginSubmit" function 실행

// 로그인 폼 제출 핸들러
function handleLoginSubmit(event) {
    event.preventDefault(); // 기본 폼 제출 동작 방지
    // 폼의 기본 제출 동작을 차단하고, 대신 자바스크립트를 사용하여 AJAX 요청을 보낼 수 있습니다.
    // 이 코드가 있어야 fetch 를 사용할 수 있다.
    /* type="button" 속성을 사용하여 폼의 기본 제출을 막고 있을 때는 event.preventDefault();가 필요하지 않습니다.
       만약 버튼에 type="submit"을 사용했다면, event.preventDefault();를 통해 기본 제출을 막고 AJAX를 사용해야 합니다. */

    // 입력 필드 값 가져오기
    const userid = $('#userid').val();
    const pwd = $('#pwd').val();
    // val() method는 input 태그의 값을 가져온다.

    // AJAX 요청
    fetch('/login_proc', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            // 데이터가 (username=abc&password=123)처럼 URL 인코딩 형식 방식으로 전송된다.
        },
        body: new URLSearchParams({
            'username': userid,
            'password': pwd
            // 왼쪽에는 form에서 오는 name을 오른쪽에는 변수 이름을 적는다.
        })
    })
    // response 변수는 서버로부터 받은 HTTP 응답을 나타내며, 이 객체에는 HTTP 상태 코드와 기타 응답 정보가 포함되어 있습니다.
    .then(response => {

        console.log(response);
        if (response.ok/*HTTP 상태 코드가 200-299 범위에 있을 경우 true를 반환*/) {
            // 로그인 성공 시
            isLoggedIn = true;

            // userid를 sessionStorage에 저장
            sessionStorage.setItem('userid', userid);

            loginForm = $('#loginForm');
            emptySpace = $('#emptySpace');
            join = $('#join');
            find = $('#find');
            logoutBtn = $('#logoutBtn');

            // 폼을 숨기고 빈 div를 보여줍니다.
            loginForm.hide(); // 폼 숨기기
            emptySpace.show(); // 빈 div 표시
            join.hide(); // 회원가입 링크 숨기기
            find.hide(); // 비밀번호 찾기 링크 숨기기
            logoutBtn.show(); // 로그아웃 링크 표시
            fetch('/api/get-nick')
            .then(response => response.text())
            .then(nick => {
                if (nick) {
                    // nick 값이 존재할 경우 처리
                    emptySpace.html('<span>안녕하세요!</span><br><span style="font-weight: bold;">' + nick + '</span>님');
                }
            })
                .catch(error => console.error('nick 값을 가져오는 중 오류 발생:', error));
        } else if (response.status === 401) {
            // response.status: 서버가 반환한 HTTP 상태 코드를 숫자로 제공합니다.
            // 예를 들어, 로그인 성공 시 200, 실패 시 401이 될 수 있습니다.
            alert('로그인 실패. 아이디와 비밀번호를 확인하세요.');
        }
    })
    .catch(error => {
        console.error('로그인 요청 중 오류 발생:', error);
    });
}


window.onload = function() {

    // 제이쿼리 사용 예시
    // $(window).on('load', status);
    // function status(/*event*/) {
    // $('#loginForm').on('submit', handleLoginSubmit);
    // "load" 라는 동작을 제어하고 싶지 않으면 매개변수로 event를 가져오지 않아도 된다.

    // sessionStorage에서 userid를 불러옵니다.
    const storedUserId = sessionStorage.getItem('userid');

    fetch('/api/session-status')
        .then(response => response.text())
        .then(status => {
            if (status === 'logged_in') {
                // 로그인 상태일 때 보여줄 내용
                fetch('/api/get-nick')
                    .then(response => response.text())
                    .then(nick => {
                        if (nick) {
                            // nick 값이 존재할 경우 처리
                            emptySpace.html('<span>안녕하세요!</span><br><span style="font-weight: bold;">' + nick + '</span>님');
                        }
                    })
                    .catch(error => console.error('nick 값을 가져오는 중 오류 발생:', error));

                loginForm = $('#loginForm');
                emptySpace = $('#emptySpace');
                join = $('#join');
                find = $('#find');
                logoutBtn = $('#logoutBtn');

                // 폼을 숨기고 빈 div를 보여줍니다.
                loginForm.hide(); // 폼 숨기기
                emptySpace.show(); // 빈 div 표시
                join.hide(); // 회원가입 링크 숨기기
                find.hide(); // 비밀번호 찾기 링크 숨기기
                logoutBtn.show(); // 로그아웃 링크 표시

                // 빈 div에 원하는 내용을 추가합니다.
                emptySpace.html
                ('<span>안녕하세요!</span><br><span style= "font-weight: bold;">'+storedUserId+'</span>님');
            } else {
                // 비로그인 상태일 때 보여줄 내용
                emptySpace.hide();
            }
        })
        .catch(error => console.error('세션 상태 확인 중 오류 발생:', error));
};