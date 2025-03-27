

    $(document).ready(() => {

        let duplicate = false;
        let deprecate = false;

        let duplicateNick = false;
        let deprecateNick = false;

        // 회원가입 폼 유효성 검사
        $('.form__section').on('submit', (event) => {

            const userid = $('#userid').val();

            const pwd = $('#pwd').val();
            const pwdCheck = $('#pwdCheck').val();
            const regexPwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[^A-Za-z0-9\s]).{8,20}$/;

            const nick = $('#nick').val();

            const mail = $('#mail').val();
            const mailCheck = $('#mailCheck').val();
            const regexMail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;

            if (deprecate || userid === '') {

                event.preventDefault();
                alert('IDが正しくありません。');

                duplicate = false;

                return;

            } else if (duplicate) {

                event.preventDefault();
                alert('すでに使用されているIDです。');

                deprecate = false;

                return;

            } else if (!regexPwd.test(pwd)) {

                event.preventDefault();
                alert('パスワードポリシーを守ってください。');

                return;

            } else if (pwd != pwdCheck) {

                event.preventDefault();
                alert('パスワードが一致していません。');

                return;

            } else if (deprecateNick || nick === "") {

                event.preventDefault();
                alert('ニックネームが正しくありません。');

                duplicateNick = false;

                return;

            } else if (duplicateNick) {

                event.preventDefault();
                alert('すでに使用されているニックネームです。');

                deprecateNick = false;

                return;

            } else if (!regexMail.test(mail)) {

                event.preventDefault();
                alert('メールアドレスが正しくありません。');

                return;

            } else if (mail != mailCheck) {

                event.preventDefault();
                alert('メールアドレスが一致していません。');

                return;
            }
        })


        // 아이디 유효성 검사
        $('#userid').on('input', function () {

            const userid = $(this).val();
            const regexUserid = /^(?=.*[a-z])(?=.*\d)[a-z0-9]{5,15}$/;

            $.ajax({

                url: '/checkUserid',  // 서버에 구현된 아이디 중복 검사 API 엔드포인트
                type: 'POST',
                dataType: 'json',
                data: { userid: userid }, // 여기까지 서버로 전송

                success: (response) => { // 여기부터 서버에서 수신

                    if(response.exists) {

                        $('#useridCheck').text('すでに使用されているIDです。').css({

                            'color': '#D31900'
                        });

                        duplicate = true;

                        return;

                    } else if (!regexUserid.test(userid)) {

                        $('#useridCheck').text('IDが正しくありません。').css({

                            'color': '#D31900'
                        });

                        deprecate = true;

                        return;

                    } else if (!response.exists && regexUserid.test(userid)) {

                        $('#useridCheck').text('使用できるIDです。').css({

                            'color': 'royalblue'
                        });

                        duplicate = false;
                        deprecate = false;

                        return;

                    } else {

                        console.log('알 수 없는 에러 발생');
                    }
                },
                error: (error) => {

                    console.error('error: ', error.status);
                }
            });
        });

        // 닉네임 유효성 검사
        $('#nick').on('input', function () {

            const nick = $('#nick').val();
            const regexNick = /^[A-Za-z0-9가-힣ㄱ-ㅎぁ-んァ-ン一-龯]{2,10}$/;

            $.ajax({

                url: '/checkNick',
                type: 'POST',
                dataType: 'json',
                data: { nick: nick },

                success: (response) => {

                    if (response.exists) {

                        $('#nickCheck').text('すでに使用されているニックネームです。').css({

                            'color': '#D31900'
                        });

                        duplicateNick = true;

                        return;

                    } else if (!regexNick.test(nick)) {

                        $('#nickCheck').text('ニックネームが正しくありません。').css({

                            'color': '#D31900'
                        });

                        deprecateNick = true;

                        return;

                    } else if (!response.exists && regexNick.test(nick)) {

                        $('#nickCheck').text('使用できるニックネームです。').css({

                            'color': 'royalblue'
                        });

                        duplicateNick = false;
                        deprecateNick = false;

                        return;

                    } else {

                        console.log('알 수 없는 에러 발생');
                    }
                },
                error: (error) => {

                    console.log('error: ', error.status);
                }
            })
        })
    })