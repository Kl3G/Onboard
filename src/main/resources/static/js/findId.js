
// 버튼 호버 색상 변경
$(document).ready(function(){

    $('#button_findPwd').on('click', function(event){

        window.location.href = '/findPwd';
    })
});
// ---------------------------------------------------



// 이메일 입력 유효성 검사
$(document).ready(function(){

    $('#form__section').on('submit', function(event){

        const mail = document.querySelector('input[name="mail"]').value.trim();
        // .trim() 으로 앞뒤 공백 제거

        if(!mail){

            event.preventDefault();
            alert('이메일을 입력해 주세요.');
        }else if (!mail.includes('@')) {

            event.preventDefault();
            alert("올바른 이메일 형식이 아닙니다.");
        }
    })
});
// ---------------------------------------------------