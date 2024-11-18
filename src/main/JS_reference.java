/*

  1. event listener(이벤트 리스너) 또는 event handler(이벤트 핸들러)
    기본 양식 = $('#태그의 id').on('event', function명);

    특정 이벤트가 발생할 때 자동으로 실행될 함수를 연결하는 역할.
    $('#loginForm').on('submit', hello);

    function hello(event) {

        event.preventDefault();
    }

    loginForm 이라는 id를 가진 form이 submit(제출)되면,
    submit 이라는 event가 발생한 것이다.
    그 event는 "hello" 라는 함수의 Argument로 전달되고
    hello함수는 그 event(submit)에 대해 여러가지 기능을 쓸 수 있다.

    preventDefault(); 이 메서드는 이벤트의 기본 동작을 막는 기능을 가지고 있으며,
    폼 제출이나 링크 클릭 등 기본 동작이 있는 이벤트에서 주로 사용한다.

    자바스크립트와 jQuery에서는 다양한 이벤트와 그에 따른 기능들이 존재하며
    이로 인해 조합할 수 있는 경우의 수가 매우 많아진다.
    덕분에 매우 다양하고 유연한 웹 애플리케이션을 만들 수 있는 가능성이 열리는 것!


  2. JPA는 DB와의 상호작용을 위한 API이고, Fetch API는 클라이언트 측에서 네트워크 요청을 수행하는 데 사용되는 API이다.


  3. 화살표 함수

    1번
    .then(response => response.text())

    2번
    .then(response => {
        console.log(response); // 응답 객체 출력
        return response.text(); // 응답 본문을 텍스트로 변환하여 반환
    })

    화살표 함수에서 기능이 하나만 있을 경우에는 return 키워드와 중괄호를 생략할 수 있다.


  4. ajax의 response
    response은 반드시 ResponseEntity 타입의 객체를 반환받는다.
    response.text() 메서드는 response의 body부분에 있는 모든 것를 문자열로 반환한다.


*/