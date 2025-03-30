
$(document).ready(function(){

    $('#button_findId').on('click', function (event) {

        window.location.href = '/findId';
    })

    $('.form__section').on('submit', function (event) {

        const newPwd = $(this).find('#newPwd').val();
        const newPwdCheck = $(this).find('#newPwdCheck').val();
        const patternPwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[^A-Za-z0-9\s]).{8,20}$/;

        if (!newPwd || !patternPwd.test(newPwd)) {

            event.preventDefault();
            alert('パスワードポリシーを守ってください。');
        } else if (newPwd !== newPwdCheck) {

            event.preventDefault();
            alert('パスワードが一致していません。');
        }
    })
});