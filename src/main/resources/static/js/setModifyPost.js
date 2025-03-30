

    $(document).ready( () => {

        $('.formPost').on('submit', (event) => {

            const ppwd = $('#ppwd').val();
            const title = $('#title').val();
            const editor = $('#editor').val();
            const files = $('#files')[0].files;

            const ppwdPattern = /^\d{4}$/;
            const titlePattern = /^(?!\s)(?=.{1,25}$)(?!.*\s$).*$/;
            const editorPattern = /^(?=.{1,3100}$)(?!^\s+$).*$/;

            if (!ppwdPattern.test(ppwd) || ppwd === '') {

                event.preventDefault();
                alert('パスワードが正しくありません。');
            } else if (!titlePattern.test(title) || title === '') {

                event.preventDefault();
                alert('タイトルが正しくありません。');
            } else if (!editorPattern.test(editor) || editor === '') {

                event.preventDefault();
                alert('何も書いてありません。');
            } else {

                let totalLength = 0;

                for (let i = 0; i < files.length; i++) {

                    totalLength += files[i].name.length;
                }

                if (totalLength > 1000) {

                    event.preventDefault();
                    alert('添付したファイルが多すぎます。');
                }
            }
        })
    });