
// 아이디 찾기 버튼 활성화
$(document).ready(function(){

    $('#button_findId').on('click', function(event){

        window.location.href = '/findId';
    })
});
// --------------------------------------------------


// 유효성 검사
$(document).ready(function(){

    $('.form__section').on('submit', function(event){

        const mail = document.querySelector('input[name="mail"]').value.trim();
        const userid = document.querySelector('input[name="userid"]').value.trim();

        if(!mail){

            event.preventDefault();
            alert('이메일을 입력해 주세요.');
        }else if(!mail.includes('@') || !mail.includes('.')){

            event.preventDefault();
            alert("올바른 이메일 형식이 아닙니다.");
        }else if(!userid){

            event.preventDefault();
            alert('아이디를 입력해 주세요.');
        }
    })
});
// --------------------------------------------------
