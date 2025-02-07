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

    // 전통적인 함수 표현식
    const add = function(a, b) {
      return a + b;
    };

    // 화살표 함수 표현식
    const add = (a, b) => {
      return a + b;
    };

    함수의 본문이 단 한 줄로, 단순히 값을 반환하는 경우 중괄호 {}와 return 키워드를 생략할 수 있습니다.
    const add = (a, b) => a + b;

    매개변수가 하나뿐인 경우, 괄호 ()도 생략할 수 있습니다.
    const square = x => x * x;


  4. ajax의 response
    response은 반드시 ResponseEntity 타입의 객체를 반환받는다.
    response.text() 메서드는 response의 body부분에 있는 모든 것를 문자열로 반환한다.


  5. ajax와 xml, json, fetch 등등의 개념
    ajax = 웹 페이지를 다시 로드하지 않고도 서버와 데이터를 주고받아 웹 페이지 일부만 갱신할 수 있게 해주는 기술.
        (jQuery의 $.ajax() 를 사용하지 않아도 순수 자바스크립트(fetch, then)를 사용해 비동기 통신)
        원래는 "Asynchronous JavaScript and XML"의 줄임말이지만,
        요즘은 "새로고침 없이(비동기) 서버와 통신"하는 자바스크립트 코드 전반을 부르는 용어.

    json(JavaScript Object Notation) = 사람이 읽기 쉽고 기계가 파싱하기 쉬운 가볍고 텍스트 기반의 데이터 교환 형식.

        {
          "name": "Alice",
          "age": 25
        }
        이 JSON은 두 개의 키-값 쌍으로 이루어져 있습니다.
        "name": 문자열 "Alice"
        "age": 숫자 25

        1) JSON 문자열을 자바스크립트 객체로 변환하기 (파싱)
        const jsonString = '{ "name": "Alice", "age": 25 }';
        const obj = JSON.parse(jsonString);

        console.log(obj.name); // 출력: Alice
        console.log(obj.age);  // 출력: 25

        2) 자바스크립트 객체를 JSON 문자열로 변환하기 (문자열화)
        const person = {
          name: "Alice",
          age: 25
        };

        const jsonStr = JSON.stringify(person);

        console.log(jsonStr); // 출력: '{"name":"Alice","age":25}'

        3) 서버로부터 데이터를 받아서 화면에 출력하기
        fetch('/api/user')
          .then(response => response.json())
          .then(data => {
            console.log("사용자 이름:", data.name);
            console.log("나이:", data.age);
          });

        4) 사용자 입력 데이터를 서버로 보내기
        const userData = { name: "Alice", age: 25 };

        fetch('/api/user', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(userData)
        })
        .then(response => response.json())
        .then(result => {
          console.log("서버 응답:", result);
        });

    fetch = fetch() 함수는 Ajax 통신에서 사용되며, HTTP 요청을 보내고,
        서버로부터의 응답을 나타내는 Response 객체를 담고 있는 Promise를 반환.
        fetch()를 호출하면, 브라우저는 지정한 URL로 HTTP 요청을 보냅니다.
        이때, 요청의 본문(body), 헤더, 메서드(POST, GET 등) 등을 옵션으로 지정할 수 있습니다.
        서버가 요청에 응답하면, fetch()는 그 응답을 나타내는 Response 객체를 담은 Promise를 반환.



  6. Promise 체이닝
    Promise 체이닝에서는 이전 .then()에서 return한 값(또는 throw한 에러)이
    **다음 .then()(혹은 .catch())**의 매개변수로 넘어가게 됩니다.

    Promise.resolve(1)
      .then(value => {
        console.log(value); // 1
        return value + 1;   // ★ 다음 then으로 넘어갈 값
      })
      .then(nextValue => {
        console.log(nextValue); // 2
        return nextValue * 3;   // ★ 다음 then으로 넘어갈 값
      })
      .then(finalValue => {
        console.log(finalValue); // 6
      });


  7. Content-Type 에 대하여
    Content-Type: text/html → 바디가 HTML 문서임을 의미
    Content-Type: image/png → 바디가 PNG 이미지
    Content-Type: application/json → 바디가 JSON 형태
    Content-Type: application/x-www-form-urlencoded → 바디가 HTML 폼 같은 key=value&... 방식


  8. Promise 객체에 대하여
    Promise 생성자를 사용할 때는 실행자 함수의 매개변수로 제공되는
    resolve와 reject 함수를 사용하여 비동기 작업의 결과를 알린다.
    순서에 따라 첫 번째는 성공(fulfilled) 처리를 위한 함수, 두 번째는 실패(rejected) 처리를 위한 함수로 자동 구분

    function getDataAsync() {
      // Promise 생성자에 실행자 함수를 전달합니다.
      return new Promise((resolve, reject) => {
        // 여기서 resolve와 reject는 Promise 생성자 내부에서 제공된 함수입니다.
        // 비동기 작업을 수행합니다.
        setTimeout(() => {
          // 비동기 작업이 성공하면 resolve를 호출합니다.
          resolve("성공 데이터");

          // 만약 비동기 작업이 실패한다면 아래와 같이 reject를 호출할 수 있습니다.
          // reject(new Error("실패 사유"));
        }, 1000);
      });
    }

    // Promise 사용 예
    getDataAsync()
      .then(data => {
        console.log("data =", data); // 1초 후에 "성공 데이터"가 출력됩니다.
      })
      .catch(err => {
        console.error("에러:", err);
    });


  9. 파싱이란?
    파싱은 문자열(글)을 컴퓨터가 이해할 수 있는 데이터 구조로 바꾸는 과정
    컴퓨터에서는 파싱을 통해 데이터를 쪼개고, 각 조각의 의미를 찾아내어,
    그 결과를 우리가 쉽게 사용할 수 있는 형태(예: 객체, 배열 등)로 만든다.


*/