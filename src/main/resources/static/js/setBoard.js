
    $(document).ready(() => {

        let duplicate = false;

        $('#b_name').on('change', () => {

            const boardName = $('#b_name').val().replace(/\s+/g, '');

            $.ajax ({

                url: '/checkBoardName',
                type: 'post',
                datatype: 'json',
                data: {boardName: boardName},

                success: (response) => {

                    if (response.exists) {

                        duplicate = true;
                    } else {

                        duplicate = false;
                    }
                },
                error: (error) => {

                    console.error('error: ', error.status);
                }
            })
        })

        $('.formBoard').on('submit', (event) => {

            const bName = $('#b_name').val();
            const bNamePattern = /^(?=.{1,30}$)\S+(?: \S+)*$/;

            const files = $('.files').val();
            const filesPattern = /^.{1,50}$/;

            const intro = $('#intro').val();
            const introPattern = /^.{1,100}$/;

            const reason = $('#reason').val();
            const reasonPattern = /^.{1,100}$/;

            if (!bNamePattern.test(bName) || bName === '') {

                event.preventDefault();
                alert('タイトルの入力範囲から外れています。');
            } else if (duplicate) {

                event.preventDefault();
                alert('重複のタイトルがあります。');
            }
            else if (!filesPattern.test(files) || files === '') {

                event.preventDefault();
                alert('イメージを入れてください。');
            } else if (!introPattern.test(intro) || intro === '') {

                event.preventDefault();
                alert('説明の入力範囲から外れています。');
            } else if (!reasonPattern.test(reason) || reason === '') {

                event.preventDefault();
                alert('開設理由の入力範囲から外れています。');
            }
        })
    })