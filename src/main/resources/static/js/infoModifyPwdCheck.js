/*$.ajax({
    type: 'POST',
    url: '/infoModifyPwdCheck_proc',
    data: {
        userid: $('#userid').val(),
        pwd: $('#pwd').val(),
        option: $('#option').val() // 혹은 원하는 방식으로 option 값을 가져옴
    },
    success: function(response) {
        // 서버에서 "redirect:" 문자열로 시작하면 리다이렉트 처리
        if(response.indexOf("redirect:") === 0) {
            var url = response.substring("redirect:".length);
            window.location.href = url;
        } else {
            // 그 외에는 HTML 스크립트가 담겨있는 경우
            // 예: <script>alert('비밀번호가 일치하지 않습니다.'); history.back();</script>
            $('body').html(response);
        }
    },
    error: function(xhr) {
        alert("오류가 발생했습니다.");
    }
});*/

/*
$(document).ready(function() {
  $('#infoModifyPwdCheckForm').on('submit', function(event) {
    event.preventDefault(); // 폼 기본 제출 막기

    const userid = $('#userid').val();
    const pwd = $('#pwd').val();
    const option = $('#option').val(); // option 값 가져오기

    fetch('/infoModifyPwdCheck_proc', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        'userid': userid,
        'pwd': pwd,
        'option': option
      })
    })
    .then(response => response.text())  // 응답을 텍스트로 변환
    .then(responseText => {
      responseText = responseText.trim(); // 공백 제거
      // 서버 응답이 "redirect:"로 시작하면 리다이렉트 처리
      if(responseText.startsWith("redirect:")) {
          const url = responseText.substring("redirect:".length);
          window.location.href = url;
      } else {
        // 그 외의 경우(예: alert 스크립트가 포함된 HTML)는 body에 삽입
        document.body.innerHTML = responseText;
      }
    })
    .catch(error => console.error('오류 발생:', error));
  });
});
*/
