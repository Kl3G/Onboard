
// 페이지 이동 버튼
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
            alert('メールアドレスを入力してください。');
        }else if(!mail.includes('@') || !mail.includes('.')){

            event.preventDefault();
            alert("正しいメールアドレスの形式ではありません。");
        }
    })
});
// ---------------------------------------------------