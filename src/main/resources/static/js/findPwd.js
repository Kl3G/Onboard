
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
            alert('メールアドレスを入力してください。');
        }else if(!mail.includes('@') || !mail.includes('.')){

            event.preventDefault();
            alert("正しいメールアドレスの形式ではありません。");
        }else if(!userid){

            event.preventDefault();
            alert('IDを入力してください。');
        }
    })
});
// --------------------------------------------------
